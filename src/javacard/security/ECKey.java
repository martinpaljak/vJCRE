package javacard.security;

public interface ECKey {
	void setFieldFP(byte[] buffer, short offset, short length) throws CryptoException;
	void setFieldF2M(short e) throws CryptoException;
	void setFieldF2M(short e1, short e2, short e3) throws CryptoException;
	void setA(byte[] buffer, short offset, short length) throws CryptoException;
	void setB(byte[] buffer, short offset, short length) throws CryptoException;
	void setG(byte[] buffer, short offset, short length) throws CryptoException;
	void setR(byte[] buffer, short offset, short length) throws CryptoException;
	void setK(short K);
	short getField(byte[] buffer, short offset) throws CryptoException;
	short getA(byte[] buffer, short offset) throws CryptoException;
	short getB(byte[] buffer, short offset) throws CryptoException;
	short getG(byte[] buffer, short offset) throws CryptoException;
	short getR(byte[] buffer, short offset) throws CryptoException;
	short getK() throws CryptoException;
}
