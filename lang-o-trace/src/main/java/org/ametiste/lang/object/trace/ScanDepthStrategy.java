package org.ametiste.lang.object.trace;

import java.lang.reflect.Method;

/**
 *
 * @since
 */
public interface ScanDepthStrategy {

    /**
     * Depth strategy instance that does not provide further scaning.
     */
    ScanDepthStrategy NOT_DEEP = new ScanDepthStrategy() {
        @Override
        public Object deeperScanFor(Method method, Class<?> returnType) {
            return null;
        }
    };

    Object deeperScanFor(Method method, Class<?> returnType);

}
