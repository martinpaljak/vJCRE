package javacard.security;

public interface ECPrivateKey extends PrivateKey, ECKey {
	void setS(byte[] buffer, short offset, short length) throws CryptoException;
	short getS(byte[] buffer, short offset) throws CryptoException;
}
