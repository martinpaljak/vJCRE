package javacard.framework;

import pro.javacard.vre.VRE;

@SuppressWarnings("serial")
public class UserException extends CardException {

	public UserException(short reason) {
		super(reason);
	}

	public static void throwIt(short reason) throws UserException {
		if (VRE.debugMode)
			throw new UserException(reason);
		VRE.vUserException.setReason(reason);
		throw VRE.vUserException;
	}
}
