package pro.javacard.vre;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import javacard.security.CryptoException;

public class vRSAPublicKey extends vKey implements javacard.security.RSAPublicKey {

	protected BigInteger publicExponent;
	protected BigInteger modulus;

	public void clearKey() {
		publicExponent = BigInteger.ZERO;
		modulus = BigInteger.ZERO;
	}

	public boolean isInitialized() {
		return (!modulus.equals(BigInteger.ZERO) && !publicExponent.equals(BigInteger.ZERO));
	}

	public short getExponent(byte[] buffer, short offset) throws CryptoException {
		return send_positive_bigint(buffer, offset, publicExponent);
	}

	public short getModulus(byte[] buffer, short offset) throws CryptoException {
		return send_positive_bigint(buffer, offset, modulus);
	}

	public void setExponent(byte[] buffer, short offset, short length) throws CryptoException {
		publicExponent = get_positive_bigint(buffer, offset, length);
	}

	public void setModulus(byte[] buffer, short offset, short length) throws CryptoException {
		modulus = get_positive_bigint(buffer, offset, length);
	}

	protected RSAPublicKey jce() {
		try {
			RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, publicExponent);
			KeyFactory kf = KeyFactory.getInstance("RSA", VRE.provider);
			RSAPublicKey k = (RSAPublicKey) kf.generatePublic(spec);
			return k;
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchProviderException e) {
			throw new RuntimeException(e);
		}
	}
}
