package pro.javacard.vre;

import java.math.BigInteger;
import java.util.Arrays;

import javacard.framework.AID;
import javacard.framework.SystemException;
import javacard.framework.Util;
import openkms.gp.GPUtils;

public class vAID {
	protected byte aid[];

	protected vAID() {}

	public vAID(byte bArray[], short offset, byte length) throws SystemException, NullPointerException, ArrayIndexOutOfBoundsException, SecurityException {
		if (length < 5 || length > 16) {
			SystemException.throwIt(SystemException.ILLEGAL_VALUE);
		}
		aid = new byte[length];
		Util.arrayCopy(bArray, offset, aid, (short) 0, length);
	}

	// These are for internal use.
	public final byte[] getBytes() {
		return aid;
	}

	public vAID(byte[] aid) {
		this(aid, (short) 0, (byte) aid.length);
	}

	public final int hashCode() {
		return new BigInteger(aid).hashCode();
	}

	public final boolean equals(Object anObject) throws SecurityException {
		if (anObject == null) {
			return false;
		}
		if (!(anObject instanceof vAID)) {
			return false;
		}
		vAID other = (vAID) anObject;

		if (other.aid.length != aid.length) {
			return false;
		}
		return Arrays.equals(aid, other.aid);
	}

	public AID jc() {
		return (AID) this;
	}

	public String toString() {
		return GPUtils.byteArrayToString(aid);
	}
}
