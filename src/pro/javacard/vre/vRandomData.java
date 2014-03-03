package pro.javacard.vre;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Arrays;

import javacard.security.CryptoException;

public class vRandomData extends javacard.security.RandomData {
	private SecureRandom rnd = null;

	public vRandomData(byte algorithm) {
		if (algorithm != ALG_PSEUDO_RANDOM && algorithm != ALG_SECURE_RANDOM) {
			CryptoException.throwIt(CryptoException.NO_SUCH_ALGORITHM);
		}
		try {
			rnd = SecureRandom.getInstance("SHA1PRNG", VRE.provider);
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			CryptoException.throwIt(CryptoException.NO_SUCH_ALGORITHM);
		}
	}

	public void generateData(byte[] buffer, short offset, short length) throws CryptoException {
		byte[] d = new byte[length];
		rnd.nextBytes(d);
		System.arraycopy(d, 0, buffer, offset, length);
	}

	public void setSeed(byte[] buffer, short offset, short length) {
		rnd.setSeed(Arrays.copyOfRange(buffer, offset, offset+length));
	}
}
