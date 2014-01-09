package com.jee.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
 
import org.richfaces.model.CalendarDataModel;
import org.richfaces.model.CalendarDataModelItem;
 
@ManagedBean
@ApplicationScoped
public class PastCalendarModel implements CalendarDataModel {
	private static final String BUSY_DAY = "bdc";

	@Override
	public CalendarDataModelItem[] getData(Date[] dateArray) {
		CalendarDataModelItem[] modelItems = new CalendarModelItem[dateArray.length];
        Calendar current = GregorianCalendar.getInstance();
        Calendar today = GregorianCalendar.getInstance();
        
        today.setTime(new Date());
        
        for (int i = 0; i < dateArray.length; i++) {
            current.setTime(dateArray[i]);
            CalendarModelItem modelItem = new CalendarModelItem();
            if (current.after(today)) {
                modelItem.setEnabled(false);
                modelItem.setStyleClass(BUSY_DAY);
            }
            else {
            	modelItem.setEnabled(true);
            	modelItem.setStyleClass("");
            }
            modelItems[i] = modelItem;
        }
        
		return modelItems;
	}

	@Override
	public Object getToolTip(Date arg0) {
		return null;
	}

}
