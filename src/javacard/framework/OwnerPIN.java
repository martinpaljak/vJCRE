package javacard.framework;

public class OwnerPIN implements PIN {
	private byte remaining = 0;
	private byte limit = 0;
	private byte[] value;
	private transient boolean validated = false;

	public OwnerPIN(byte tryLimit, byte maxPINSize) {
		value = new byte[1+maxPINSize];
	}
	public byte getTriesRemaining() {
		return remaining;
	}

	public boolean check(byte[] pin, short offset, byte length) throws ArrayIndexOutOfBoundsException, NullPointerException {
		if (remaining == 0)
			return false;
		remaining = (byte) (remaining - 1);
		setValidatedFlag(false);

		if (value[0] != length)
			return false;

		if (Util.arrayCompare(pin, (short) 0, value, (short) 1, length) == 0) {
			setValidatedFlag(true);
			remaining = (byte) (remaining + 1); // Restore
			return true;
		}
		return false;
	}

	public boolean isValidated() {
		return validated;
	}

	protected boolean getValidatedFlag() {
		return validated;
	}

	protected void setValidatedFlag(boolean value) {
		validated = false;
	}

	public void reset() {
		if (getValidatedFlag()) {
			setValidatedFlag(false);
			remaining = limit;
		}
	}
	public void update(byte[] pin, short offset, byte length) throws PINException {
		if (length > value.length + 1)
			PINException.throwIt(PINException.ILLEGAL_VALUE);
		value[0] = length;
		Util.arrayCopy(pin, (short)0, value, (short)1, length);
		setValidatedFlag(false);
	}

	public void resetAndUnblock() {
		setValidatedFlag(false);
		remaining = limit;
	}
}
