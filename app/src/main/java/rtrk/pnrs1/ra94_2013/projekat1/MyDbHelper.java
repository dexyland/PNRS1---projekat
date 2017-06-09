package rtrk.pnrs1.ra94_2013.projekat1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Calendar;

public class MyDbHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "tasks.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "tasks";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "taskName";
    public static final String COLUMN_DESCRIPTION = "taskDescription";
    public static final String COLUMN_DISPLAYDATE = "taskDisplayDate";
    public static final String COLUMN_YEAR = "taskYear";
    public static final String COLUMN_MONTH = "taskMonth";
    public static final String COLUMN_DAY = "taskDay";
    public static final String COLUMN_HOUR = "taskHour";
    public static final String COLUMN_MINUTE = "taskMinute";
    public static final String COLUMN_PRIORITY = "taskPriority";
    public static final String COLUMN_REMINDER = "taskReminder";
    public static final String COLUMN_FINISHED = "taskFinished";

    public MyDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_DISPLAYDATE + " TEXT, " +
                COLUMN_YEAR + " INT, " +
                COLUMN_MONTH + " INT, " +
                COLUMN_DAY + " INT, " +
                COLUMN_HOUR + " INT, " +
                COLUMN_MINUTE + " INT, " +
                COLUMN_PRIORITY + " INT, " +
                COLUMN_REMINDER + " INT, " +
                COLUMN_FINISHED + " INT);");

        Log.d("BAZA", "NAPRAVILA SE BAZA");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(listElement mListElement){
        SQLiteDatabase mDb = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, mListElement.getTaskId());
        values.put(COLUMN_NAME, mListElement.getTaskName());
        values.put(COLUMN_DESCRIPTION, mListElement.getTaskDescription());
        values.put(COLUMN_DISPLAYDATE, mListElement.getTaskDate());
        values.put(COLUMN_YEAR, mListElement.getTaskYear());
        values.put(COLUMN_MONTH, mListElement.getTaskMonth());
        values.put(COLUMN_DAY, mListElement.getTaskDay());
        values.put(COLUMN_HOUR, mListElement.getTaskHour());
        values.put(COLUMN_MINUTE, mListElement.getTaskMinute());
        values.put(COLUMN_PRIORITY, mListElement.getTaskPriority());
        values.put(COLUMN_REMINDER, mListElement.getTaskReminder());
        values.put(COLUMN_FINISHED, mListElement.getTaskFinished());

        mDb.insert(TABLE_NAME, null, values);

        Log.d("BAZA", "DODAO SAM TASK");
        mDb.close();
    }

    public listElement readTask(String id){
        SQLiteDatabase mDb = getReadableDatabase();
        Cursor cursor = mDb.query(TABLE_NAME, null, COLUMN_ID + "= ?",
                new String[] {id}, null, null, null);

        cursor.moveToFirst();
        listElement mTask = createTask(cursor);

        close();
        return mTask;
    }

    public listElement[] readTasks() {
        SQLiteDatabase mDb = getReadableDatabase();
        Cursor cursor = mDb.query(TABLE_NAME, null, null, null, null, null, null, null);

        int i = 0;
        listElement[] mTasks = new listElement[cursor.getCount()];
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            mTasks[i++] = createTask(cursor);
        }

        close();
        Log.d("BAZA", "CITAM TASKOVEEEE");
        return mTasks;
    }

    public void deleteTask(String id){
        SQLiteDatabase mDb = getWritableDatabase();
        mDb.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{id});
        close();
    }

    public void updateTask(listElement mListElement, String id){
        SQLiteDatabase mDb = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, mListElement.getTaskId());
        values.put(COLUMN_NAME, mListElement.getTaskName());
        values.put(COLUMN_DESCRIPTION, mListElement.getTaskDescription());
        values.put(COLUMN_DISPLAYDATE, mListElement.getTaskDate());
        values.put(COLUMN_YEAR, mListElement.getTaskYear());
        values.put(COLUMN_MONTH, mListElement.getTaskMonth());
        values.put(COLUMN_DAY, mListElement.getTaskDay());
        values.put(COLUMN_HOUR, mListElement.getTaskHour());
        values.put(COLUMN_MINUTE, mListElement.getTaskMinute());
        values.put(COLUMN_PRIORITY, mListElement.getTaskPriority());
        values.put(COLUMN_REMINDER, mListElement.getTaskReminder());
        values.put(COLUMN_FINISHED, mListElement.getTaskFinished());

        mDb.update(TABLE_NAME, values, "id = ?", new String[] {id});
        mDb.close();
    }

    private listElement createTask(Cursor cursor){
        int mTaskPositionId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
        String mTaskName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
        String mTaskDescription = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
        String mTaskPriority = cursor.getString(cursor.getColumnIndex(COLUMN_PRIORITY));
        String mTaskDisplayDate = cursor.getString(cursor.getColumnIndex(COLUMN_DISPLAYDATE));
        int mTaskYear = cursor.getInt(cursor.getColumnIndex(COLUMN_YEAR));
        int mTaskMonth = cursor.getInt(cursor.getColumnIndex(COLUMN_MONTH));
        int mTaskDay = cursor.getInt(cursor.getColumnIndex(COLUMN_DAY));
        int mTaskHour = cursor.getInt(cursor.getColumnIndex(COLUMN_HOUR));
        int mTaskMinute = cursor.getInt(cursor.getColumnIndex(COLUMN_MINUTE));
        int mTaskReminder = cursor.getInt(cursor.getColumnIndex(COLUMN_REMINDER));
        int mTaskFinished = cursor.getInt(cursor.getColumnIndex(COLUMN_FINISHED));

        Calendar tmpCal = Calendar.getInstance();
        tmpCal.set(Calendar.YEAR, mTaskYear);
        tmpCal.set(Calendar.MONTH, mTaskMonth);
        tmpCal.set(Calendar.DAY_OF_MONTH, mTaskDay);
        tmpCal.set(Calendar.HOUR_OF_DAY, mTaskHour);
        tmpCal.set(Calendar.MINUTE, mTaskMinute);

        listElement mTask = new listElement(mTaskPositionId, mTaskName, mTaskDescription, mTaskPriority, mTaskReminder, mTaskDisplayDate, tmpCal, mTaskFinished);
        mTask.setTaskId(mTaskPositionId);
        mTask.setTaskFinished(mTaskFinished);

        return mTask;
    }
}