package javacard.security;

import pro.javacard.vre.vSignature;

public abstract class Signature {

	public static final byte ALG_DES_MAC4_NOPAD = 1;
	public static final byte ALG_DES_MAC8_NOPAD = 2;
	public static final byte ALG_DES_MAC4_ISO9797_M1 = 3;
	public static final byte ALG_DES_MAC8_ISO9797_M1 = 4;
	public static final byte ALG_DES_MAC4_ISO9797_M2 = 5;
	public static final byte ALG_DES_MAC8_ISO9797_M2 = 6;
	public static final byte ALG_DES_MAC4_PKCS5 = 7;
	public static final byte ALG_DES_MAC8_PKCS5 = 8;
	public static final byte ALG_RSA_SHA_ISO9796 = 9;
	public static final byte ALG_RSA_SHA_PKCS1 = 10;
	public static final byte ALG_RSA_MD5_PKCS1 = 11;
	public static final byte ALG_RSA_RIPEMD160_ISO9796 = 12;
	public static final byte ALG_RSA_RIPEMD160_PKCS1 = 13;
	public static final byte ALG_DSA_SHA = 14;
	public static final byte ALG_RSA_SHA_RFC2409 = 15;
	public static final byte ALG_RSA_MD5_RFC2409 = 16;
	public static final byte ALG_ECDSA_SHA = 17;
	public static final byte ALG_AES_MAC_128_NOPAD = 18;
	public static final byte ALG_DES_MAC4_ISO9797_1_M2_ALG3 = 19;
	public static final byte ALG_DES_MAC8_ISO9797_1_M2_ALG3 = 20;
	public static final byte ALG_RSA_SHA_PKCS1_PSS = 21;
	public static final byte ALG_RSA_MD5_PKCS1_PSS = 22;
	public static final byte ALG_RSA_RIPEMD160_PKCS1_PSS = 23;
	public static final byte ALG_HMAC_SHA1 = 24;
	public static final byte ALG_HMAC_SHA_256 = 25;
	public static final byte ALG_HMAC_SHA_384 = 26;
	public static final byte ALG_HMAC_SHA_512 = 27;
	public static final byte ALG_HMAC_MD5 = 28;
	public static final byte ALG_HMAC_RIPEMD160 = 29;
	public static final byte ALG_RSA_SHA_ISO9796_MR = 30;
	public static final byte ALG_RSA_RIPEMD160_ISO9796_MR = 31;
	public static final byte ALG_KOREAN_SEED_MAC_NOPAD = 32;

	// 3.0.1
	public static final byte ALG_ECDSA_SHA_256 = 33;
	public static final byte ALG_ECDSA_SHA_384 = 34;
	public static final byte ALG_AES_MAC_192_NOPAD = 35;
	public static final byte ALG_AES_MAC_256_NOPAD = 36;
	public static final byte ALG_ECDSA_SHA_224 = 37;
	public static final byte ALG_ECDSA_SHA_512 = 38;
	public static final byte ALG_RSA_SHA_224_PKCS1 = 39;
	public static final byte ALG_RSA_SHA_256_PKCS1 = 40;
	public static final byte ALG_RSA_SHA_384_PKCS1 = 41;
	public static final byte ALG_RSA_SHA_512_PKCS1 = 42;
	public static final byte ALG_RSA_SHA_224_PKCS1_PSS = 43;
	public static final byte ALG_RSA_SHA_256_PKCS1_PSS = 44;
	public static final byte ALG_RSA_SHA_384_PKCS1_PSS = 45;
	public static final byte ALG_RSA_SHA_512_PKCS1_PSS = 46;
	// 3.0.4
	public static final byte ALG_DES_MAC4_ISO9797_1_M1_ALG3 = 47;
	public static final byte ALG_DES_MAC8_ISO9797_1_M1_ALG3 = 48;

	// 3.0.4
	public static final byte SIG_CIPHER_AES_MAC128 = 6;
	public static final byte SIG_CIPHER_DES_MAC4 = 1;
	public static final byte SIG_CIPHER_DES_MAC8 = 2;
	public static final byte SIG_CIPHER_DSA = 4;
	public static final byte SIG_CIPHER_ECDSA = 5;
	public static final byte SIG_CIPHER_HMAC = 7;
	public static final byte SIG_CIPHER_KOREAN_SEED_MAC = 8;
	public static final byte SIG_CIPHER_RSA = 3;

	public static final byte MODE_SIGN = 1;
	public static final byte MODE_VERIFY = 2;

	protected Signature() { }

	public abstract void init(Key theKey, byte theMode) throws CryptoException;
	public abstract void init(Key theKey, byte theMode, byte[] bArray, short bOff, short bLen) throws CryptoException;
	public abstract short getLength() throws CryptoException;
	public abstract byte getAlgorithm();
	public abstract void update(byte[] inBuff, short inOffset, short inLength) throws CryptoException;
	public abstract short sign(byte[] inBuff, short inOffset, short inLength, byte[] sigBuff, short sigOffset) throws CryptoException;
	public abstract boolean verify(byte[] inBuff, short inOffset, short inLength, byte[] sigBuff, short sigOffset, short sigLength) throws CryptoException;
	public static final Signature getInstance(byte algorithm, boolean externalAccess) throws CryptoException {
		if (externalAccess) {
			CryptoException.throwIt(CryptoException.NO_SUCH_ALGORITHM);
		}
		return new vSignature(algorithm);
	}
	// 3.0.4
	public static final Signature getInstance(byte messageDigestAlgorithm, byte cipherAlgorithm, byte paddingAlgorithm, boolean externalAccess) throws CryptoException {
		CryptoException.throwIt(CryptoException.NO_SUCH_ALGORITHM);
		return null;
	}
}
