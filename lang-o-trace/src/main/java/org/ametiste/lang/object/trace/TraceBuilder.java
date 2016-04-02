package org.ametiste.lang.object.trace;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @since
 */
public class TraceBuilder {

    private List<String> element = new ArrayList<>();

    // TODO: extract to interface
    public void addTraceElement(String element) {
        this.element.add(element);
    }

    // TODO: specific implementation method
    public String toUri() {
        return String.join("/", element);
    }

}
