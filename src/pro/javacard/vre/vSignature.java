package pro.javacard.vre;

import javacard.security.CryptoException;
import javacard.security.Key;
import javacard.security.Signature;


public class vSignature extends Signature {

	public vSignature(byte algorithm) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(Key theKey, byte theMode) throws CryptoException {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(Key theKey, byte theMode, byte[] bArray, short bOff,
			short bLen) throws CryptoException {
		// TODO Auto-generated method stub

	}

	@Override
	public short getLength() throws CryptoException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte getAlgorithm() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(byte[] inBuff, short inOffset, short inLength)
			throws CryptoException {
		// TODO Auto-generated method stub

	}

	@Override
	public short sign(byte[] inBuff, short inOffset, short inLength,
			byte[] sigBuff, short sigOffset) throws CryptoException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean verify(byte[] inBuff, short inOffset, short inLength,
			byte[] sigBuff, short sigOffset, short sigLength)
					throws CryptoException {
		// TODO Auto-generated method stub
		return false;
	}

}
