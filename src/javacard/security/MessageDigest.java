package javacard.security;

import pro.javacard.vre.vMessageDigest;

public abstract class MessageDigest {
	public static final byte ALG_NULL = 0; // 3.0.1
	public static final byte ALG_SHA = 1;
	public static final byte ALG_MD5 = 2;
	public static final byte ALG_RIPEMD160 = 3;
	public static final byte ALG_SHA_256 = 4;
	public static final byte ALG_SHA_384 = 5;
	public static final byte ALG_SHA_512 = 6;
	public static final byte ALG_SHA_224 = 7; // 3.0.1
	public static final byte LENGTH_MD5 = 16;
	public static final byte LENGTH_RIPEMD160 = 20;
	public static final byte LENGTH_SHA = 20;
	public static final byte LENGTH_SHA_256 = 32;
	public static final byte LENGTH_SHA_384 = 48;
	public static final byte LENGTH_SHA_512 = 64;
	public static final byte LENGTH_SHA_224 = 28; // 3.0.1


	protected MessageDigest() {}
	public abstract byte getAlgorithm();
	public abstract byte getLength();
	public abstract short doFinal(byte[] inBuff, short inOffset, short inLength, byte[] outBuff, short outOffset);
	public abstract void update(byte[] inBuff, short inOffset, short inLength);
	public abstract void reset();

	public static final InitializedMessageDigest getInitializedMessageDigestInstance(byte algorithm, boolean externalAccess) throws CryptoException {
		CryptoException.throwIt(CryptoException.NO_SUCH_ALGORITHM);
		return null;
	}

	public static final MessageDigest getInstance(byte algorithm, boolean externalAccess) throws CryptoException {
		if (externalAccess) {
			CryptoException.throwIt(CryptoException.NO_SUCH_ALGORITHM);
		}
		return new vMessageDigest(algorithm);
	}
}