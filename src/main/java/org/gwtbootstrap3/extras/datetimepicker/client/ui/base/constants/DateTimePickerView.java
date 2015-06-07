package org.gwtbootstrap3.extras.datetimepicker.client.ui.base.constants;

/*
 * #%L
 * GwtBootstrap3
 * %%
 * Copyright (C) 2013 - 2014 GwtBootstrap3
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

/**
 * 1 or 'days' for the days in a month view (the default)
 * 2 or 'months' for months in a year view 
 * 3 or 'years' for the years in a decade overview
 *
 * @author Joshua Godi , Unimax
 */
public enum DateTimePickerView {
    DAYS("days"), MONTHS("months"), YEARS("years");

    private final String value;

    DateTimePickerView(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
