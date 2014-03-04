package javacard.framework;

import pro.javacard.vre.VRE;

@SuppressWarnings("serial")
public class CardException extends Exception {

	private short reason;

	public CardException(short reason) {
		this.reason = reason;
	}

	public short getReason() {
		return reason;
	}

	public void setReason(short reason) {
		this.reason = reason;
	}

	public static void throwIt(short reason) throws CardException {
		if (VRE.debugMode)
			throw new CardException(reason);
		VRE.vCardException.setReason(reason);
		throw VRE.vCardException;
	}
}
