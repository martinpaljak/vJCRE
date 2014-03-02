package javacard.security;

import pro.javacard.vre.vKeyAgreement;


public abstract class KeyAgreement {
	public static final byte ALG_EC_SVDP_DH = 1;
	public static final byte ALG_EC_SVDP_DHC = 2;

	protected KeyAgreement() {
	}

	public static final KeyAgreement getInstance(byte algorithm, boolean externalAccess) throws CryptoException {
		if (externalAccess)
			CryptoException.throwIt(CryptoException.NO_SUCH_ALGORITHM);
		return new vKeyAgreement(algorithm);
	}

	public abstract void init(PrivateKey privateKey) throws CryptoException;
	public abstract byte getAlgorithm();
	public abstract short generateSecret(byte[] publicData, short publicOffset, short publicLength, byte[] secret, short secretOffset) throws CryptoException;

}
