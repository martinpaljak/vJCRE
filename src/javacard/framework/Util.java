package javacard.framework;

import openkms.gp.GPUtils;
import pro.javacard.vre.VRE;


public class Util {

	public static final short arrayCopy(byte src[], short srcOff, byte dest[], short destOff, short length) throws ArrayIndexOutOfBoundsException, NullPointerException, TransactionException {
		return arrayCopyNonAtomic(src, srcOff, dest, destOff, length);
	}

	public static final short arrayCopyNonAtomic(byte src[], short srcOff, byte dest[], short destOff, short length) throws ArrayIndexOutOfBoundsException, NullPointerException {
		if (VRE.debugMode) {
			System.out.println(GPUtils.byteArrayToString(src));
			System.out.println(GPUtils.byteArrayToString(dest));
			System.out.println(srcOff + " " + destOff + " " + length);
		}

		System.arraycopy(src, srcOff, dest, destOff, length);
		return (short) (destOff + length);
	}

	public static final short arrayFillNonAtomic(byte bArray[], short bOff, short bLen, byte bValue) throws ArrayIndexOutOfBoundsException, NullPointerException {
		for (short i=0; i<bLen; i++) {
			bArray[bOff+i] = bValue;
		}
		return (short) (bOff + bLen);
	}

	public static final byte arrayCompare(byte src[], short srcOff, byte dest[], short destOff, short length) throws ArrayIndexOutOfBoundsException, NullPointerException {
		if (srcOff < 0 || destOff < 0 || length < 0) {
			throw new ArrayIndexOutOfBoundsException();
		}
		for (short i = 0; i < length; i++) {
			if (src[srcOff + i] != dest[destOff + i]) {
				return (byte) ((short) (src[srcOff + i] & 0x00ff) > (short) (dest[destOff + i] & 0x00ff) ? 1 : -1);
			}
		}
		return 0;
	}

	public static final short makeShort(byte b1, byte b2) {
		return (short) (((short) b1 << 8) + ((short) b2 & 0xff));
	}

	public static final short getShort(byte bArray[], short bOff) throws ArrayIndexOutOfBoundsException, NullPointerException {
		return (short) (((short) bArray[bOff] << 8) + ((short) bArray[bOff + 1] & 0xff));
	}

	public static final short setShort(byte bArray[], short bOff, short sValue) throws TransactionException, ArrayIndexOutOfBoundsException, NullPointerException {
		bArray[bOff] = (byte) (sValue >> 8);
		bArray[bOff + 1] = (byte) sValue;
		return (short) (bOff + 2);
	}
}
