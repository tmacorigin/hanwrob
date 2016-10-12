package com.toolset.dataManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wanghp1 on 2016/10/7.
 */
public class dataManagerdataBase {

    public String dateTime;
    public dataManagerdataBase()
    {
        dateTime = calendarToString(Calendar.getInstance());
    }



    private String calendarToString( Calendar calendat  )
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
       return sdf.format(calendat.getTime());
    }

    private Calendar stringToCalendar( String str )
    {
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar;
    }
}
