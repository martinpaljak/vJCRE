package javacard.security;

public interface RSAPrivateCrtKey extends PrivateKey {
	void setP(byte[] buffer, short offset, short length) throws CryptoException;
	void setQ(byte[] buffer, short offset, short length) throws CryptoException;
	void setDP1(byte[] buffer, short offset, short length) throws CryptoException;
	void setDQ1(byte[] buffer, short offset, short length) throws CryptoException;
	void setPQ(byte[] buffer, short offset, short length) throws CryptoException;
	short getP(byte[] buffer, short offset) throws CryptoException;
	short getQ(byte[] buffer, short offset) throws CryptoException;
	short getDP1(byte[] buffer, short offset) throws CryptoException;
	short getDQ1(byte[] buffer, short offset) throws CryptoException;
	short getPQ(byte[] buffer, short offset) throws CryptoException;
}