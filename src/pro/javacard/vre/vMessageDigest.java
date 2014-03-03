package pro.javacard.vre;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;

import javacard.security.CryptoException;

public class vMessageDigest extends javacard.security.MessageDigest {
	private byte algorithm = 0;
	private MessageDigest digest;
	public vMessageDigest(byte algorithm) {
		try {
			switch (algorithm) {
			case ALG_MD5:
				digest = MessageDigest.getInstance("MD5", VRE.provider);
				break;
			case ALG_SHA:
				digest = MessageDigest.getInstance("SHA-1", VRE.provider);
				break;
			case ALG_SHA_256:
				digest = MessageDigest.getInstance("SHA-256", VRE.provider);
				break;
			case ALG_SHA_384:
				digest = MessageDigest.getInstance("SHA-384", VRE.provider);
				break;
			case ALG_SHA_512:
				digest = MessageDigest.getInstance("SHA-512", VRE.provider);
				break;
			case ALG_SHA_224:
				digest = MessageDigest.getInstance("SHA-224", VRE.provider);
				break;
			default:
				CryptoException.throwIt(CryptoException.NO_SUCH_ALGORITHM);
				break;
			}
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			CryptoException.throwIt(CryptoException.NO_SUCH_ALGORITHM);
		}
	}

	public byte getAlgorithm() {
		return algorithm;
	}

	public byte getLength() {
		switch (algorithm) {
		case ALG_MD5:
			return LENGTH_MD5;
		case ALG_RIPEMD160:
			return LENGTH_RIPEMD160;
		case ALG_SHA:
			return LENGTH_SHA;
		case ALG_SHA_256:
			return LENGTH_SHA_256;
		case ALG_SHA_384:
			return LENGTH_SHA_384;
		case ALG_SHA_512:
			return LENGTH_SHA_512;
		case ALG_SHA_224:
			return LENGTH_SHA_224;
		default:
			return 0;
		}
	}

	public short doFinal(byte[] inBuff, short inOffset, short inLength, byte[] outBuff, short outOffset) {
		byte [] result = digest.digest(Arrays.copyOfRange(inBuff, inOffset, inOffset +inLength));
		System.arraycopy(result, 0, outBuff, outOffset, result.length);
		return (short) result.length;
	}

	public void update(byte[] inBuff, short inOffset, short inLength) {
		digest.update(inBuff, inOffset, inLength);
	}

	public void reset() {
		digest.reset();
	}
}
