package javacard.security;

public interface KoreanSEEDKey extends SecretKey {
	byte getKey(byte[] keyData, short kOff);
	void setKey(byte[] keyData, short kOff);
}
