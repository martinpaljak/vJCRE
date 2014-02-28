# import pro.javacard.VRE;
> Focus on running **existing code** in a **modern container**, instead of emulating an old environment to the dumbest details

* Interfacing with any APDU interface, like
 * CommandAPDU from [javax.smartcardio](http://docs.oracle.com/javase/7/docs/jre/api/security/smartcardio/spec/javax/smartcardio/package-summary.html)
 * Generic byte[] exchange, like [Android Host Card Emulation](http://developer.android.com/guide/topics/connectivity/nfc/hce.html)
* JavaCard 2.2.2+ (Extended APDU)
* [HostApduService](https://developer.android.com/reference/android/nfc/cardemulation/HostApduService.html) proxy for Android
* Applet management interface, not unlike GlobalPlatform
* Simple, thin and modular codebase to the finest extent possible.

# Usage

        import pro.javacard.*;
        
        VRE vre = new VRE();
        AID aid = new AID(FakeEstEIDApplet.aid);
        vre.load(FakeEstEIDApplet.class, aid);
        vre.install(aid);
        Security.addProvider(new SmartCardIOProvider());
        TerminalFactory tf = TerminalFactory.getInstance("PC/SC", vre, SmartCardIOProvider.PROVIDER_NAME);
        CardTerminals terms = tf.terminals();


#### Similar projects
* jCardSim - https://github.com/licel/jcardsim
 * JavaCard 2.2.1, T=0 only
 * Apache license (JC API classes?)

----
[javacard.pro](http://javacard.pro)
