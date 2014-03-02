package javacard.security;

public interface RSAPublicKey extends PublicKey {
	short getExponent(byte[] buffer, short offset) throws CryptoException;
	short getModulus(byte[] buffer, short offset) throws CryptoException;
	void setExponent(byte[] buffer, short offset, short length) throws CryptoException;
	void setModulus(byte[] buffer, short offset, short length) throws CryptoException;
}