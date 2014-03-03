package pro.javacard.vre;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateCrtKeySpec;

import javacard.security.CryptoException;

public class vRSAPrivateCrtKey extends vRSAPrivateKey implements javacard.security.RSAPrivateCrtKey {

	protected BigInteger p;
	protected BigInteger q;
	protected BigInteger dp1;
	protected BigInteger dq1;
	protected BigInteger pq;

	public void clearKey() {
		super.clearKey();
		p = BigInteger.ZERO;
		q = BigInteger.ZERO;
		dp1 = BigInteger.ZERO;
		dq1 = BigInteger.ZERO;
		pq = BigInteger.ZERO;
	}

	public boolean isInitialized() {
		if (!p.equals(BigInteger.ZERO) && !q.equals(BigInteger.ZERO) && !dp1.equals(BigInteger.ZERO) && !dq1.equals(BigInteger.ZERO) && !pq.equals(BigInteger.ZERO))
			return true;
		return false;
	}

	public void setP(byte[] buffer, short offset, short length) throws CryptoException {
		p = get_positive_bigint(buffer, offset, length);
	}

	public void setQ(byte[] buffer, short offset, short length) throws CryptoException {
		q = get_positive_bigint(buffer, offset, length);
	}

	public void setDP1(byte[] buffer, short offset, short length) throws CryptoException {
		dp1 = get_positive_bigint(buffer, offset, length);
	}

	public void setDQ1(byte[] buffer, short offset, short length) throws CryptoException {
		dq1 = get_positive_bigint(buffer, offset, length);
	}

	public void setPQ(byte[] buffer, short offset, short length) throws CryptoException {
		pq = get_positive_bigint(buffer, offset, length);
	}

	public short getP(byte[] buffer, short offset) throws CryptoException {
		return send_positive_bigint(buffer, offset, p);
	}

	public short getQ(byte[] buffer, short offset) throws CryptoException {
		return send_positive_bigint(buffer, offset, q);
	}

	public short getDP1(byte[] buffer, short offset) throws CryptoException {
		return send_positive_bigint(buffer, offset, dp1);
	}

	public short getDQ1(byte[] buffer, short offset) throws CryptoException {
		return send_positive_bigint(buffer, offset, dq1);
	}

	public short getPQ(byte[] buffer, short offset) throws CryptoException {
		return send_positive_bigint(buffer, offset, pq);
	}

	protected RSAPrivateCrtKey jce() {
		try {
			RSAPrivateCrtKeySpec spec = new RSAPrivateCrtKeySpec(p.multiply(q), null, null, p, q, dp1, dq1, pq);
			KeyFactory kf = KeyFactory.getInstance("RSA", VRE.provider);
			RSAPrivateCrtKey k = (RSAPrivateCrtKey) kf.generatePrivate(spec);
			System.out.println(k.getClass().getCanonicalName());
			return k;
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchProviderException e) {
			throw new RuntimeException(e);
		}
	}
}
