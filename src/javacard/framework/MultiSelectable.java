package javacard.framework;

public interface MultiSelectable {
	boolean select(boolean appInstAlreadyActive);
	void deselect(boolean appInstStillActive);
}
