package javacard.security;

import pro.javacard.vre.vRandomData;

public abstract class RandomData {
	public static final byte ALG_PSEUDO_RANDOM = 1;
	public static final byte ALG_SECURE_RANDOM = 2;

	protected RandomData() {}
	public abstract void generateData(byte[] buffer, short offset, short length) throws CryptoException;
	public abstract void setSeed(byte[] buffer, short offset, short length);
	public static final RandomData getInstance(byte algorithm) throws CryptoException {
		return new vRandomData();
	}
}
