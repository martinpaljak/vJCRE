package javacard.security;

import pro.javacard.vre.vKey;

public final class KeyBuilder {

	public static final byte TYPE_DES_TRANSIENT_RESET = 1;
	public static final byte TYPE_DES_TRANSIENT_DESELECT = 2;
	public static final byte TYPE_DES = 3;
	public static final byte TYPE_RSA_PUBLIC = 4;
	public static final byte TYPE_RSA_PRIVATE = 5;
	public static final byte TYPE_RSA_CRT_PRIVATE = 6;
	public static final byte TYPE_DSA_PUBLIC = 7;
	public static final byte TYPE_DSA_PRIVATE = 8;
	public static final byte TYPE_EC_F2M_PUBLIC = 9;
	public static final byte TYPE_EC_F2M_PRIVATE = 10;
	public static final byte TYPE_EC_FP_PUBLIC = 11;
	public static final byte TYPE_EC_FP_PRIVATE = 12;
	public static final byte TYPE_AES_TRANSIENT_RESET = 13;
	public static final byte TYPE_AES_TRANSIENT_DESELECT = 14;
	public static final byte TYPE_AES = 15;
	public static final byte TYPE_KOREAN_SEED_TRANSIENT_RESET = 16;
	public static final byte TYPE_KOREAN_SEED_TRANSIENT_DESELECT = 17;
	public static final byte TYPE_KOREAN_SEED = 18;
	public static final byte TYPE_HMAC_TRANSIENT_RESET = 19;
	public static final byte TYPE_HMAC_TRANSIENT_DESELECT = 20;
	public static final byte TYPE_HMAC = 21;
	// 3.0.1
	public static final byte TYPE_RSA_PRIVATE_TRANSIENT_RESET = 22;
	public static final byte TYPE_RSA_PRIVATE_TRANSIENT_DESELECT = 23;
	public static final byte TYPE_RSA_CRT_PRIVATE_TRANSIENT_RESET = 24;
	public static final byte TYPE_RSA_CRT_PRIVATE_TRANSIENT_DESELECT = 25;
	public static final byte TYPE_DSA_PRIVATE_TRANSIENT_RESET = 26;
	public static final byte TYPE_DSA_PRIVATE_TRANSIENT_DESELECT = 27;
	public static final byte TYPE_EC_F2M_PRIVATE_TRANSIENT_RESET = 28;
	public static final byte TYPE_EC_F2M_PRIVATE_TRANSIENT_DESELECT = 29;
	public static final byte TYPE_EC_FP_PRIVATE_TRANSIENT_RESET = 30;
	public static final byte TYPE_EC_FP_PRIVATE_TRANSIENT_DESELECT = 31;

	public static final short LENGTH_DES = 64;
	public static final short LENGTH_DES3_2KEY = 128;
	public static final short LENGTH_DES3_3KEY = 192;
	public static final short LENGTH_RSA_512 = 512;
	public static final short LENGTH_RSA_736 = 736;
	public static final short LENGTH_RSA_768 = 768;
	public static final short LENGTH_RSA_896 = 896;
	public static final short LENGTH_RSA_1024 = 1024;
	public static final short LENGTH_RSA_1280 = 1280;
	public static final short LENGTH_RSA_1536 = 1536;
	public static final short LENGTH_RSA_1984 = 1984;
	public static final short LENGTH_RSA_2048 = 2048;
	public static final short LENGTH_DSA_512 = 512;
	public static final short LENGTH_DSA_768 = 768;
	public static final short LENGTH_DSA_1024 = 1024;
	public static final short LENGTH_EC_FP_112 = 112;
	public static final short LENGTH_EC_F2M_113 = 113;
	public static final short LENGTH_EC_FP_128 = 128;
	public static final short LENGTH_EC_F2M_131 = 131;
	public static final short LENGTH_EC_FP_160 = 160;
	public static final short LENGTH_EC_F2M_163 = 163;
	public static final short LENGTH_EC_FP_192 = 192;
	public static final short LENGTH_EC_F2M_193 = 193;
	public static final short LENGTH_AES_128 = 128;
	public static final short LENGTH_AES_192 = 192;
	public static final short LENGTH_AES_256 = 256;
	public static final short LENGTH_HMAC_SHA_1_BLOCK_64 = 64;
	public static final short LENGTH_HMAC_SHA_256_BLOCK_64 = 64;
	public static final short LENGTH_HMAC_SHA_384_BLOCK_128 = 128;
	public static final short LENGTH_HMAC_SHA_512_BLOCK_128 = 128;
	public static final short LENGTH_KOREAN_SEED_128 = 128;
	// 3.0.1
	public static final short LENGTH_EC_FP_224 = 224;
	public static final short LENGTH_EC_FP_256 = 256;
	public static final short LENGTH_EC_FP_384 = 384;
	public static final short LENGTH_RSA_3072 = 3072;
	public static final short LENGTH_RSA_4096 = 4096;
	// 3.0.4
	public static final short LENGTH_EC_FP_521 = 521;

	// 3.0.4
	public static final byte ALG_TYPE_DES = 1;
	public static final byte ALG_TYPE_AES = 2;
	public static final byte ALG_TYPE_DSA_PUBLIC = 3;
	public static final byte ALG_TYPE_DSA_PRIVATE = 4;
	public static final byte ALG_TYPE_EC_F2M_PUBLIC = 5;
	public static final byte ALG_TYPE_EC_F2M_PRIVATE = 6;
	public static final byte ALG_TYPE_EC_FP_PUBLIC = 7;
	public static final byte ALG_TYPE_EC_FP_PRIVATE = 8;
	public static final byte ALG_TYPE_HMAC = 9;
	public static final byte ALG_TYPE_KOREAN_SEED = 10;
	public static final byte ALG_TYPE_RSA_PUBLIC = 11;
	public static final byte ALG_TYPE_RSA_PRIVATE = 12;
	public static final byte ALG_TYPE_RSA_CRT_PRIVATE = 13;

	public static Key buildKey(byte keyType, short keyLength, boolean keyEncryption) throws CryptoException {
		if (keyEncryption)
			CryptoException.throwIt(CryptoException.NO_SUCH_ALGORITHM);
		return vKey.buildKey(keyType, keyLength);
	}
	// 3.0.4
	public static Key buildKey(byte algorithmicKeyType, byte keyMemoryType, short keyLength, boolean keyEncryption) throws CryptoException {
		CryptoException.throwIt(CryptoException.NO_SUCH_ALGORITHM);
		return null;
	}
}