package fengfei.ucm.registry;

import java.util.Map;

/**
 *
 */

public interface Invocation {

	/**
	 * get method name.
	 * 
	 * @serial
	 * @return method name.
	 */
	String getMethodName();

	/**
	 * get parameter types.
	 * 
	 * @serial
	 * @return parameter types.
	 */
	Class<?>[] getParameterTypes();

	/**
	 * get arguments.
	 * 
	 * @serial
	 * @return arguments.
	 */
	Object[] getArguments();

	/**
	 * get attachments.
	 * 
	 * @serial
	 * @return attachments.
	 */
	Map<String, Object> getAttachments();
	
	/**
     * get attachment by key.
     * 
     * @serial
     * @return attachment value.
     */
	<T> T getAttachment(String key);
	
	/**
     * get attachment by key with default value.
     * 
     * @serial
     * @return attachment value.
     */
    <T> T getAttachment(String key, T defaultValue);

    /**
     * get the invoker in current context.
     * 
     * @transient
     * @return invoker.
     */
    Invoker<?> getInvoker();

}