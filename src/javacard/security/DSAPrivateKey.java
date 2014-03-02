package javacard.security;

public interface DSAPrivateKey extends PrivateKey, DSAKey {
	void setX(byte[] buffer, short offset, short length) throws CryptoException;
	short getX(byte[] buffer, short offset) throws CryptoException;
}