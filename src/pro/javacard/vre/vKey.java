package pro.javacard.vre;

import java.math.BigInteger;
import java.util.Arrays;

import javacard.security.CryptoException;
import javacard.security.Key;
import javacard.security.KeyBuilder;

public abstract class vKey implements Key {
	protected byte type;
	protected short length;

	@Override
	public short getSize() {
		return length;
	}

	@Override
	public byte getType() {
		return type;
	}

	public static Key buildKey(byte keyType, short keyLength) {
		Key k = null;
		switch (keyType) {
		case KeyBuilder.TYPE_RSA_PUBLIC:
			k = new vRSAPublicKey();
			break;
		case KeyBuilder.TYPE_RSA_PRIVATE:
			k = new vRSAPrivateKey();
			break;
		case KeyBuilder.TYPE_RSA_CRT_PRIVATE:
			k = new vRSAPrivateCrtKey();
			break;
		default:
			CryptoException.throwIt(CryptoException.NO_SUCH_ALGORITHM);
		}
		((vKey) k).type = keyType;
		((vKey) k).length = keyLength;
		return k;
	}

	// Get the JCE-compatible key
	protected abstract java.security.Key jce();

	// Copy BigInteger value to buffer
	protected static short send_positive_bigint(byte[] buffer, short offset, BigInteger i) {
		byte[] bytes = i.toByteArray();
		System.arraycopy(bytes, 0, buffer, offset, bytes.length);
		return (short) bytes.length;
	}

	// Get BigInteger value from buffer
	protected static BigInteger get_positive_bigint(byte[] buffer, short offset, short length) {
		return new BigInteger(1, Arrays.copyOfRange(buffer, offset, offset + length));
	}
}
