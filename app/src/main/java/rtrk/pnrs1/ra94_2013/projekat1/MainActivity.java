package rtrk.pnrs1.ra94_2013.projekat1;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection{

    private Button newTask;
    private Button statistics;

    private ListView mList;
    private MyAdapter mTaskAdapter;

    private int mPosition;
    private int ADD_MODE = 0;
    private int EDIT_MODE = 1;

    private int year;
    private int month;
    private int day;

    int highPerc = 50;              //
    int mediumPerc = 76;            //  Used to store percentage of finished tasks. (Not accurate!)
    int lowPerc = 100;              //

    private IMyAidlInterface mNotification;
    private ServiceConnection mServiceConnection;
    private Intent mServiceIntent;

    public static ArrayList<listElement> mTaskList;

    private String mDateToDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newTask = (Button)findViewById(R.id.newTask);
        statistics = (Button)findViewById(R.id.statistics);
        mList = (ListView)findViewById(R.id.list);
        mTaskAdapter = new MyAdapter(MainActivity.this);

        mServiceConnection = this;
        mServiceIntent = new Intent(this, MyService.class);
        bindService(mServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        mTaskList = mTaskAdapter.getTasks();

        newTask.setOnClickListener(this);
        statistics.setOnClickListener(this);

        mList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {                            // Registers a long click on an item in list and starts activity with request code LONG_PRESS_START.
            @Override                                                                                           // Also sets labels for buttons on bottom of activity to corresponding ones.
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i1 = new Intent(MainActivity.this, TaskCreator.class);
                mPosition = position;
                i1.putExtra("listElement", position);
                i1.putExtra("MODE", EDIT_MODE);
                startActivityForResult(i1, EDIT_MODE);
                return true;
            }
        });

        mList.setAdapter(mTaskAdapter);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.newTask :                                                                               // Register a click on button and starts activity with request code NORMAL_START.
                Intent i2 = new Intent(this, TaskCreator.class);                                              // Also sets labels for buttons on bottom of activity to corresponding ones.
                i2.putExtra("MODE", ADD_MODE);
                startActivityForResult(i2, ADD_MODE);
                break;

            case R.id.statistics :
                Intent i3 = new Intent(this, Statistics.class);
                i3.putExtra("highPerc", highPerc);
                i3.putExtra("mediumPerc", mediumPerc);
                i3.putExtra("lowPerc", lowPerc);
                startActivity(i3);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {                             // Method automatically invoked after closing an activity started from this activity that was expected an result from.
        super.onActivityResult(requestCode, resultCode, data);                                                  // According to 'request code' and 'result code' activity retrieved appropriate methods are called.
        if(requestCode == ADD_MODE && resultCode == RESULT_OK)
        {
            year = data.getExtras().getInt("Year");
            month = data.getExtras().getInt("Month");
            day = data.getExtras().getInt("Day");
            makeDate();

            mTaskAdapter.addTask(new listElement(0, data.getStringExtra("Name"),
                                                 data.getStringExtra("Description"),
                                                 mDateToDisplay,
                                                 data.getExtras().getInt("Hour"),
                                                 data.getExtras().getInt("Minute"),
                                                 data.getStringExtra("Priority"),
                                                 data.getExtras().getInt("ReminderSet")));

            try {
                mNotification.onNotificationAdd(data.getStringExtra("Name"));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if(requestCode == EDIT_MODE && resultCode == RESULT_OK)
        {
            if(data.getExtras().getInt("Button") == 0){
                //mTaskAdapter.editTask();
                try {
                    mNotification.onNotificationEdit(data.getStringExtra("Name"));
                } catch(RemoteException e){
                    e.printStackTrace();
                }
            }
            else if(data.getExtras().getInt("Button") == 1) {
                mTaskAdapter.removeTask(mPosition);
                try {
                    mNotification.onNotificationDelete(data.getStringExtra("Name"));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void makeDate(){                                                                        // Function formats the date output. Selected date is compared to current date so the corresponding date label could be added to display
        final Calendar ref = Calendar.getInstance();

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);

        if(ref.get(Calendar.YEAR) == year)
        {
            if(c.get(Calendar.DAY_OF_YEAR) - ref.get(Calendar.DAY_OF_YEAR) == 0)
            {
                mDateToDisplay = getString(R.string.today);
            }
            else if(c.get(Calendar.DAY_OF_YEAR) - ref.get(Calendar.DAY_OF_YEAR) == 1)
            {
                mDateToDisplay = getString(R.string.tommorow);
            }
            else if(c.get(Calendar.DAY_OF_YEAR) - ref.get(Calendar.DAY_OF_YEAR) >=2 &&
                    c.get(Calendar.DAY_OF_YEAR) - ref.get(Calendar.DAY_OF_YEAR) <7)
            {
                switch (c.get(Calendar.DAY_OF_WEEK))
                {
                    case(Calendar.MONDAY) :
                    {
                        mDateToDisplay = getString(R.string.monday);
                        break;
                    }
                    case(Calendar.TUESDAY) :
                    {
                        mDateToDisplay = getString(R.string.tuesday);
                        break;
                    }
                    case(Calendar.WEDNESDAY) :
                    {
                        mDateToDisplay = getString(R.string.wednesday);
                        break;
                    }
                    case(Calendar.THURSDAY) :
                    {
                        mDateToDisplay = getString(R.string.thursday);
                        break;
                    }
                    case(Calendar.FRIDAY) :
                    {
                        mDateToDisplay = getString(R.string.friday);
                        break;
                    }
                    case(Calendar.SATURDAY) :
                    {
                        mDateToDisplay = getString(R.string.saturday);
                        break;
                    }
                    case(Calendar.SUNDAY) :
                    {
                        mDateToDisplay = getString(R.string.sunday);
                        break;
                    }
                }
            }
            else
                mDateToDisplay = pad(day)+"-"+pad(month+1)+"-"+Integer.toString(year);
        }
    }

    private static String pad(int c) {                                                              // Adds '0' to single number dates so the date format would always stay the same (DD-MM-YYYY)
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service){
        mNotification = IMyAidlInterface.Stub.asInterface(service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name){}
}