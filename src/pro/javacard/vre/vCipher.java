package pro.javacard.vre;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javacard.security.CryptoException;
import javacard.security.Key;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;

public final class vCipher extends javacardx.crypto.Cipher {

	byte algorithm = 0;
	boolean isInitialized = false;
	Cipher cipher = null;

	public vCipher(byte algorithm) {
		this.algorithm = algorithm;
		try {
			switch (algorithm) {
			case ALG_RSA_NOPAD:
				cipher = javax.crypto.Cipher.getInstance("RSA/ECB/NoPadding", VRE.provider);
				break;
			case ALG_RSA_PKCS1:
				cipher = javax.crypto.Cipher.getInstance("RSA/ECB/PKCS1Padding", VRE.provider);
				break;
			default:
				CryptoException.throwIt(CryptoException.NO_SUCH_ALGORITHM);
				break;
			}
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | NoSuchProviderException e) {
			e.printStackTrace();
			CryptoException.throwIt(CryptoException.NO_SUCH_ALGORITHM);
		}
	}

	public void init(Key theKey, byte theMode) throws CryptoException {
		if (theKey == null) {
			CryptoException.throwIt(CryptoException.UNINITIALIZED_KEY);
		}
		if (!theKey.isInitialized()) {
			CryptoException.throwIt(CryptoException.UNINITIALIZED_KEY);
		}

		try {
			int mode = 0;
			if (theMode == MODE_ENCRYPT)
				mode = Cipher.ENCRYPT_MODE;
			else if (theMode == MODE_DECRYPT)
				mode = Cipher.DECRYPT_MODE;
			else
				CryptoException.throwIt(CryptoException.ILLEGAL_VALUE);
			cipher.init(mode, ((vKey) theKey).jce());
		} catch (InvalidKeyException e) {
			if (VRE.debugMode) {
				e.printStackTrace();
			}
			CryptoException.throwIt(CryptoException.ILLEGAL_VALUE);
		}
		isInitialized = true;
	}

	public void init(Key theKey, byte theMode, byte[] bArray, short bOff, short bLen) throws CryptoException {
		CryptoException.throwIt(CryptoException.ILLEGAL_VALUE);
	}

	public byte getAlgorithm() {
		return algorithm;
	}

	public short doFinal(byte[] inBuff, short inOffset, short inLength, byte[] outBuff, short outOffset) throws CryptoException {
		if (!isInitialized) {
			CryptoException.throwIt(CryptoException.INVALID_INIT);
		}
		short result = 0;
		try {
			result = (short) cipher.doFinal(inBuff, inOffset, inLength, outBuff, outOffset);
		} catch (ShortBufferException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
			CryptoException.throwIt(CryptoException.ILLEGAL_USE);
		}
		return result;
	}

	public short update(byte[] inBuff, short inOffset, short inLength, byte[] outBuff, short outOffset) throws CryptoException {
		if (!isInitialized) {
			CryptoException.throwIt(CryptoException.INVALID_INIT);
		}
		short result = 0;
		try {
			result = (short) cipher.update(inBuff, inOffset, inLength, outBuff, outOffset);
		} catch (ShortBufferException e) {
			e.printStackTrace();
			CryptoException.throwIt(CryptoException.ILLEGAL_USE);
		}
		return result;
	}
}
