package javacard.security;

public interface DSAKey {
	void setP(byte[] buffer, short offset, short length) throws CryptoException;
	void setQ(byte[] buffer, short offset, short length) throws CryptoException;
	void setG(byte[] buffer, short offset, short length) throws CryptoException;
	short getP(byte[] buffer, short offset) throws CryptoException;
	short getQ(byte[] buffer, short offset) throws CryptoException;
	short getG(byte[] buffer, short offset) throws CryptoException;
}