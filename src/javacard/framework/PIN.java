package javacard.framework;

public interface PIN {
	byte getTriesRemaining();
	boolean check(byte pin[], short offset, byte length) throws ArrayIndexOutOfBoundsException, NullPointerException;
	boolean isValidated();
	void reset();
}
