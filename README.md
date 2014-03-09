# import pro.javacard.vre.*;
> Focus on running **existing code** in a **modern container**, instead of emulating an old environment to the dumbest details. Fully **open source**!

vJCRE is a vritual Java Card Runtime Environment that allows to run Java code targeting the smart card platform inside a standard desktop Java virtual machine. Ideal for fast development, testing or experimentation.

#### Jump to ...
 * [Download](#get-it-now)
 * [Contact](#contact)

# Features
* Easy interfacing with any APDU-like interface:
 * `CommandAPDU` from [javax.smartcardio](http://docs.oracle.com/javase/7/docs/jre/api/security/smartcardio/spec/javax/smartcardio/package-summary.html)
 * Generic `byte[]` exchange, like [Android Host Card Emulation](http://developer.android.com/guide/topics/connectivity/nfc/hce.html)
* JavaCard 2.2.2+, usage of Extended APDU encouraged
* [HostApduService](https://developer.android.com/reference/android/nfc/cardemulation/HostApduService.html) proxy for Android
* Includes all packages with essential interfaces and abstract classes up to JavaCard v3.0.4:
 * `javacard.framework.*`
 * `javacard.security.*`
 * `javacardx.apdu.*`
 * `javacardx.crypto.*`
* Simple, thin and modular codebase to the finest extent possible.
 * Minimal one-liner pollution in `javacard*.*`. The only allowed import is from pro.javacard.vre.*;
 * No Javadoc or license for API specification. Go read the original API docs. *([hint](http://www.win.tue.nl/pinpasjc/docs/apis/jc222/overview-summary.html))*
* Implements necessary JCSystem and APDU, cryptography provided either by standard JCE or [BouncyCastle](http://bouncycastle.org/java.html).
* Applet management interface, not unlike [GlobalPlatform](https://github.com/martinpaljak/GlobalPlatform#globalplatform-from-openkms)
* No support for:
 * Transactions
 * RMI
 * Services
 * Logical channels
* Future versions
 * More algorithms, including JavaCard 3.0.X features
 * Multiple independent VJCRE instances in a single VM
 * Saving and loading VM instances (real "virtual cards")
* Works great with the rest of the open source smart card stack.
* Made in Estonia 

# Usage

```java
import pro.javacard.*;
        
VRE vre = VRE.getInstance();
AID aid = new AID(FakeEstEIDApplet.aid);
vre.load(FakeEstEIDApplet.class, aid); // Load the applet with the specified AID
vre.install(aid, default); // Make a default selected instance of the applet with same AID
TerminalFactory tf = TerminalFactory.getInstance("PC/SC", vre, new VJCREProvider());
CardTerminals terms = tf.terminals();
// Now use javax.smartcardio as you would do when talking to a real smart card
```

# Get it now!
Version 0.1 will be released early March 2014 together with [FakeEstEID](https://github.com/martinpaljak/AppletPlayground/wiki/FakeEstEID) [Android version](https://github.com/martinpaljak/mobiil-idkaart#mobile-id-as-esteid-over-nfc). Until that time feel free to look around in the repository.

# Contact
* martin@martinpaljak.net

#### Similar projects
* jCardSim - https://github.com/licel/jcardsim
 * JavaCard 2.2.1, T=0 only
 * Apache license (Also in JC API classes???)
 * Currently implements more algorithms

#### Related projects
* AlgTest - https://github.com/petrs/AlgTest

----
[javacard.pro](http://javacard.pro)
