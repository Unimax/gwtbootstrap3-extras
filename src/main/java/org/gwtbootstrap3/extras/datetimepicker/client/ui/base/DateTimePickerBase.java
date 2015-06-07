package org.gwtbootstrap3.extras.datetimepicker.client.ui.base;

/*
 * #%L
 * GwtBootstrap3
 * %%
 * Copyright (C) 2013 GwtBootstrap3
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.util.Date;

import org.gwtbootstrap3.client.shared.event.HideEvent;
import org.gwtbootstrap3.client.shared.event.HideHandler;
import org.gwtbootstrap3.client.shared.event.ShowEvent;
import org.gwtbootstrap3.client.shared.event.ShowHandler;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.client.ui.base.HasId;
import org.gwtbootstrap3.client.ui.base.HasPlaceholder;
import org.gwtbootstrap3.client.ui.base.HasResponsiveness;
import org.gwtbootstrap3.client.ui.base.ValueBoxBase;
import org.gwtbootstrap3.client.ui.base.helper.StyleHelper;
import org.gwtbootstrap3.client.ui.base.mixin.ErrorHandlerMixin;
import org.gwtbootstrap3.client.ui.constants.DeviceSize;
import org.gwtbootstrap3.extras.datetimepicker.client.ui.base.constants.DateTimePickerDayOfWeek;
import org.gwtbootstrap3.extras.datetimepicker.client.ui.base.constants.DateTimePickerLanguage;
import org.gwtbootstrap3.extras.datetimepicker.client.ui.base.constants.DateTimePickerPosition;
import org.gwtbootstrap3.extras.datetimepicker.client.ui.base.constants.DateTimePickerToolbarPosition;
import org.gwtbootstrap3.extras.datetimepicker.client.ui.base.constants.DateTimePickerView;
import org.gwtbootstrap3.extras.datetimepicker.client.ui.base.constants.HasDateTimePickerHandlers;
import org.gwtbootstrap3.extras.datetimepicker.client.ui.base.constants.HasDaysOfWeekDisabled;
import org.gwtbootstrap3.extras.datetimepicker.client.ui.base.constants.HasEndDate;
import org.gwtbootstrap3.extras.datetimepicker.client.ui.base.constants.HasFormat;
import org.gwtbootstrap3.extras.datetimepicker.client.ui.base.constants.HasLanguage;
import org.gwtbootstrap3.extras.datetimepicker.client.ui.base.constants.HasPosition;
import org.gwtbootstrap3.extras.datetimepicker.client.ui.base.constants.HasShowTodayButton;
import org.gwtbootstrap3.extras.datetimepicker.client.ui.base.constants.HasStartDate;
import org.gwtbootstrap3.extras.datetimepicker.client.ui.base.constants.HasStartView;
import org.gwtbootstrap3.extras.datetimepicker.client.ui.base.events.ChangeDateEvent;
import org.gwtbootstrap3.extras.datetimepicker.client.ui.base.events.ChangeDateHandler;
import org.gwtbootstrap3.extras.datetimepicker.client.ui.base.events.ErrorEvent;
import org.gwtbootstrap3.extras.datetimepicker.client.ui.base.events.ErrorHandler;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.editor.client.IsEditor;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.editor.client.adapters.TakesValueEditor;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Unimax aka Sunil Kumar Sheoran
 * 
 * GWT Wrapper for http://eonasdan.github.io/bootstrap-datetimepicker/
 * 
 */
