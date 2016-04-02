package org.amtetiste.uttil.object.trace;

import java.util.function.Consumer;

/**
 *
 * @since
 */
public class Trace<T> {

    private final T tracedInstance;

    public Trace(T tracedInstance) {
        // TODO: need to check that is realy traced instance, but not usual class instance
        this.tracedInstance = tracedInstance;
    }

    public void recordTrace(Consumer<T> record) {
        record.accept(this.tracedInstance);
    }

}
