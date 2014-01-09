package com.jee.domain;

import org.richfaces.model.CalendarDataModelItem;

public class CalendarModelItem implements CalendarDataModelItem {
    private boolean enabled;
    private String styleClass;
 
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
 
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }
 
    public boolean isEnabled() {
        return enabled;
    }
 
    public String getStyleClass() {
        return styleClass;
    }
 
    public Object getData() {
        return null;
    }
 
    public boolean hasToolTip() {
        return false;
    }
 
    public Object getToolTip() {
        return null;
    }
 
    public int getDay() {
        return 0;
    }
}