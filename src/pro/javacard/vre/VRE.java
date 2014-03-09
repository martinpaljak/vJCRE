package pro.javacard.vre;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javacard.framework.AID;
import javacard.framework.APDU;
import javacard.framework.APDUException;
import javacard.framework.Applet;
import javacard.framework.CardException;
import javacard.framework.CardRuntimeException;
import javacard.framework.ISO7816;
import javacard.framework.ISOException;
import javacard.framework.JCSystem;
import javacard.framework.PINException;
import javacard.framework.Shareable;
import javacard.framework.SystemException;
import javacard.framework.TransactionException;
import javacard.framework.UserException;
import javacard.security.CryptoException;
import javacardx.apdu.ExtendedLength;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.objenesis.strategy.StdInstantiatorStrategy;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class VRE {
	public static final int API_LEVEL_222 = 222;
	public static final int API_LEVEL_301 = 301;
	public static final int API_LEVEL_304 = 304;

	// Disabling this makes it more JCRE-conforming but less easy to debug
	public static final boolean debugMode = true;
	public static final boolean verbose = true;
	public static final String provider = BouncyCastleProvider.PROVIDER_NAME;
	public static final void log(String msg) {
		System.out.println(msg);
	}
	protected static byte[] default_atr_bytes = new byte[] { (byte) 0x3b, (byte) 0x80, (byte) 0x80, (byte) 0x01, (byte) 0x01 };

	// This is the singleton
	private transient static VRE instance = null;

	// AID-s of important applets.
	private AID currentApplet = null;
	private AID previousApplet = null;
	private AID defaultApplet = null;

	// JCRE technical details
	@SuppressWarnings("rawtypes")
	// Loaded applets
	private Map<AID, Class> loaded = new HashMap<AID, Class>();
	// installed (registered) applets
	private Map<AID, Applet> installed = new HashMap<AID, Applet>();

	// Memory slices. TODO: add DESELECT is applet-specific?
	private List resetSlices = new ArrayList();
	private List deselectSlices = new ArrayList();

	// JCRE-owned exception instances
	public static final SystemException vSystemException = new SystemException((short) 0);;
	public static final APDUException vAPDUException = new APDUException((short) 0);
	public static final CardException vCardException = new CardException((short) 0);
	public static final CardRuntimeException vCardRuntimeException = new CardRuntimeException((short) 0);
	public static final ISOException vISOException = new ISOException((short) 0);
	public static final PINException vPINException = new PINException((short) 0);
	public static final UserException vUserException = new UserException((short) 0);
	public static final TransactionException vTransactionException = new TransactionException((short) 0);
	public static final CryptoException vCryptoException = new CryptoException((short) 0);

	// Technical parameters of the card connection
	private byte protocol = APDU.PROTOCOL_T0;
	private byte[] atr = default_atr_bytes;

	private final transient APDU apdu = new APDU();
	public VRE() {
		if (Security.getProvider(provider) == null)
			Security.addProvider(new BouncyCastleProvider());

		instance = this;
		atr = default_atr_bytes;
		reset();
	}

	public static VRE getInstance() {
		if (instance == null)
			instance = new VRE();
		return instance;
	}

	// Only supposed to be called on loading? TODO
	public static void setInstance(VRE instance) {
		VRE.instance = instance;
	}

	public void setATR(byte[] atr) {
		this.atr = atr; // TODO: maybe copy ?
	}
	public byte[] getATR() {
		return atr;
	}

	public byte getProtocol() {
		return protocol;
	}

	public void connect(byte protocol) {
		this.protocol = protocol;

		if (defaultApplet != null)
			select(defaultApplet);
		log("CONNECT " + (defaultApplet != null ? defaultApplet : "nothing"));
	}

	// Actually invoke Applet.install() method
	@SuppressWarnings("rawtypes")
	private void install(Class applet, byte [] buff, short offset, byte len) throws SecurityException {
		try {
			Method install = applet.getMethod("install", new Class[]{byte[].class, short.class, byte.class});
			install.invoke(null, new Object[]{buff, new Short(offset), new Byte(len)});
		} catch (InvocationTargetException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException e) {
			e.printStackTrace();
			throw new RuntimeException("This is no applet!");
		}
	}


	public void install(vAID applet, vAID instance) {
		@SuppressWarnings("rawtypes")
		Class app = loaded.get(applet);
		log("INSTALL  " + app.getCanonicalName() + " with AID " + applet.toString());

		byte [] instdata = new byte[ 3 + instance.getBytes().length];
		instdata[0] = (byte) instance.getBytes().length;
		System.arraycopy(instance.getBytes(), 0, instdata, 1, instance.getBytes().length);
		install(app, instdata, (short)0, (byte) instdata.length);
	}

	public void install(AID applet, boolean isDefault) {
		if (isDefault) {
			defaultApplet = applet;
		}
		log("INSTALL  instance of " + applet.toString() + " AS DEFAULT");
		install(applet, applet);
	}

	@SuppressWarnings("rawtypes")
	public void load(Class applet, AID defaultAID) {
		log("LOAD     " + applet.getName() + " with AID " + defaultAID);
		loaded.put(defaultAID, applet);
	}

	// Callback from Applet.register(byte[] buffer, short offset, byte length)
	public void register(Applet applet, byte[] buffer, short offset, byte length) {
		if (length < 5 || length > 16) {
			SystemException.throwIt(SystemException.ILLEGAL_VALUE);
		}
		AID instanceAID = new AID(buffer, offset, length);
		log("REGISTER " + applet.getClass().getName() + " with AID " + instanceAID);

		installed.put(instanceAID, applet);
	}

	// Callback from Applet.register()
	public void register(Applet applet) {
		log("REGISTER " + applet.getClass().getName() + " with AID " + currentApplet);
		installed.put(currentApplet, applet);
	}


	// Select applet
	public boolean select(AID aid) {
		if (!installed.containsKey(aid)) {
			return false;
		}
		Applet app = installed.get(aid);
		boolean selected =  app.select();
		if (selected)
			currentApplet = aid;
		log(aid + (selected?" SELECTED" : "NOT SELECTED"));
		return selected;
	}

	public byte[] transmit(byte[] apdu) {
		APDU ref = getInstance().apdu;

		Applet app = installed.get(currentApplet);
		ref.bb.rewind();
		ref.bb.put(apdu);
		ref.bbin = ref.bb.duplicate();
		// So that any output would go back to the real buffer
		ref.bb.rewind();

		// Refuse to work with extended APDU-s unless...
		if (!currentIsExtended() && ref.isExtendedAPDU()) {
			throw new RuntimeException("Applet does not do extended APDU!");
		}
		System.out.println(app);
		try {
			app.process(ref);
			ref.bb.putShort(ISO7816.SW_NO_ERROR);
		} catch (ISOException iso) {
			// If anything was already returned, set the SW at the end.
			// Set SW and return the thing.
			ref.bb.putShort(iso.getReason());
		} catch (Exception e) {
			if (e instanceof CardRuntimeException) {
				CardRuntimeException cre = (CardRuntimeException) e;
				System.out.println(e.getClass().getName() + ": " + cre.getReason());
				e.printStackTrace();
			}
			e.printStackTrace();
			//
			ref.bb.putShort(ISO7816.SW_UNKNOWN);
		}

		byte[] copy = new byte[ref.bb.position()];
		ref.bb.rewind();
		ref.bb.get(copy);
		return copy;
	}

	public boolean currentIsExtended() {
		Applet app = installed.get(currentApplet);
		return (app instanceof ExtendedLength);
	}

	// Load/reastore
	public void storeVM(File f) throws IOException {
		Kryo kryo = new Kryo();
		kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
		Output o = new Output(new FileOutputStream(f));

		kryo.writeClassAndObject(o, instance);
		o.close();
	}

	public void loadVM(File f) throws IOException, ClassNotFoundException {
		Kryo kryo = new Kryo();
		kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
		FileInputStream s = new FileInputStream(f);
		Input input = new Input(s);
		Object inst = kryo.readClassAndObject(input);
		input.close();
		if (inst instanceof VRE)
			setInstance((VRE)inst);
		System.out.println("Loaded " + inst.getClass().getCanonicalName());
	}


	public void reset() {
		vSystemException.setReason((short) 0);
		vAPDUException.setReason((short) 0);
		vCardException.setReason((short) 0);
		vCardRuntimeException.setReason((short) 0);
		vISOException.setReason((short) 0);
		vPINException.setReason((short) 0);
		vUserException.setReason((short) 0);
		vTransactionException.setReason((short) 0);
		vCryptoException.setReason((short) 0);

		for (Object o: resetSlices) {
			resetTransient(o);
		}
		for (Object o: deselectSlices) {
			resetTransient(o);
		}
	}

	private void resetTransient(Object o) {
		if (o instanceof byte[]) {
			byte[] b = (byte[]) o;
			for (int i = 0; i < b.length; i++) {
				b[i] = 0x00;
			}
		} else if (o instanceof boolean[]) {
			boolean[] b = (boolean[]) o;
			for (int i = 0; i < b.length; i++) {
				b[i] = false;
			}
		} else if (o instanceof short[]) {
			short[] b = (short[]) o;
			for (int i = 0; i < b.length; i++) {
				b[i] = 0x00;
			}
		} else if (o instanceof Object[]) {
			Object[] b = (Object[]) o;
			for (int i = 0; i < b.length; i++) {
				b[i] = null;
			}
		} else {
			throw new RuntimeException("Unknown transient type: " + o.getClass().getCanonicalName());
		}
	}

	// Helpers for memory management
	private byte whichMemoryType(Object theObj) {
		if (!theObj.getClass().isArray()) {
			return JCSystem.NOT_A_TRANSIENT_OBJECT;
		}
		if (deselectSlices.contains(theObj)) {
			return JCSystem.CLEAR_ON_DESELECT;
		}
		if (resetSlices.contains(theObj)) {
			return JCSystem.CLEAR_ON_RESET;
		}
		return JCSystem.NOT_A_TRANSIENT_OBJECT;
	}

	@SuppressWarnings("unchecked")
	private void setMemoryReference(Object ref, byte event) {
		switch (event) {
		case JCSystem.CLEAR_ON_DESELECT:
			deselectSlices.add(ref);
			break;
		case JCSystem.CLEAR_ON_RESET:
			resetSlices.add(ref);
			break;
		default:
			SystemException.throwIt(SystemException.ILLEGAL_VALUE);
		}
	}

	// javacard.framework.JCSystem methods
	public static byte isTransient(Object theObj) {return getInstance().whichMemoryType(theObj);}
	public static boolean[] makeTransientBooleanArray(short length, byte event) {
		boolean[] array = new boolean[length];
		getInstance().setMemoryReference(array, event);
		return array;
	}
	public static byte[] makeTransientByteArray(short length, byte event) {
		byte[] array = new byte[length];
		getInstance().setMemoryReference(array, event);
		return array;
	}
	public static short[] makeTransientShortArray(short length, byte event) {
		short[] array = new short[length];
		getInstance().setMemoryReference(array, event);
		return array;
	}
	public static Object[] makeTransientObjectArray(short length, byte event) {
		Object[] array = new Object[length];
		getInstance().setMemoryReference(array, event);
		return array;
	}
	public static short getVersion() {return 0x0202;}
	public static AID getAID() {return getInstance().currentApplet;}
	public static AID lookupAID(byte[] buffer, short offset, byte length) {
		AID q = new AID(buffer, offset, length);
		// get the VRE-owned instance
		for (AID a: getInstance().installed.keySet()) {
			if (q.equals(a))
				return a;
		}
		return null;
	}
	public static void beginTransaction() throws TransactionException {}
	public static void abortTransaction() throws TransactionException {}
	public static void commitTransaction() throws TransactionException {}
	public static byte getTransactionDepth() {return 0;}
	public static short getUnusedCommitCapacity() {return Short.MAX_VALUE;}
	public static AID getPreviousContextAID() {return getInstance().previousApplet;}
	public static short getAvailableMemory(byte memoryType) throws SystemException {return Short.MAX_VALUE;}

	// FIXME: contexts
	public static Shareable getAppletShareableInterfaceObject(vAID serverAID, byte parameter) {
		Applet app = getInstance().installed.get(serverAID);
		return app.getShareableInterfaceObject(getInstance().currentApplet, parameter);
	};
	public static boolean isObjectDeletionSupported() {return true;}
	public static void requestObjectDeletion() throws SystemException {}
	public static byte getAssignedChannel() {return 0;}
}
