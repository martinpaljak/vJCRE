package javacard.security;

public interface AESKey extends SecretKey {
	void setKey(byte[] keyData, short kOff) throws CryptoException, NullPointerException, ArrayIndexOutOfBoundsException;
	byte getKey(byte[] keyData, short kOff) throws CryptoException;
}