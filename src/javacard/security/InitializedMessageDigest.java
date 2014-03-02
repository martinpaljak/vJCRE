package javacard.security;


public abstract class InitializedMessageDigest extends MessageDigest {
	abstract void setInitialDigest(byte[] initialDigestBuf, short initialDigestOffset, short initialDigestLength, byte[] digestedMsgLenBuf, short digestedMsgLenOffset, short digestedMsgLenLength) throws CryptoException;
}
