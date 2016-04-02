package org.amtetiste.util.object.trace;

import java.lang.reflect.Method;

/**
 *
 * <p>
 *     These event objects will be passed to each subscribed {@link MethodCallListener},
 *     when the method call is reached during scan of traced object.
 * </p>
 *
 * <p>
 *     Event provides information about method name and arguments.
 * </p>
 *
 * @since 0.1.0
 */
public class MethodCallEvent {

    private final Method method;

    private final Object[] args;

    public MethodCallEvent(Method method, Object[] args) {
        this.method = method;
        this.args = args;
    }

    public Method method() {
        return method;
    }

    public Object[] args() {
        return args;
    }

}
