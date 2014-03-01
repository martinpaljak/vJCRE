# import pro.javacard.VRE;
> Focus on running **existing code** in a **modern container**, instead of emulating an old environment to the dumbest details.

* Easy interfacing with any APDU-like interface:
 * `CommandAPDU` from [javax.smartcardio](http://docs.oracle.com/javase/7/docs/jre/api/security/smartcardio/spec/javax/smartcardio/package-summary.html)
 * Generic `byte[]` exchange, like [Android Host Card Emulation](http://developer.android.com/guide/topics/connectivity/nfc/hce.html)
* JavaCard 2.2.2+, usage of Extended APDU encouraged
* [HostApduService](https://developer.android.com/reference/android/nfc/cardemulation/HostApduService.html) proxy for Android
* Applet management interface, not unlike [GlobalPlatform](https://github.com/martinpaljak/GlobalPlatform#globalplatform-from-openkms)
* Simple, thin and modular codebase to the finest extent possible.
  * Minimal one-liner pollution in `javacard*.*`. The only allowed import is from pro.javacard.vre.*;
  * No Javadoc-s in `javacard*.*`. Go read the original API docs.

# Usage

        import pro.javacard.*;
        
        VRE vre = new VRE();
        AID aid = new AID(FakeEstEIDApplet.aid);
        vre.load(FakeEstEIDApplet.class, aid); // Load the applet with the specified AID
        vre.install(aid, default); // Make a default selected instance of the applet with same AID
        Security.addProvider(new SmartCardIOProvider());
        TerminalFactory tf = TerminalFactory.getInstance("PC/SC", vre, SmartCardIOProvider.PROVIDER_NAME);
        CardTerminals terms = tf.terminals();
        // Now use javax.smartcardio as you would do when talking to a real JavaCard


#### Similar projects
* jCardSim - https://github.com/licel/jcardsim
 * JavaCard 2.2.1, T=0 only
 * Apache license (JC API classes?)

----
[javacard.pro](http://javacard.pro)
