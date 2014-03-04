package visa.openplatform;

import javacard.framework.APDU;

public interface ProviderSecurityDomain {
	void closeSecureChannel(byte channel);
	boolean decryptVerifyKey(byte channel, APDU apdu, short offset);
	byte openSecureChannel(APDU apdu);
	void unwrap(byte channel, APDU apdu);
	void verifyExternalAuthenticate(byte channel, APDU apdu);
}
