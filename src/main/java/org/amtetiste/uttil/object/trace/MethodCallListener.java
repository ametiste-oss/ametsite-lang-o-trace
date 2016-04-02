package org.amtetiste.uttil.object.trace;

/**
 *
 * <p>
 *     Objects that provides this interface can be subscribed to receive
 *     events about method calls during an object trace scaning.
 * </p>
 *
 * @since 0.1.0
 */
public interface MethodCallListener {

    void methodCalled(MethodCallEvent methodCallEvent);

}
