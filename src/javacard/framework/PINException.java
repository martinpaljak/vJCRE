package javacard.framework;

import pro.javacard.vre.VRE;

public class PINException extends CardRuntimeException {

	public static final short ILLEGAL_VALUE = 1;

	public PINException(short reason) {
		super(reason);
	}

	public static void throwIt(short reason) throws PINException {
		if (VRE.debugMode)
			throw new PINException(reason);
		VRE.vPINException.setReason(reason);
		throw VRE.vPINException;
	}
}
