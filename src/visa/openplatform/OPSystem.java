package visa.openplatform;

import javacard.framework.APDU;

// TODO
public class OPSystem {
	public static final byte APPLET_BLOCKED = 127;
	public static final byte APPLET_INSTALLED = 3;
	public static final byte APPLET_LOCKED = -127;
	public static final byte APPLET_PERSONALIZED = 15;
	public static final byte APPLET_SELECTABLE = 7;
	public static final byte CARD_INITIALIZED = 7;
	public static final byte CARD_LOCKED = 127;
	public static final byte CARD_OP_READY = 1;
	public static final byte CARD_SECURED = 15;
	public static final byte INITIALIZED = 7;
	public static final byte PACKAGE_LOADED = 1;

	public static byte getCardContentState() {
		return APPLET_SELECTABLE;
	}
	public static boolean setATRHistBytes(byte[] buffer, short bOffset, byte bLength) {
		return false;
	}
	public static byte getCardManagerState() {
		return CARD_OP_READY;
	}

	public static void getCPLCData(APDU apdu, short apdu_offset, short cplc_offset, short length) {

	}
}
