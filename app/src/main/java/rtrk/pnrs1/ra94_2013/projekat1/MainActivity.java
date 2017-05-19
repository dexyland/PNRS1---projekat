package rtrk.pnrs1.ra94_2013.projekat1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button newTask;
    private Button statistics;

    private ListView mList;
    private MyAdapter mTaskAdapter;

    private int mPosition;
    private int NORMAL_START = 0;
    private int LONG_PRESS_START = 1;

    private int year;
    private int month;
    private int day;

    int highPerc = 50;              //
    int mediumPerc = 76;            //  Used to store percentage of finished tasks. (Not accurate!)
    int lowPerc = 100;              //

    private String mDateToDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newTask = (Button)findViewById(R.id.newTask);
        statistics = (Button)findViewById(R.id.statistics);
        mList = (ListView)findViewById(R.id.list);
        mTaskAdapter = new MyAdapter(MainActivity.this);

        newTask.setOnClickListener(this);
        statistics.setOnClickListener(this);

        mList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {                            // Registers a long click on an item in list and starts activity with request code LONG_PRESS_START.
            @Override                                                                                           // Also sets labels for buttons on bottom of activity to corresponding ones.
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i1 = new Intent(MainActivity.this, TaskCreator.class);
                i1.putExtra("leftButtonLabel", getText(R.string.save));
                i1.putExtra("rightButtonLabel", getText(R.string.delete));
                mPosition = position;
                i1.putExtra("listElement", position);
                startActivityForResult(i1, LONG_PRESS_START);
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
                i2.putExtra("leftButtonLabel", getText(R.string.add));
                i2.putExtra("rightButtonLabel", getText(R.string.cancel));
                startActivityForResult(i2, NORMAL_START);
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
        if(requestCode == NORMAL_START && resultCode == RESULT_OK)
        {
            year = data.getExtras().getInt("Year");
            month = data.getExtras().getInt("Month");
            day = data.getExtras().getInt("Day");
            makeDate();

            mTaskAdapter.addTask(new listElement(data.getStringExtra("Name"),
                                                 mDateToDisplay,
                                                 data.getStringExtra("Priority"),
                                                 data.getExtras().getBoolean("ReminderSet")));
            mTaskAdapter.notifyDataSetChanged();
        }
        if(requestCode == LONG_PRESS_START && resultCode == RESULT_CANCELED)
        {
            mTaskAdapter.removeTask(mPosition);
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
                mDateToDisplay = "Danas";
            }
            else if(c.get(Calendar.DAY_OF_YEAR) - ref.get(Calendar.DAY_OF_YEAR) == 1)
            {
                mDateToDisplay = "Sutra";
            }
            else if(c.get(Calendar.DAY_OF_YEAR) - ref.get(Calendar.DAY_OF_YEAR) >=2 &&
                    c.get(Calendar.DAY_OF_YEAR) - ref.get(Calendar.DAY_OF_YEAR) <7)
            {
                switch (c.get(Calendar.DAY_OF_WEEK))
                {
                    case(Calendar.MONDAY) :
                    {
                        mDateToDisplay = "Ponedeljak";
                        break;
                    }
                    case(Calendar.TUESDAY) :
                    {
                        mDateToDisplay = "Utorak";
                        break;
                    }
                    case(Calendar.WEDNESDAY) :
                    {
                        mDateToDisplay = "Sreda";
                        break;
                    }
                    case(Calendar.THURSDAY) :
                    {
                        mDateToDisplay = "Cetvrtak";
                        break;
                    }
                    case(Calendar.FRIDAY) :
                    {
                        mDateToDisplay = "Petak";
                        break;
                    }
                    case(Calendar.SATURDAY) :
                    {
                        mDateToDisplay = "Subota";
                        break;
                    }
                    case(Calendar.SUNDAY) :
                    {
                        mDateToDisplay = "Nedelja";
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
}