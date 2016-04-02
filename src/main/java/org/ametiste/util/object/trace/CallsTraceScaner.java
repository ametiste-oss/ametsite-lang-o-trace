package org.ametiste.util.object.trace;

import java.util.List;

/**
 *
 * @since
 */
public class CallsTraceScaner<T> {

    private final Class<T> tClass;
    private final List<MethodCallListener> listeners;
    private final ScanDepthStrategy scanDepthStrategy;

    public CallsTraceScaner(Class<T> tClass, List<MethodCallListener> listeners, ScanDepthStrategy scanDepthStrategy) {
        this.tClass = tClass;
        this.listeners = listeners;
        this.scanDepthStrategy = scanDepthStrategy;
    }

    public CallsTraceScaner(Class<T> tClass, List<MethodCallListener> listeners) {
        this(tClass, listeners, ScanDepthStrategy.NOT_DEEP);
    }

    public Trace<T> createTrace() {
        return new Trace<>(
            ScanInterceptor.createProxy(tClass, listeners, scanDepthStrategy)
        );
    }

}
