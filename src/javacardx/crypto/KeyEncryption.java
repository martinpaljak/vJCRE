package javacardx.crypto;

public interface KeyEncryption {
	void setKeyCipher(Cipher keyCipher);
	Cipher getKeyCipher();
}
