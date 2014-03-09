package pro.javacard.vre;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAKeyGenParameterSpec;

import javacard.security.CryptoException;
import javacard.security.KeyBuilder;
import javacard.security.KeyPair;
import javacard.security.PrivateKey;
import javacard.security.PublicKey;

public class vKeyPair {
	PrivateKey priv;
	PublicKey pub;

	protected vKeyPair() {}

	public final void genKeyPair() throws CryptoException {
		try {
			// Generate a key and copy over the material.
			if (pub.getType() == KeyBuilder.TYPE_RSA_PUBLIC) {
				KeyPairGenerator keyGen;
				keyGen = KeyPairGenerator.getInstance("RSA", VRE.provider);
				vRSAPublicKey vpubk = (vRSAPublicKey) pub;
				if (vpubk.publicExponent != BigInteger.ZERO) {
					keyGen.initialize(new RSAKeyGenParameterSpec(vpubk.getSize(), vpubk.publicExponent));
				} else {
					keyGen.initialize(vpubk.getSize());
				}
				java.security.KeyPair kp = keyGen.generateKeyPair();
				RSAPublicKey pubk = (RSAPublicKey)kp.getPublic();
				vpubk.publicExponent = pubk.getPublicExponent();
				vpubk.modulus = pubk.getModulus();
				if (priv.getType() == KeyBuilder.TYPE_RSA_CRT_PRIVATE) {
					vRSAPrivateCrtKey vprivk = (vRSAPrivateCrtKey) priv;
					RSAPrivateCrtKey privk = (RSAPrivateCrtKey)kp.getPrivate();
					vprivk.p = privk.getPrimeP();
					vprivk.q = privk.getPrimeQ();
					vprivk.dp1 = privk.getPrimeExponentP();
					vprivk.dq1 = privk.getPrimeExponentQ();
					vprivk.pq = privk.getCrtCoefficient();
				} else if (priv.getType() == KeyBuilder.TYPE_RSA_PRIVATE) {
					vRSAPrivateKey vprivk = (vRSAPrivateKey) priv;
					RSAPrivateKey privk = (RSAPrivateKey)kp.getPrivate();
					vprivk.privateExponent = privk.getPrivateExponent();
					vprivk.modulus = privk.getModulus();
				} else {
					throw new CryptoException(CryptoException.NO_SUCH_ALGORITHM);
				}
			}
		} catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException e) {
			throw new CryptoException(CryptoException.NO_SUCH_ALGORITHM);
		}
	}

	public vKeyPair(byte algorithm, short keyLength) throws CryptoException {
		if (algorithm == KeyPair.ALG_RSA) {
			priv = (PrivateKey) KeyBuilder.buildKey(KeyBuilder.TYPE_RSA_PRIVATE, keyLength, false);
			pub = (PublicKey) KeyBuilder.buildKey(KeyBuilder.TYPE_RSA_PUBLIC, keyLength, false);
		} else if (algorithm == KeyPair.ALG_RSA_CRT) {
			priv = (PrivateKey) KeyBuilder.buildKey(KeyBuilder.TYPE_RSA_CRT_PRIVATE, keyLength, false);
			pub = (PublicKey) KeyBuilder.buildKey(KeyBuilder.TYPE_RSA_PUBLIC, keyLength, false);
		} else {
			throw new CryptoException(CryptoException.NO_SUCH_ALGORITHM);
		}
	}


	public vKeyPair(PublicKey publicKey, PrivateKey privateKey) throws CryptoException {
		if (publicKey == null || privateKey == null) {
			CryptoException.throwIt(CryptoException.ILLEGAL_VALUE);
		}
		if ((publicKey.getType() != privateKey.getType()) || (publicKey.getSize() != privateKey.getSize())) {
			CryptoException.throwIt(CryptoException.ILLEGAL_VALUE);
		}
		priv = privateKey;
		pub = publicKey;
	}


	public PublicKey getPublic() {
		return pub;
	}

	public PrivateKey getPrivate() {
		return priv;
	}
}
