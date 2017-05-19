package rtrk.pnrs1.ra94_2013.projekat1;

import java.io.Serializable;

public class listElement implements Serializable {

    protected String mTaskName;
    protected String mTaskDate;
    protected String mTaskPriority;
    protected boolean mTaskReminder;

    public listElement(String name, String date, String priority, boolean reminderSet)
    {
        mTaskName = name;
        mTaskDate = date;
        mTaskPriority = priority;
        mTaskReminder = reminderSet;
    }
}