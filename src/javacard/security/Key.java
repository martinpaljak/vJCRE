package javacard.security;

public interface Key {
	void clearKey();
	short getSize();
	byte getType();
	boolean isInitialized();
}
