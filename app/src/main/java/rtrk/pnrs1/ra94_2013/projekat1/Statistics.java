package rtrk.pnrs1.ra94_2013.projekat1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Statistics extends AppCompatActivity {
    private PriorityGraph HighPriority;
    private PriorityGraph MediumPriority;
    private PriorityGraph LowPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        HighPriority = (PriorityGraph)findViewById(R.id.highGraph);
        MediumPriority = (PriorityGraph)findViewById(R.id.mediumGraph);
        LowPriority = (PriorityGraph)findViewById(R.id.lowGraph);

        int highPerc = getIntent().getExtras().getInt("highPerc");          //
        int mediumPerc = getIntent().getExtras().getInt("mediumPerc");      //  Getting the max values to be drawn.
        int lowPerc = getIntent().getExtras().getInt("lowPerc");            //

        HighPriority.setPercToDraw(highPerc);                               //
        MediumPriority.setPercToDraw(mediumPerc);                           //  Setting the max values to be drawn.
        LowPriority.setPercToDraw(lowPerc);                                 //

        HighPriority.setPriority(3);                                        //
        MediumPriority.setPriority(2);                                      //  Setting graph 'priority'. Used for different colors and text titles.
        LowPriority.setPriority(1);                                         //
    }
}