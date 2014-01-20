
package com.apsis.util;

/**
 * @author Cal Stephens
 */
public interface Groupable {

	/**
	 * Will be called when the object is added to a Group.
	 * 
	 * @param group the Group the object is being added to
	 * @return false if the operation should be canceled
	 */
	public boolean addedTo(Group<? extends Groupable> group);
	
	/**
	 * Will be called when the object is removed from a Group.
	 * 
	 * @param group the Group that the object is being removed from
	 */
	public void removedFrom(Group<? extends Groupable> group);
	
}
