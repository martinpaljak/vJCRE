package javacard.framework;

import pro.javacard.vre.VRE;

@SuppressWarnings("serial")
public class ISOException extends CardRuntimeException {

	public ISOException(short sw) {
		super(sw);
	}

	public static void throwIt(short sw) throws ISOException {
		if (VRE.debugMode)
			throw new ISOException(sw);
		VRE.vISOException.setReason(sw);
		throw VRE.vISOException;
	}
}
