package javacard.framework;

import pro.javacard.vre.VRE;

public class SystemException extends CardRuntimeException {

	public static final short ILLEGAL_VALUE = 1;
	public static final short NO_TRANSIENT_SPACE = 2;
	public static final short ILLEGAL_TRANSIENT = 3;
	public static final short ILLEGAL_AID = 4;
	public static final short NO_RESOURCE = 5;
	public static final short ILLEGAL_USE = 6;

	public SystemException(short reason) {
		super(reason);
	}

	public static void throwIt(short reason) throws SystemException {
		if (VRE.debugMode)
			throw new SystemException(reason);
		VRE.vSystemException.setReason(reason);
		throw VRE.vSystemException;
	}
}
