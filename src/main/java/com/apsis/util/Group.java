package com.apsis.util;

import java.util.ArrayList;
import java.util.Objects;

/**
 * An ArrayList wrapper built to help eliminate Concurrency Exceptions
 *
 * @author Cal Stephens
 * @param <T> the type of object contained in the group
 */
public class Group<T extends Object> {

	private final ArrayList<T> content = new ArrayList<>();

	/**
	 * Creates an empty group
	 */
	public Group() {
	}

	/**
	 * Creates a Group containing the given objects..<br/>
	 * Calls {@link Group#add(java.lang.Object...) }
	 *
	 * @param objects the objects to initialize the group with
	 */
	public Group(T... objects) {
		add(objects);
	}

	/**
	 * Adds the given objects to the group
	 *
	 * @param objects the objects to add
	 */
	public synchronized void add(T... objects) {
		for (T object : objects) {
			content.add(object);
			if (object instanceof Groupable) {
				if (!((Groupable) object).addedTo((Group<Groupable>) this)) content.remove(object);
			}
		}
	}

	/**
	 * Creates a shallow copy of the internal list of group members
	 * and returns it.<br/>
	 * Modifications to the returned list will have <b><i>no</b></i>
	 * effect on the Group itself.
	 * Modifications to the objects inside the list itself
	 * <b><i>will</i></b> affect the objects inside of the Group.
	 *
	 * @return a shallow copy of the internal list
	 */
	public synchronized ArrayList<T> getContent() {
		return (ArrayList<T>) content.clone();
	}

	/**
	 * <b>Not recommended for use, proceed with caution.</b><br/>
	 * Returns the ArrayList this group is wrapped around.<br/>
	 * Modifications to the returned list affect the Group's list as well.<br/>
	 * (In that they are the same object).<br/>
	 * <b>There is no protection against Concurrency Exceptions.</b>
	 *
	 * @return The Group's internal Array List
	 * @deprecated Since Apsis 1.0
	 * @see Group#getContent()
	 */
	@Deprecated
	public ArrayList<T> getInternalContentArray() {
		return content;
	}

	/**
	 * Calls runOnAll() in the supplied Runner using a
	 * copy of the internal ArrayList as returned by
	 * {@link Group#getContent() getContent()}.
	 *
	 * @param runner the runner to run using
	 * @param args the arguments of the method
	 * @return the returned Object array as specified by Runner.runOnAll()
	 * @see Runner#runOnAll(java.util.List, java.lang.Object...)
	 */
	public Object[] runOnAll(Runner<T> runner, Object... args) {
		return runner.runOnAll(getContent(), args);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 43 * hash + Objects.hashCode(this.content);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final Group<?> other = (Group<?>) obj;
		return Objects.equals(this.content, other.content);
	}

}
