package javacard.framework;

import pro.javacard.vre.VRE;

public abstract class Applet {

	protected Applet() {
	}

	public static void install(byte bArray[], short bOffset, byte bLength) throws ISOException {
		ISOException.throwIt(ISO7816.SW_FUNC_NOT_SUPPORTED);
	}

	public abstract void process(APDU apdu) throws ISOException;

	public boolean select() {
		return true;
	}

	public void deselect() {
	}

	public Shareable getShareableInterfaceObject(AID clientAID, byte parameter) {
		return null;
	}

	protected final void register() throws SystemException {
		VRE.getInstance().register(this);
	}

	protected final void register(byte bArray[], short bOffset, byte bLength) throws SystemException {
		VRE.getInstance().register(this, bArray, bOffset, bLength);
	}

	protected final boolean selectingApplet() {
		return false; // FIXME
	}
}
