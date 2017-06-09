package rtrk.pnrs1.ra94_2013.projekat1;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    private IMyAidlInterface mNotification;
    private ServiceConnection mServiceConnection;
    private Intent mServiceIntent;

    public static ArrayList<listElement> mTaskList;
    public static MyDbHelper mDb;

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
                i1.putExtra(getResources().getString(R.string.taskPosition),mPosition+1);
                i1.putExtra(getString(R.string.mode), EDIT_MODE);
                startActivityForResult(i1, EDIT_MODE);
                return true;
            }
        });

        mList.setAdapter(mTaskAdapter);
        mDb = new MyDbHelper(this);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        listElement[] mTasks = mDb.readTasks();
        mTaskAdapter.update(mTasks);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.newTask :                                                                               // Register a click on button and starts activity with request code NORMAL_START.
                Intent i2 = new Intent(this, TaskCreator.class);                                              // Also sets labels for buttons on bottom of activity to corresponding ones.
                i2.putExtra(getString(R.string.mode), ADD_MODE);
                startActivityForResult(i2, ADD_MODE);
                break;

            case R.id.statistics :
                Intent i3 = new Intent(this, Statistics.class);
                startActivity(i3);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {                             // Method automatically invoked after closing an activity started from this activity that was expected an result from.
        super.onActivityResult(requestCode, resultCode, data);                                                  // According to 'request code' and 'result code' activity retrieved appropriate methods are called.
        if(requestCode == ADD_MODE && resultCode == RESULT_OK && data.getExtras().getInt("Button") == 0){
            listElement mTask = (listElement) data.getSerializableExtra(getString(R.string.taskResult));
            String mTaskDate = pad(mTask.getTaskDay()) + "-" + pad(mTask.getTaskMonth()+1) + "-" + mTask.getTaskYear();
            mTaskAdapter.addTask(mTask);
            mTask.setTaskId(mTaskAdapter.getCount());
            mTask.setTaskDate(mTaskDate);

            mDb.insert(mTask);
            listElement[] mTasks = mDb.readTasks();
            mTaskAdapter.update(mTasks);

            try {
                mNotification.onNotificationAdd(mTask.getTaskName());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        if(requestCode == EDIT_MODE && resultCode == RESULT_OK){
            if(data.getExtras().getInt("Button") == 0){
                listElement mTask = (listElement) data.getSerializableExtra(getString(R.string.taskResult));
                mTask.setTaskId(mPosition+1);
                mDb.updateTask(mTask, String.valueOf(mTask.getTaskId()));
                Log.d("IDIDIDIDIDIDID", String.valueOf(mTask.getTaskId()));
                listElement[] mTasks = mDb.readTasks();
                mTaskAdapter.update(mTasks);

                try {
                    mNotification.onNotificationEdit(data.getStringExtra("Name"));
                } catch(RemoteException e){
                    e.printStackTrace();
                }
            }
            else if(data.getExtras().getInt("Button") == 1) {
                mDb.deleteTask(String.valueOf(mPosition+1));
                if(mTaskAdapter.getCount() > 1)
                {
                    listElement[] mTasks = mDb.readTasks();
                    for(listElement mTask : mTasks)
                    {
                        if(mTask.getTaskId() > mPosition+1)
                        {
                            mTask.setTaskId(mTask.getTaskId() -1);
                            mDb.updateTask(mTask,String.valueOf(mTask.getTaskId()+1));
                        }
                    }
                }
                listElement[] mTasks = mDb.readTasks();
                mTaskAdapter.update(mTasks);
                try {
                    mNotification.onNotificationDelete(data.getStringExtra("Name"));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
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