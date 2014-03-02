package javacard.security;

public interface SignatureMessageRecovery {
	short beginVerify(byte[] sigAndRecDataBuff, short buffOffset, short sigLength) throws CryptoException;
	byte getAlgorithm();
	short getLength() throws CryptoException;
	void init(Key theKey, byte theMode) throws CryptoException;
	short sign(byte[] inBuff, short inOffset, short inLength, byte[] sigBuff, short sigOffset, short[] recMsgLen, short recMsgLenOffset) throws CryptoException;
	void update(byte[] inBuff, short inOffset, short inLength) throws CryptoException;
	boolean verify(byte[] inBuff, short inOffset, short inLength) throws CryptoException;
}
