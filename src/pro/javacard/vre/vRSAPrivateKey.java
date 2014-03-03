package pro.javacard.vre;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;

import javacard.security.CryptoException;

public class vRSAPrivateKey extends vKey implements javacard.security.RSAPrivateKey {
	protected BigInteger privateExponent;
	protected BigInteger modulus;

	public void clearKey() {
		modulus = BigInteger.ZERO;
		privateExponent = BigInteger.ZERO;
	}

	public boolean isInitialized() {
		return (!modulus.equals(BigInteger.ZERO) && !privateExponent.equals(BigInteger.ZERO));
	}

	public short getExponent(byte[] buffer, short offset) throws CryptoException {
		return send_positive_bigint(buffer, offset, privateExponent);
	}

	public short getModulus(byte[] buffer, short offset) throws CryptoException {
		return send_positive_bigint(buffer, offset, modulus);
	}

	public void setExponent(byte[] buffer, short offset, short length) throws CryptoException {
		privateExponent = get_positive_bigint(buffer, offset, length);
	}

	public void setModulus(byte[] buffer, short offset, short length) throws CryptoException {
		modulus = get_positive_bigint(buffer, offset, length);
	}

	protected java.security.interfaces.RSAPrivateKey jce() {
		try {
			RSAPrivateKeySpec spec = new RSAPrivateKeySpec(modulus, privateExponent);
			KeyFactory kf = KeyFactory.getInstance("RSA", VRE.provider);
			RSAPrivateKey k = (RSAPrivateKey) kf.generatePrivate(spec);
			System.out.println(k.getClass().getCanonicalName());
			return k;
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchProviderException e) {
			throw new RuntimeException(e);
		}
	}
}
