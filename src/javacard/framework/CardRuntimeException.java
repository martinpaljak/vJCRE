package javacard.framework;

import pro.javacard.vre.VRE;

@SuppressWarnings("serial")
public class CardRuntimeException extends RuntimeException {

	private short reason;

	public CardRuntimeException(short reason) {
		this.reason = reason;
	}

	public short getReason() {
		return this.reason;
	}

	public void setReason(short reason) {
		this.reason = reason;
	}

	public static void throwIt(short reason) throws CardRuntimeException {
		if (VRE.debugMode)
			throw new CardRuntimeException(reason);
		VRE.vCardRuntimeException.setReason(reason);
		throw VRE.vCardRuntimeException;
	}
}
