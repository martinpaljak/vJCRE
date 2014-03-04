package javacard.framework;

import pro.javacard.vre.VRE;

@SuppressWarnings("serial")
public class APDUException extends CardRuntimeException {

	public static final short ILLEGAL_USE = 1;
	public static final short BUFFER_BOUNDS = 2;
	public static final short BAD_LENGTH = 3;
	public static final short IO_ERROR = 4;
	public static final short NO_T0_GETRESPONSE = 170;
	public static final short T1_IFD_ABORT = 171;
	public static final short NO_T0_REISSUE = 172;

	public APDUException(short reason) {
		super(reason);
	}

	public static void throwIt(short reason) throws APDUException {
		if (VRE.debugMode)
			throw new APDUException(reason);
		VRE.vAPDUException.setReason(reason);
		throw VRE.vAPDUException;
	}
}
