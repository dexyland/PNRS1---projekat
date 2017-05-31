package rtrk.pnrs1.ra94_2013.projekat1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "tasks.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "tasks";
    public static final String COLUMN_mTaskPositionId = "id";
    public static final String COLUMN_mTaskName = "taskName";
    public static final String COLUMN_mTaskDescription = "taskDescription";
    public static final String COLUMN_mTaskDate = "taskDate";
    public static final String COLUMN_mTaskHour = "taskHour";
    public static final String COLUMN_mTaskMinute = "taskMinute";
    public static final String COLUMN_mTaskPriority = "taskPriority";
    public static final String COLUMN_mTaskReminder = "taskReminder";

    private SQLiteDatabase mDb = null;

    public MyDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_mTaskPositionId + " INTEGER PRIMARY KEY, " +
                COLUMN_mTaskName + "TEXT, " +
                COLUMN_mTaskDescription + " TEXT, " +
                COLUMN_mTaskDate + " TEXT, " +
                COLUMN_mTaskHour + " INTEGER, " +
                COLUMN_mTaskMinute + " INTEGER, " +
                COLUMN_mTaskPriority + " TEXT, " +
                COLUMN_mTaskReminder + " INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private SQLiteDatabase getDb(){
        if(mDb == null){
            mDb = getWritableDatabase();
        }
        return mDb;
    }

    public void insert(listElement mListElement){
        SQLiteDatabase db = getDb();

        ContentValues values = new ContentValues();
        values.put(COLUMN_mTaskName, mListElement.mTaskName);
        values.put(COLUMN_mTaskDescription, mListElement.mTaskDescription);
        values.put(COLUMN_mTaskDate, mListElement.mTaskDate);
        values.put(COLUMN_mTaskHour, mListElement.mTaskHour);
        values.put(COLUMN_mTaskMinute, mListElement.mTaskMinute);
        values.put(COLUMN_mTaskPriority, mListElement.mTaskPriority);
        values.put(COLUMN_mTaskReminder, mListElement.mTaskReminder);

        mListElement.mTaskPositionId = db.insert(TABLE_NAME, null, values);
    }

    public listElement readTask(long id){
        SQLiteDatabase db = getDb();

        Cursor cursor = db.query(TABLE_NAME, null, "id = ?",
                new String[] {Long.toString(id)}, null, null, null);

        cursor.moveToFirst();
        return createTask(cursor);
    }

    public void update(listElement mListElement){
        SQLiteDatabase db = getDb();

        ContentValues values = new ContentValues();
        values.put(COLUMN_mTaskName, mListElement.mTaskName);
        values.put(COLUMN_mTaskDescription, mListElement.mTaskDescription);
        values.put(COLUMN_mTaskDate, mListElement.mTaskDate);
        values.put(COLUMN_mTaskHour, mListElement.mTaskHour);
        values.put(COLUMN_mTaskMinute, mListElement.mTaskMinute);
        values.put(COLUMN_mTaskPriority, mListElement.mTaskPriority);
        values.put(COLUMN_mTaskReminder, mListElement.mTaskReminder);

        db.update(TABLE_NAME, values, "id = ?", new String[] {Long.toString(mListElement.mTaskPositionId)});
    }

    private listElement createTask(Cursor cursor){
        long mTaskPositionId = cursor.getLong(cursor.getColumnIndex(COLUMN_mTaskPositionId));
        String mTaskName = cursor.getString(cursor.getColumnIndex(COLUMN_mTaskName));
        String mTaskDescription = cursor.getString(cursor.getColumnIndex(COLUMN_mTaskDescription));
        String mTaskDate = cursor.getString(cursor.getColumnIndex(COLUMN_mTaskDate));
        String mTaskPriority = cursor.getString(cursor.getColumnIndex(COLUMN_mTaskPriority));
        int mTaskHour = cursor.getInt(cursor.getColumnIndex(COLUMN_mTaskHour));
        int mTaskMinute = cursor.getInt(cursor.getColumnIndex(COLUMN_mTaskMinute));
        int mTaskReminder = cursor.getInt(cursor.getColumnIndex(COLUMN_mTaskReminder));

        return new listElement(mTaskPositionId, mTaskName,
                               mTaskDescription, mTaskDate,
                               mTaskHour, mTaskMinute,
                               mTaskPriority, mTaskReminder);
    }
}
