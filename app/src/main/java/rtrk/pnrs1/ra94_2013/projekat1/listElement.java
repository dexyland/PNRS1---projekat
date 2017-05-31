package rtrk.pnrs1.ra94_2013.projekat1;

import java.io.Serializable;

public class listElement implements Serializable {

    protected String mTaskName;
    protected String mTaskDescription;
    protected String mTaskDate;
    protected String mTaskPriority;

    protected int mTaskHour;
    protected int mTaskMinute;
    protected int mTaskReminder;

    protected long mTaskPositionId;

    public listElement(long positionId, String name, String description, String date, int hour, int minute, String priority, int reminderSet)
    {
        mTaskPositionId = positionId;
        mTaskName = name;
        mTaskDescription = description;
        mTaskDate = date;
        mTaskHour = hour;
        mTaskMinute = minute;
        mTaskPriority = priority;
        mTaskReminder = reminderSet;
    }

    public void setTaskDescription(String mTaskDescription){
        this.mTaskDescription = mTaskDescription;
    }

    public String getTaskDescription(){
        return mTaskDescription;
    }

    public void setTaskId(long positionId){
        this.mTaskPositionId = positionId;
    }

    public long getTaskId(){
        return mTaskPositionId;
    }

    public void setTaskName(String mTaskName){
        this.mTaskName = mTaskName;
    }

    public String getTaskName(){
        return mTaskName;
    }

    public void setTaskDate(String mTaskDate){
        this.mTaskDate = mTaskDate;
    }

    public String getTaskDate(){
        return mTaskDate;
    }

    public void setmTaskPriority(String mTaskPriority){
        this.mTaskPriority = mTaskPriority;
    }

    public String getmTaskPriority(){
        return mTaskPriority;
    }

    public void setTaskHour(int mTaskHour){
        this.mTaskHour = mTaskHour;
    }

    public int getTaskHour(){
        return mTaskHour;
    }

    public void setTaskMinute(int mTaskMinute){
        this.mTaskMinute = mTaskMinute;
    }

    public int getTaskMinute(){
        return mTaskMinute;
    }

    public String getTaskTime(){
        return pad(mTaskHour) + ":" + pad(mTaskMinute);
    }

    public void setmTaskReminder(int mTaskReminder){
        this.mTaskReminder = mTaskReminder;
    }

    public int getTaskReminder(){
        return mTaskReminder;
    }

    private static String pad(int c) {                                                                                                  //     In case selected time (hours or minute field)
        if (c >= 10)                                                                                                                    // are smaller then 10 this function adds '0' before
            return String.valueOf(c);                                                                                                   // that field so the time format would always be the
        else                                                                                                                            // same (HH:MM).
            return "0" + String.valueOf(c);
    }
}