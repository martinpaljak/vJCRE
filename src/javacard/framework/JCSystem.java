package javacard.framework;

import pro.javacard.vre.VRE;

public final class JCSystem extends VRE {
	public static final byte MEMORY_TYPE_PERSISTENT = 0;
	public static final byte MEMORY_TYPE_TRANSIENT_RESET = 1;
	public static final byte MEMORY_TYPE_TRANSIENT_DESELECT = 2;
	public static final byte NOT_A_TRANSIENT_OBJECT = 0;
	public static final byte CLEAR_ON_RESET = 1;
	public static final byte CLEAR_ON_DESELECT = 2;
}
