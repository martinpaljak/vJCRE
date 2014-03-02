package javacard.security;

public interface HMACKey extends SecretKey {
	byte getKey(byte[] keyData, short kOff);
	void setKey(byte[] keyData, short kOff, short kLen) throws CryptoException, NullPointerException, ArrayIndexOutOfBoundsException;
}
