package rtrk.pnrs1.ra94_2013.projekat1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Calendar;

public class Statistics extends AppCompatActivity {
    private statisticsNative mStatisticsNative;
    private PriorityGraph HighPriority;
    private PriorityGraph MediumPriority;
    private PriorityGraph LowPriority;
    float highPerc;
    float mediumPerc;
    float lowPerc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        HighPriority = (PriorityGraph)findViewById(R.id.highGraph);
        MediumPriority = (PriorityGraph)findViewById(R.id.mediumGraph);
        LowPriority = (PriorityGraph)findViewById(R.id.lowGraph);

        mStatisticsNative = new statisticsNative();
        calculatePercentages();

        HighPriority.setPercToDraw(highPerc);                               //
        MediumPriority.setPercToDraw(mediumPerc);                           //  Setting the max values to be drawn.
        LowPriority.setPercToDraw(lowPerc);                                 //

        HighPriority.setPriority(3);                                        //
        MediumPriority.setPriority(2);                                      //  Setting graph 'priority'. Used for different colors and text titles.
        LowPriority.setPriority(1);                                         //
    }

    private void calculatePercentages(){
        MyDbHelper mDb = new MyDbHelper(this);

        float highDone = 0;
        float highTotal = 0;
        float mediumDone = 0;
        float mediumTotal = 0;
        float lowDone = 0;
        float lowTotal = 0;
        String priority;
        Calendar current = Calendar.getInstance();

        listElement[] mTasks = mDb.readTasks();

        for(listElement mTask : mTasks){
            priority = mTask.getTaskPriority();
            Log.d("STATISTIKAAA", "ETO ME U FORUUUUUUUU" + mTask.getTaskPriority());
            if(priority.equals("high")){
                highTotal+=1;
                if(mTask.getTaskCalendar().getTimeInMillis() <= current.getTimeInMillis())
                    highDone+=1;
            }

            if(mTask.getTaskPriority().equals(getString(R.string.mediumPriority))){
                mediumTotal+=1;
                if(mTask.getTaskCalendar().getTimeInMillis() <= current.getTimeInMillis())
                    mediumDone+=1;
            }

            if(mTask.getTaskPriority().equals(getString(R.string.lowPriority))){
                lowTotal+=1;
                if(mTask.getTaskCalendar().getTimeInMillis() <= current.getTimeInMillis())
                    lowDone+=1;
            }
        }

        Log.d("TOTAL", ""+highTotal);
        Log.d("TOTAL", ""+mediumTotal);

        Log.d("TOTALDONE", ""+highDone);
        Log.d("TOTALDONE", ""+mediumDone);

        if(highDone == 0)
        {
            highPerc = 0;
        }
        else
        {
            highPerc = (int)mStatisticsNative.getStatisticsResult(highDone, highTotal);
        }
        if(mediumDone == 0)
        {
            mediumPerc = 0;
        }
        else
        {
            mediumPerc = (int)mStatisticsNative.getStatisticsResult(mediumDone, mediumTotal) ;
        }
        if(lowDone == 0)
        {
            lowPerc = 0;
        }
        else
        {
            lowPerc = (int)mStatisticsNative.getStatisticsResult(lowDone, lowTotal);
        }

        Log.d("HIGH", ""+highTotal);
        Log.d("TOTAL", ""+mediumTotal);
    }
}