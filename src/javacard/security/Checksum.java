package javacard.security;

import pro.javacard.vre.vChecksum;

public abstract class Checksum {
	public static final byte ALG_ISO3309_CRC16 = 1;
	public static final byte ALG_ISO3309_CRC32 = 2;

	protected Checksum() {}
	public abstract byte getAlgorithm();
	public abstract void init(byte bArray[], short bOff, short bLen) throws CryptoException;
	public abstract short doFinal(byte inBuff[], short inOffset, short inLength, byte outBuff[], short outOffset);
	public abstract void update(byte inBuff[], short inOffset, short inLength);

	public static final Checksum getInstance(byte algorithm, boolean externalAccess) throws CryptoException {
		if (externalAccess) {
			CryptoException.throwIt(CryptoException.NO_SUCH_ALGORITHM);
		}
		return new vChecksum(algorithm);
	}
}
