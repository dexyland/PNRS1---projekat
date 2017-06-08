package rtrk.pnrs1.ra94_2013.projekat1;

import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;
import java.util.Calendar;

public class listElement extends AppCompatActivity implements Serializable {

    protected String mTaskName;
    protected String mTaskDescription;
    protected String mTaskPriority;
    protected String mTaskDate;
    protected int mTaskReminder;
    protected int mTaskFinished;
    protected int mTaskPositionId;
    protected Calendar mCalendar;


    public listElement(String name, String description, String priority, int reminderSet, Calendar calendar)
    {
        mTaskPositionId = 0;
        mTaskFinished = 0;
        mTaskName = name;
        mTaskDescription = description;
        mTaskDate = null;
        mTaskPriority = priority;
        mTaskReminder = reminderSet;
        mCalendar = calendar;
    }

    public listElement(int positionID, String name,String description, String priority, int reminderSet, String displayDate, Calendar calendar, int finished)
    {
        mTaskPositionId = positionID;
        mTaskFinished = finished;
        mTaskName = name;
        mTaskDescription = description;
        mTaskDate = displayDate;
        mTaskPriority = priority;
        mTaskReminder = reminderSet;
        mCalendar = calendar;
    }

    public void setTaskDescription(String mTaskDescription){
        this.mTaskDescription = mTaskDescription;
    }

    public String getTaskDate(){
        return mTaskDate;
    }

    public void setTaskDate(String taskDate){
        this.mTaskDate = taskDate;
    }

    public String getTaskDescription(){
        return mTaskDescription;
    }

    public void setTaskId(int positionId){
        this.mTaskPositionId = positionId;
    }

    public int getTaskId(){
        return mTaskPositionId;
    }

    public void setTaskName(String mTaskName){
        this.mTaskName = mTaskName;
    }

    public String getTaskName(){
        return mTaskName;
    }

    public void setTaskPriority(String mTaskPriority){
        this.mTaskPriority = mTaskPriority;
    }

    public String getTaskPriority(){
        return mTaskPriority;
    }

    public void setTaskReminder(int mTaskReminder){
        this.mTaskReminder = mTaskReminder;
    }

    public int getTaskReminder(){
        return mTaskReminder;
    }

    public void setTaskFinished(int mTaskFinished){
        this.mTaskFinished = mTaskFinished;
    }

    public int getTaskFinished(){
        return mTaskFinished;
    }

    public void setTaskCalendar(Calendar calendar){
        mCalendar = calendar;
    }

    public Calendar getTaskCalendar(){
        return mCalendar;
    }

    public int getTaskYear(){
        return mCalendar.get(Calendar.YEAR);
    }
    public void setTaskYear(int year){
        this.mCalendar.set(Calendar.YEAR, year);
    }

    public int getTaskMonth(){
        return mCalendar.get(Calendar.MONTH);
    }
    public void setTaskMonth(int month){
        this.mCalendar.set(Calendar.MONTH, month);
    }

    public int getTaskDay(){
        return mCalendar.get(Calendar.DAY_OF_MONTH);
    }
    public void setTaskDay(int day){
        this.mCalendar.set(Calendar.DAY_OF_MONTH, day);
    }

    public int getTaskHour(){
        return mCalendar.get(Calendar.HOUR_OF_DAY);
    }
    public void setTaskHour(int hour){
        this.mCalendar.set(Calendar.HOUR_OF_DAY, hour);
    }

    public int getTaskMinute(){
        return mCalendar.get(Calendar.MINUTE);
    }
    public void setTaskMinute(int minute){
        this.mCalendar.set(Calendar.MINUTE, minute);
    }
}