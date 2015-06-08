package org.gwtbootstrap3.extras.datetimepicker.client.ui.base.events;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.Event;

/**
 * @author Unimax
 */
public class ErrorEvent extends GwtEvent<ErrorHandler> {

    private static final Type<ErrorHandler> TYPE = new Type<ErrorHandler>();

    private final Event nativeEvent;

    public static Type<ErrorHandler> getType() {
        return TYPE;
    }

    public ErrorEvent(final Event nativeEvent) {
        this.nativeEvent = nativeEvent;
    }

    public Event getNativeEvent() {
        return nativeEvent;
    }

    @Override
    public Type<ErrorHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(final ErrorHandler handler) {
        handler.onError(this);
    }
}
