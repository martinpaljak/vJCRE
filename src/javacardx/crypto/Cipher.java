package javacardx.crypto;

import javacard.security.CryptoException;
import javacard.security.Key;
import pro.javacard.vre.vCipher;

public abstract class Cipher {
	public static final byte ALG_DES_CBC_NOPAD = 1;
	public static final byte ALG_DES_CBC_ISO9797_M1 = 2;
	public static final byte ALG_DES_CBC_ISO9797_M2 = 3;
	public static final byte ALG_DES_CBC_PKCS5 = 4;
	public static final byte ALG_DES_ECB_NOPAD = 5;
	public static final byte ALG_DES_ECB_ISO9797_M1 = 6;
	public static final byte ALG_DES_ECB_ISO9797_M2 = 7;
	public static final byte ALG_DES_ECB_PKCS5 = 8;
	public static final byte ALG_RSA_ISO14888 = 9;
	public static final byte ALG_RSA_PKCS1 = 10;
	@Deprecated
	public static final byte ALG_RSA_ISO9796 = 11;
	public static final byte ALG_RSA_NOPAD = 12;
	public static final byte ALG_AES_BLOCK_128_CBC_NOPAD = 13;
	public static final byte ALG_AES_BLOCK_128_ECB_NOPAD = 14;
	public static final byte ALG_RSA_PKCS1_OAEP = 15;
	public static final byte ALG_KOREAN_SEED_ECB_NOPAD = 16;
	public static final byte ALG_KOREAN_SEED_CBC_NOPAD = 17;
	// 3.0.1
	public static final byte ALG_AES_BLOCK_192_CBC_NOPAD = 18;
	public static final byte ALG_AES_BLOCK_192_ECB_NOPAD = 19;
	public static final byte ALG_AES_BLOCK_256_CBC_NOPAD = 20;
	public static final byte ALG_AES_BLOCK_256_ECB_NOPAD = 21;
	public static final byte ALG_AES_CBC_ISO9797_M1 = 22;
	public static final byte ALG_AES_CBC_ISO9797_M2 = 23;
	public static final byte ALG_AES_CBC_PKCS5 = 24;
	public static final byte ALG_AES_ECB_ISO9797_M1 = 25;
	public static final byte ALG_AES_ECB_ISO9797_M2 = 26;
	public static final byte ALG_AES_ECB_PKCS5 = 27;
	public static final byte MODE_DECRYPT = 1;
	public static final byte MODE_ENCRYPT = 2;

	protected Cipher() {}
	public abstract void init(Key theKey, byte theMode) throws CryptoException;
	public abstract void init(Key theKey, byte theMode, byte[] bArray, short bOff, short bLen) throws CryptoException;
	public abstract byte getAlgorithm();
	public abstract short doFinal(byte[] inBuff, short inOffset, short inLength, byte[] outBuff, short outOffset) throws CryptoException;
	public abstract short update(byte[] inBuff, short inOffset, short inLength, byte[] outBuff, short outOffset) throws CryptoException;

	public static final Cipher getInstance(byte algorithm, boolean externalAccess) throws CryptoException {
		if (externalAccess) {
			CryptoException.throwIt(CryptoException.NO_SUCH_ALGORITHM);
		}
		return new vCipher(algorithm);
	}
}
