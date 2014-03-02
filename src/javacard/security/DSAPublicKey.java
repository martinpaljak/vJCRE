package javacard.security;

public interface DSAPublicKey extends PublicKey, DSAKey {
	void setY(byte[] buffer, short offset, short length) throws CryptoException;
	short getY(byte[] buffer, short offset) throws CryptoException;
}