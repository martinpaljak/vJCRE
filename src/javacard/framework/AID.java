package javacard.framework;


public final class AID extends pro.javacard.vre.vAID {

	public final byte getBytes(byte dest[], short offset) throws NullPointerException, ArrayIndexOutOfBoundsException, SecurityException {
		Util.arrayCopy(aid, (short) 0, dest, offset, (short) aid.length);
		return (byte) aid.length;
	}

	public final boolean equals(byte bArray[], short offset, byte length) throws ArrayIndexOutOfBoundsException, SecurityException {
		if (length != aid.length)
			return false;
		return Util.arrayCompare(bArray, offset, aid, (short) 0, length) == 0;
	}

	public final boolean partialEquals(byte bArray[], short offset, byte length) throws ArrayIndexOutOfBoundsException, SecurityException {
		if (length > aid.length) {
			return false;
		}
		return Util.arrayCompare(bArray, offset, aid, (short) 0, length) == 0;
	}

	public final byte getPartialBytes(short aidOffset, byte dest[], short oOffset, byte oLength) throws NullPointerException, ArrayIndexOutOfBoundsException, SecurityException {
		byte len = oLength;
		if (oLength == 0) {
			len = (byte) (aid.length - aidOffset);
		}
		Util.arrayCopy(aid, aidOffset, dest, oOffset, len);
		return (byte) len;
	}

	public final boolean RIDEquals(AID otherAID) throws SecurityException {
		if (otherAID == null) {
			return false;
		}
		return Util.arrayCompare(aid, (short) 0, otherAID.aid, (short) 0, (short) 5) == 0;
	}
}
