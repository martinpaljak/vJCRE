package pro.javacard.vre;

import javacard.security.Checksum;
import javacard.security.CryptoException;

public class vChecksum extends Checksum {
	private byte algorithm;

	public vChecksum(byte algorithm) {
		this.algorithm = algorithm;
	}

	@Override
	public byte getAlgorithm() {
		return algorithm;
	}

	@Override
	public void init(byte[] bArray, short bOff, short bLen) throws CryptoException {
		// TODO Auto-generated method stub

	}

	@Override
	public short doFinal(byte[] inBuff, short inOffset, short inLength,
			byte[] outBuff, short outOffset) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(byte[] inBuff, short inOffset, short inLength) {
		// TODO Auto-generated method stub

	}

}