public class DateTimePickerBase extends Widget implements HasEnabled, HasId, HasResponsiveness, HasPlaceholder,
        HasDaysOfWeekDisabled, HasEndDate, HasFormat, HasShowTodayButton, HasStartDate, HasStartView,
        HasDateTimePickerHandlers, HasLanguage, HasName, HasValue<Date>, HasPosition, IsEditor<LeafValueEditor<Date>> {

    private final TextBox textBox;
    private DateTimeFormat dateTimeFormat;
    private final DateTimeFormat startEndDateFormat = DateTimeFormat.getFormat("YYYY-MM-DD");
    private LeafValueEditor<Date> editor;
    private final ErrorHandlerMixin<Date> errorHandlerMixin = new ErrorHandlerMixin<Date>(this);

    /**
     * DEFAULT values
     */
    private String format = "MM/DD/YYYY HH:mm A";
    private String dayViewHeaderFormat = "MMMM YYYY";
    private String extraFormats = "false";
    private int stepping = 1;
    private Date minDate = null;
    private Date maxDate = null;
    private String useCurrent = "day"; //'year', 'month', 'day', 'hour', 'minute'
    private boolean collapse = true;
    private String locale = "en"; //default is moment.locale()
    private Date defaultDate = null; //if we set this use current will be overridden
    private String[] disabledDates = {};
    private boolean useStrict = true; //TODO default is false
    private boolean sideBySide = false;
    private DateTimePickerDayOfWeek[] daysOfWeekDisabled = {};
    private boolean calendarWeeks = false;
    private DateTimePickerView viewMode = DateTimePickerView.DAYS; // Accepts: 'years','months','days'
    private DateTimePickerToolbarPosition toolbarPlacement = DateTimePickerToolbarPosition.DEFAULT;
    private boolean showTodayButton = true; //TODO default is false, Show the "Today" button in the icon toolbar.
    private boolean showClear = true; //TODO default is false, Show the "Clear" button in the icon toolbar.
    private boolean showClose = true; //TODO default is false, Show the "Close" button in the icon toolbar.
    private String widgetParent = null;
    private boolean keepOpen = false;
    private boolean inline = false;
    private boolean keepInvalid = false;
    private boolean debug = false; //Will cause the date picker to stay open after a blur event.
    private boolean focusOnShow = true;// If false, the textbox will not be given focus when the picker is shown
    private String[] disabledHours = {};
    private String viewDate = "false";

    private boolean highlightToday = false;

    private DateTimePickerLanguage language = DateTimePickerLanguage.EN;
    private DateTimePickerPosition position = DateTimePickerPosition.BOTTOM_RIGHT;

    //TODO list 
    /*
     * check daysOfWeekDisabled
     * options()
     * check format()
     * check locale()
     * stepping()
     * sideBySide()
     * collapse()
     * icons()
     * useStrict()
     * widgetPositioning()
     * check viewMode()
     * calendarWeeks()
     * showClear()
     * showTodayButton()
     * toolbarplacement()
     * dayViewHeaderFormat()
     * keybinds()
     * inline()
     * ignoreReadonly()
     * showClose()
     * keepInvalid()
     * allowInputToggle()
     * focusOnShow()
     * disabledTimeIntervals()
     * disabledHours()
     * viewDate()
     */

    public DateTimePickerBase() {
        textBox = new TextBox();
        setElement((Element)textBox.getElement());
        setFormat(format);
    }

    public TextBox getTextBox() {
        return textBox;
    }

    public void setAlignment(final ValueBoxBase.TextAlignment align) {
        textBox.setAlignment(align);
    }

    /** {@inheritDoc} */
    @Override
    public void setPlaceholder(final String placeHolder) {
        textBox.setPlaceholder(placeHolder);
    }

    /** {@inheritDoc} */
    @Override
    public String getPlaceholder() {
        return textBox.getPlaceholder();
    }

    public void setReadOnly(final boolean readOnly) {
        textBox.setReadOnly(readOnly);
    }

    public boolean isReadOnly() {
        return textBox.isReadOnly();
    }

    /** {@inheritDoc} */
    @Override
    public boolean isEnabled() {
        return textBox.isEnabled();
    }

    /** {@inheritDoc} */
    @Override
    public void setEnabled(final boolean enabled) {
        if (enabled) {
            enable(getElement());
        } else {
            disable(getElement());
        }
        textBox.setEnabled(enabled);
    }

    /** {@inheritDoc} */
    @Override
    public void setId(final String id) {
        textBox.setId(id);
    }

    /** {@inheritDoc} */
    @Override
    public String getId() {
        return textBox.getId();
    }

    /** {@inheritDoc} */
    @Override
    public void setName(final String name) {
        textBox.setName(name);
    }

    /** {@inheritDoc} */
    @Override
    public String getName() {
        return textBox.getName();
    }

    /** {@inheritDoc} */
    @Override
    public void setVisibleOn(final DeviceSize deviceSize) {
        StyleHelper.setVisibleOn(this, deviceSize);
    }

    /** {@inheritDoc} */
    @Override
    public void setHiddenOn(final DeviceSize deviceSize) {
        StyleHelper.setHiddenOn(this, deviceSize);
    }

    /** {@inheritDoc} */
    @Override
    public void setLanguage(final DateTimePickerLanguage language) {
        this.language = language;
        setLanguage(language.getCode());
    }

    private final native void setLanguage(String code) /*-{
                                                       $wnd.moment.locale(code);
                                                       }-*/;

    /** {@inheritDoc} */
    @Override
    public DateTimePickerLanguage getLanguage() {
        return language;
    }

    /**
     * show only time picker
     * Note: changing the format will change the picker
     * @param boolean
     */
    public void setTimePickerOnly(boolean enable) {
        if (enable) {
            setFormat("LT");
        } else {
            setFormat(format);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void setPosition(final DateTimePickerPosition position) {
        this.position = position;
    }

    /** {@inheritDoc} */
    @Override
    public DateTimePickerPosition getPosition() {
        return position;
    }

    /**
     * Call this whenever changing any settings: minView, startView, format, etc. If you are changing
     * format and date value, the updates must take in such order:
     * <p/>
     * 1. DateTimePicker.reload()
     * 2. DateTimePicker.setValue(newDate); // Date newDate.
     * <p/>
     * Otherwise date value is not updated.
     */
    public void reload() {
        configure();
    }

    public void toggle() {
        toggle(getElement());
    }

    public void show() {
        show(getElement());
    }

    public void clear() {
        clear(getElement());
    }

    public void hide() {
        hide(getElement());
    }

    /** {@inheritDoc} */
    @Override
    public void onShow(final Event e) {
        // On show we put focus on the textbox
        textBox.setFocus(true);
        fireEvent(new ShowEvent(e));
    }

    /** {@inheritDoc} */
    @Override
    public HandlerRegistration addShowHandler(final ShowHandler showHandler) {
        return addHandler(showHandler, ShowEvent.getType());
    }

    /** {@inheritDoc} */
    @Override
    public void onHide(final Event e) {
        // On hide we remove focus from the textbox
        textBox.setFocus(false);
        fireEvent(new HideEvent(e));
    }

    /** {@inheritDoc} */
    @Override
    public HandlerRegistration addHideHandler(final HideHandler hideHandler) {
        return addHandler(hideHandler, HideEvent.getType());
    }

    /** {@inheritDoc} */

    @Override
    public void onChange(final Event e) {
        fireEvent(new ChangeDateEvent(e));
        ValueChangeEvent.fire(DateTimePickerBase.this, getValue());
        //TODO ?hide();
    }

    @Override
    public HandlerRegistration addChangeDateHandler(final ChangeDateHandler changeDateHandler) {
        return addHandler(changeDateHandler, ChangeDateEvent.getType());
    }

    @Override
    public void onError(final Event e) {
        fireEvent(new ErrorEvent(e));
    }

    @Override
    public HandlerRegistration addErrorHandler(final ErrorHandler errorHandler) {
        return addHandler(errorHandler, ErrorEvent.getType());
    }

    public void onUpdate(final Event e) {
        fireEvent(new ChangeDateEvent(e));
        ValueChangeEvent.fire(DateTimePickerBase.this, getValue());
    }

    /** {@inheritDoc} */
    @Override
    public void setDaysOfWeekDisabled(final DateTimePickerDayOfWeek... daysOfWeekDisabled) {
        setDaysOfWeekDisabled(getElement(), toDaysOfWeekDisabledString(daysOfWeekDisabled));
    }

    /** {@inheritDoc} */
    @Override
    public void setEndDate(final Date endDate) {
        this.maxDate = endDate;
        setEndDate(startEndDateFormat.format(endDate));
    }

    /** {@inheritDoc} */
    @Override
    public void setEndDate(final String endDate) {
        this.maxDate = new Date(endDate);
        setEndDate(getElement(), endDate);
    }

    /** {@inheritDoc} */
    @Override
    public void clearEndDate() {
        this.maxDate = null;
        setStartDate(getElement(), null);
    }

    /** {@inheritDoc} */
    @Override
    public void setShowTodayButton(final boolean showTodayButton) {
        this.showTodayButton = showTodayButton;
    }

    /** {@inheritDoc} */
    @Override
    public void setStartDate(final Date startDate) {
        this.minDate = startDate;
        setStartDate(startEndDateFormat.format(startDate));
    }

    /** {@inheritDoc} */
    @Override
    public void setStartDate(final String startDate) {
        this.minDate = new Date(startDate);
        setStartDate(getElement(), startDate);
    }

    /** {@inheritDoc} */
    @Override
    public void clearStartDate() {
        this.minDate = null;
        setStartDate(getElement(), null);
    }

    public void setViewMode(final DateTimePickerView dateTimePickerView) {
        this.viewMode = dateTimePickerView;

    }

    /** {@inheritDoc} */
    @Override
    public void setFormat(final String format) {
        this.format = format;

        // Get the old value
        final Date oldValue = getValue();

        // Make the new DateTimeFormat
        setDateTimeFormat(format);

        if (oldValue != null) {
            setValue(oldValue);
        }
    }

    public void setDate(String date) {
        date(getElement(), date);
    }

    public void setDate(Date date) {
        date(getElement(), date);
    }

    /**
     * Takes an [ string ] of values and disallows the user to select those days. 
     * Setting this takes precedence over options.minDate, options.maxDate configuration. 
     * Also calling this function removes the configuration of options.enabledDates if such exist.
     * @param arrayOfDatesToBeDisabled
     */
    public void setDisabledDates(String[] arrayOfDatesToBeDisabled) {
        disabledDates(getElement(), arrayOfDatesToBeDisabled);
    }

    /**
     * Takes an [ date ] of values and disallows the user to select those days. 
     * Setting this takes precedence over options.minDate, options.maxDate configuration. 
     * Also calling this function removes the configuration of options.enabledDates if such exist.
     * @param arrayOfDatesToBeDisabled
     */
    public void setDisabledDates(Date[] arrayOfDatesToBeDisabled) {
        disabledDates(getElement(), arrayOfDatesToBeDisabled);
    }

    /**
     * Takes a String. Will set the picker's inital date.
     * @param date
     */
    public void setDefaultDate(String date) {
        defaultDate(getElement(), date);
    }

    /**
     * Takes a Date. Will set the picker's inital date.
     * @param date
     */
    public void setDefaultDate(Date date) {
        defaultDate(getElement(), date);
    }

    /**
     *  You can select the granularity on the initialized Date by passing one of the following strings 
     *  ('year', 'month', 'day', 'hour', 'minute') in the variable.
     *  If for example you pass 'day' to the setUseCurrent function and the input field is empty
     *  the first time the user opens the datetimepicker widget the input text will be initialized to
     *  the current datetime with day granularity 
     *  (ie if currentTime = 2014-08-10 13:32:33 the input value will be initialized to 2014-08-10 00:00:00)
     *  
     *  Note: If the options.defaultDate is set or
     *  the input element the component is attached to has already a value that takes precedence 
     *  and the functionality of useCurrent is not triggered!
     * @param string
     */
    public void setUseCurrent(String string) {
        useCurrent(getElement(), string);
    }

    private void setDateTimeFormat(final String format) {
        this.dateTimeFormat = DateTimeFormat.getFormat(format);
    }

    /** {@inheritDoc} */
    @Override
    public Date getValue() {
        try {
            return dateTimeFormat != null && textBox.getValue() != null ? dateTimeFormat.parse(textBox.getValue())
                    : null;
        } catch (final Exception e) {
            return null;
        }
    }

    public String getBaseValue() {
        return textBox.getValue();
    }

    /** {@inheritDoc} */
    @Override
    public HandlerRegistration addValueChangeHandler(final ValueChangeHandler<Date> dateValueChangeHandler) {
        return addHandler(dateValueChangeHandler, ValueChangeEvent.getType());
    }

    /** {@inheritDoc} */
    @Override
    public void setValue(final Date value) {
        setValue(value, false);
    }

    /** {@inheritDoc} */
    @Override
    public void setValue(final Date value, final boolean fireEvents) {
        errorHandlerMixin.clearErrors();
        textBox.setValue(value != null ? dateTimeFormat.format(value) : null);
        update(textBox.getElement());

        if (fireEvents) {
            ValueChangeEvent.fire(DateTimePickerBase.this, value);
        }
    }

    @Override
    public LeafValueEditor<Date> asEditor() {
        if (editor == null) {
            editor = TakesValueEditor.of(this);
        }
        return editor;
    }

    /** {@inheritDoc} */
    @Override
    protected void onLoad() {
        super.onLoad();
        configure();
        //TODO may be below is not needed
        if (getElement().getParentElement() != null) {
            getElement().getParentElement().getStyle().setPosition(Style.Position.RELATIVE);
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void onUnload() {
        super.onUnload();
        remove(getElement());
    }

    protected void configure() {
        Widget w = this;
        w.getElement().setAttribute("data-date-format", format);

        //TODO If configuring not for the first time, datetimepicker must be removed first.
        //   remove(w.getElement());
        configure(w.getElement(), format, dayViewHeaderFormat, extraFormats, stepping, minDate, maxDate, useCurrent,
                collapse, defaultDate, disabledDates, useStrict, sideBySide,
                toDaysOfWeekDisabledString(daysOfWeekDisabled), calendarWeeks, viewMode.getValue(),
                toolbarPlacement.getPosition(), showTodayButton, showClear, showClose, widgetParent, keepOpen, inline,
                keepInvalid, debug, focusOnShow, disabledHours, language.toString(), position.getPosition());
    }

    protected void execute(final String cmd) {
        execute(getElement(), cmd);
    }

    private native void execute(Element e, String cmd) /*-{
                                                       $wnd.jQuery(e).datetimepicker(cmd);
                                                       }-*/;

    private native void remove(Element e) /*-{
                                          $wnd.jQuery(e).datetimepicker('destroy');
                                          }-*/;

    private native void clear(Element e) /*-{
                                         $wnd.jQuery(e).datetimepicker('clear');
                                         }-*/;

    private native void show(Element e) /*-{
                                        $wnd.jQuery(e).datetimepicker('show');
                                        }-*/;

    /**
     * Disables the input element, the component is attached to, by adding a disabled="true" attribute to it.
     *  If the widget was visible before that call it is hidden.
     * @param Element
     */
    private native void disable(Element e) /*-{
                                           $wnd.jQuery(e).datetimepicker('disable');
                                           }-*/;

    /**
     * Enables the input element, the component is attached to, by removing disabled attribute from it.
     * @param Element
     */
    private native void enable(Element e) /*-{
                                          $wnd.jQuery(e).datetimepicker('enable');
                                          }-*/;

    /**
     * Shows or hides the widget
     * @param Element
     */
    private native void toggle(Element e) /*-{
                                          $wnd.jQuery(e).datetimepicker('toggle');
                                          }-*/;

    private native void date(Element e, String date)/*-{
                                                    $wnd.jQuery(e).datetimepicker('date',date);
                                                    }-*/;

    private native void date(Element e, Date date)/*-{
                                                  $wnd.jQuery(e).datetimepicker('date',date);
                                                  }-*/;

    private native void hide(Element e) /*-{
                                        $wnd.jQuery(e).datetimepicker('hide');
                                        }-*/;

    private native void update(Element e) /*-{
                                          $wnd.jQuery(e).datetimepicker('update');
                                          }-*/;

    private native void setStartDate(Element e, String startDate) /*-{
                                                                  $wnd.jQuery(e).datetimepicker('minDate', startDate);
                                                                  }-*/;

    private native void setEndDate(Element e, String endDate) /*-{
                                                              $wnd.jQuery(e).datetimepicker('maxDate', endDate);
                                                              }-*/;

    private native void setDaysOfWeekDisabled(Element e, Integer[] daysOfWeekDisabled) /*-{
                                                                                       $wnd.jQuery(e).datetimepicker('daysOfWeekDisabled', daysOfWeekDisabled);
                                                                                       }-*/;

    private native void disabledDates(Element e, String[] dates)
    /*-{
    $wnd.jQuery(e).datetimepicker('disabledDates', dates);
    }-*/;

    private native void disabledDates(Element e, Date[] dates)
    /*-{
    $wnd.jQuery(e).datetimepicker('disabledDates', dates);
    }-*/;

    private native void defaultDate(Element e, String date)
    /*-{
    $wnd.jQuery(e).datetimepicker('defaultDate', date);
    }-*/;

    private native void defaultDate(Element e, Date date)
    /*-{
    $wnd.jQuery(e).datetimepicker('defaultDate', date);
    }-*/;

    private native void useCurrent(Element e, String format)
    /*-{
    $wnd.jQuery(e).datetimepicker('useCurrent', format);
    }-*/;

    //TODO complete this function for all customizable fields
    protected native void configure(Element e, String format, String dayViewHeaderFormat, String extraFormats,
            int stepping, Date minDate, Date maxDate, String useCurrent, boolean collapse, Date defaultDate,
            String[] disabledDates, boolean useStrict, boolean sideBySide, Integer[] daysOfWeekDisabled,
            boolean calendarWeeks, String viewMode, String toolbarPlacement, boolean showTodayButton,
            boolean showClear, boolean showClose, String widgetParent, boolean keepOpen, boolean inline,
            boolean keepInvalid, boolean debug, boolean focusOnShow, String[] disabledHours, String language,
            String position) /*-{
                             var that = this;
                             $wnd
                             .jQuery(e)
                             .datetimepicker({
                             format : format,
                             dayViewHeaderFormat :dayViewHeaderFormat,
                             stepping : stepping ,
                             useCurrent :useCurrent ,
                             collapse : collapse,
                             defaultDate : defaultDate,
                             disabledDates : disabledDates,
                             useStrict : useStrict,
                             sideBySide : sideBySide,
                             daysOfWeekDisabled : daysOfWeekDisabled,
                             calendarWeeks :calendarWeeks ,
                             viewMode :viewMode,
                             toolbarPlacement : toolbarPlacement,
                             showTodayButton : showTodayButton,
                             showClear :showClear,
                             showClose :showClose ,
                             keepOpen : keepOpen,
                             inline :inline,
                             keepInvalid :keepInvalid,
                             debug :debug,
                             locale : language
                             })
                             .on(
                             'dp.show',
                             function(e) {
                             that.@org.gwtbootstrap3.extras.datetimepicker.client.ui.base.DateTimePickerBase::onShow(Lcom/google/gwt/user/client/Event;)(e);
                             })
                             .on(
                             "dp.hide",
                             function(e) {
                             that.@org.gwtbootstrap3.extras.datetimepicker.client.ui.base.DateTimePickerBase::onHide(Lcom/google/gwt/user/client/Event;)(e);
                             })
                             .on(
                             "dp.change",
                             function(e) {
                             that.@org.gwtbootstrap3.extras.datetimepicker.client.ui.base.DateTimePickerBase::onChange(Lcom/google/gwt/user/client/Event;)(e);
                             })
                             .on(
                             "dp.error",
                             function(e) {
                             that.@org.gwtbootstrap3.extras.datetimepicker.client.ui.base.DateTimePickerBase::onError(Lcom/google/gwt/user/client/Event;)(e);
                             })

                             .on(
                             "dp.update",
                             function(e) {
                             that.@org.gwtbootstrap3.extras.datetimepicker.client.ui.base.DateTimePickerBase::onUpdate(Lcom/google/gwt/user/client/Event;)(e);
                             })

                             }-*/;

    protected Integer[] toDaysOfWeekDisabledString(final DateTimePickerDayOfWeek... dateTimePickerDayOfWeeks) {
        this.daysOfWeekDisabled = dateTimePickerDayOfWeeks;

        final Integer[] arrayOfWeekDaysToBeDisabled = new Integer[dateTimePickerDayOfWeeks.length];

        int i = 0;
        for (int j = 0; j < dateTimePickerDayOfWeeks.length; j++) {
            arrayOfWeekDaysToBeDisabled[i] = dateTimePickerDayOfWeeks[i].getValue();
        }
        return arrayOfWeekDaysToBeDisabled;
    }

    @Override
    public void setStartView(DateTimePickerView dateTimePickerView) {
        this.viewMode = dateTimePickerView;
    }

}
