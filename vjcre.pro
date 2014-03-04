-injars build
-injars lib/bcpkix-jdk15on-150.jar(!META-INF/*)
-injars lib/kryo-2.23.0.jar(!META-INF/*)
-injars lib/objenesis-2.1.jar(!META-INF/*)

-libraryjars lib/bcprov-jdk15on-150.jar
-libraryjars <java.home>/lib/rt.jar:<java.home>/lib/jce.jar

-outjars optimized-vjcre.jar
-dontobfuscate
-dontoptimize
-keep public class pro.javacard.** { public <methods>; public <fields>; }
-keep public class javacard.** { public <methods>; public <fields>; }
-keep public class javacardx.** { public <methods>; public <fields>; }
-keep public class visa.** { public <methods>; public <fields>; }
-printseeds
-dontnote
-ignorewarnings
