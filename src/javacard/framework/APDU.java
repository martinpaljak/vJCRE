package javacard.framework;


public final class APDU extends pro.javacard.vre.vAPDU {

	public static final byte STATE_INITIAL = 0;
	public static final byte STATE_PARTIAL_INCOMING = 1;
	public static final byte STATE_FULL_INCOMING = 2;
	public static final byte STATE_OUTGOING = 3;
	public static final byte STATE_OUTGOING_LENGTH_KNOWN = 4;
	public static final byte STATE_PARTIAL_OUTGOING = 5;
	public static final byte STATE_FULL_OUTGOING = 6;
	public static final byte STATE_ERROR_NO_T0_GETRESPONSE = -1;
	public static final byte STATE_ERROR_T1_IFD_ABORT = -2;
	public static final byte STATE_ERROR_IO = -3;
	public static final byte STATE_ERROR_NO_T0_REISSUE = -4;
	public static final byte PROTOCOL_MEDIA_MASK = (byte) 0xF0;
	public static final byte PROTOCOL_TYPE_MASK =  (byte) 0x0F;
	public static final byte PROTOCOL_T0 = 0;
	public static final byte PROTOCOL_T1 = 1;
	public static final byte PROTOCOL_MEDIA_DEFAULT = 0;
	public static final byte PROTOCOL_MEDIA_CONTACTLESS_TYPE_A = (byte) 0x80;
	public static final byte PROTOCOL_MEDIA_CONTACTLESS_TYPE_B = (byte) 0x90;
	public static final byte PROTOCOL_MEDIA_USB = (byte) 0xA0;

}
