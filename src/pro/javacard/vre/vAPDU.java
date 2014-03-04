package pro.javacard.vre;

import java.nio.ByteBuffer;

import javacard.framework.APDU;
import javacard.framework.APDUException;
import javacard.framework.ISO7816;

import javax.smartcardio.CommandAPDU;

import org.bouncycastle.util.Arrays;

// translated CommandAPDU/ResponseAPDU and byte arrays into VRE objects
// Instance of this class is fed to Applet.process()
// This class needs seom spec-love.

public class vAPDU {
	protected transient byte [] buffer;
	protected transient ByteBuffer bb;
	protected transient ByteBuffer bbin;
	private transient byte state;

	// the singleton
	private static vAPDU currentAPDU;

	protected vAPDU() {
		currentAPDU = this;
		buffer = new byte[Short.MAX_VALUE];
		bb = ByteBuffer.wrap(buffer);
		bbin = bb.duplicate();
	}

	public byte[] getBuffer() {
		return buffer;
	}

	public static byte getProtocol() {
		return VRE.getInstance().getProtocol();
	}

	public byte getNAD() {
		return 0;
	}

	public static short getOutBlockSize() {
		if (getProtocol() == APDU.PROTOCOL_T0) {
			return 258;
		}
		return Short.MAX_VALUE;
	}

	// FIXME: get actual Le if exists.
	public short setOutgoing() throws APDUException {
		state = APDU.STATE_OUTGOING;
		short ret = 256;
		if (VRE.getInstance().currentIsExtended())
			ret = Short.MAX_VALUE;
		return ret;
	}

	public short setOutgoingNoChaining() throws APDUException {
		state = APDU.STATE_OUTGOING;
		return 256;
	}

	// Ignore this, as all data is concatenated into the "endless" buffer
	public void setOutgoingLength(short len) throws APDUException {
		state = APDU.STATE_OUTGOING_LENGTH_KNOWN;
	}

	public short receiveBytes(short bOff) throws APDUException {
		state = APDU.STATE_FULL_INCOMING;
		// FIXME return "incoming minus offset in parameter"
		return (short) (bbin.position() - ISO7816.OFFSET_CDATA);
	}

	public short setIncomingAndReceive() throws APDUException {
		state = APDU.STATE_FULL_INCOMING;
		// Virtually call receiveBytes
		// FIXME: logic is wrong.
		if (VRE.getInstance().currentIsExtended() && isExtendedAPDU()) {
			return getExtendedLc();
		}
		// Use Lc for short APDU-s
		if (bbin.position() > ISO7816.OFFSET_LC)
			return (short) (buffer[ISO7816.OFFSET_LC] & 0x00FF);

		return 0;
	}

	public void sendBytes(short bOff, short len) throws APDUException {
		bb.position(bOff + len);
	}

	public void sendBytesLong(byte outData[], short bOff, short len) throws APDUException, SecurityException {
		byte[] data = Arrays.copyOfRange(outData, bOff, bOff + len);
		bb.put(data);
	}

	public void setOutgoingAndSend(short bOff, short len) throws APDUException {
		setOutgoing();
		setOutgoingLength(len);
		sendBytes(bOff, len);
	}

	public byte getCurrentState() {
		return state;
	}

	public static APDU getCurrentAPDU() throws SecurityException {
		return (APDU) currentAPDU;
	}

	public static byte[] getCurrentAPDUBuffer() throws SecurityException {
		return getCurrentAPDU().getBuffer();
	}

	public static byte getCLAChannel() {
		// TODO set on incoming, but always 0 ATM
		return 0;
	}

	public static void waitExtension() throws APDUException {
		// Do nothing. Maybe APDUException.ILLEGAL_USE ?
	}
	public boolean isCommandChainingCLA() {
		return (buffer[ISO7816.OFFSET_CLA] & 0x10) != 0;
	}
	public boolean isSecureMessagingCLA() {
		return (buffer[ISO7816.OFFSET_CLA] & 0x2C) != 0;
	}
	public boolean isISOInterindustryCLA() {
		return (buffer[ISO7816.OFFSET_CLA] & 0x80) == 0;
	}
	public short getIncomingLength() {
		if (isExtendedAPDU())
			return getExtendedLc();
		return (short) (buffer[ISO7816.OFFSET_LC] & 0x00FF);
	}
	public short getOffsetCdata() {
		if (isExtendedAPDU())
			return 7;
		return 5;
	}

	// Internal helpers
	protected void fromCommandAPDU(CommandAPDU apdu) {
		bb.rewind();
		bb.put(apdu.getBytes());
		bbin = bb.duplicate();
	}

	protected boolean isExtendedAPDU() {
		if (bbin.position() > 6 && buffer[ISO7816.OFFSET_LC] == 0x00)
			return true;
		return false;
	}

	protected short getExtendedLc() {
		return (short) ((buffer[ISO7816.OFFSET_LC +1] & 0xff << 8) | buffer[ISO7816.OFFSET_LC+2] & 0xff);
	}

	protected short getExtendedLe() {
		return (short) ((buffer[bbin.position()-2] & 0xff << 8) | buffer[bbin.position()-1] & 0xff);
	}
}
