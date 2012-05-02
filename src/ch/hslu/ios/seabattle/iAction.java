package ch.hslu.ios.seabattle;

/**
 * Generics interface which will perform an action with one parameter.
 * @author Thomas Bomatter
 * @param <T> The parameter of the action
 */
public interface iAction<T> {

	/**
	 * Performs an action.
	 * @param data The data used to perform the action
	 */
	public void perform(T data);
}
