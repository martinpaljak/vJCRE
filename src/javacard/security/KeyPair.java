package javacard.security;

import pro.javacard.vre.vKeyPair;

public final class KeyPair extends vKeyPair {
	public static final byte ALG_RSA = 1;
	public static final byte ALG_RSA_CRT = 2;
	public static final byte ALG_DSA = 3;
	public static final byte ALG_EC_F2M = 4;
	public static final byte ALG_EC_FP = 5;

	public KeyPair(byte algorithm, short keyLength) {
		super(algorithm, keyLength);
	}

	public KeyPair(PublicKey publicKey, PrivateKey privateKey) {
		super(publicKey, privateKey);
	}
}