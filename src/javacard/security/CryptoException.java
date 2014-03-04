package javacard.security;

import javacard.framework.CardRuntimeException;
import pro.javacard.vre.VRE;

@SuppressWarnings("serial")
public class CryptoException extends CardRuntimeException {

	public static final short ILLEGAL_VALUE = 1;
	public static final short UNINITIALIZED_KEY = 2;
	public static final short NO_SUCH_ALGORITHM = 3;
	public static final short INVALID_INIT = 4;
	public static final short ILLEGAL_USE = 5;

	public CryptoException(short reason) {
		super(reason);
	}

	public static void throwIt(short reason) {
		if (VRE.debugMode)
			throw new CryptoException(reason);
		VRE.vCryptoException.setReason(reason);
		throw VRE.vCryptoException;
	}
}