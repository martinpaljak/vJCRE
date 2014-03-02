package javacard.security;

public interface ECPublicKey extends PublicKey, ECKey {
	void setW(byte[] buffer, short offset, short length) throws CryptoException;
	short getW(byte[] buffer, short offset) throws CryptoException;
}