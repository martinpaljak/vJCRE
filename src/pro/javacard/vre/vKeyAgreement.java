package pro.javacard.vre;

import javacard.security.CryptoException;
import javacard.security.KeyAgreement;
import javacard.security.PrivateKey;

public class vKeyAgreement extends KeyAgreement {
	public vKeyAgreement(byte algorithm) {
		// nada
	}

	@Override
	public void init(PrivateKey privateKey) throws CryptoException {
		// TODO Auto-generated method stub

	}

	@Override
	public byte getAlgorithm() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public short generateSecret(byte[] publicData, short publicOffset,
			short publicLength, byte[] secret, short secretOffset)
					throws CryptoException {
		// TODO Auto-generated method stub
		return 0;
	}
}
