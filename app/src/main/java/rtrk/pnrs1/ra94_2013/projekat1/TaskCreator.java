package rtrk.pnrs1.ra94_2013.projekat1;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.EditText;
import java.util.Calendar;

public class TaskCreator extends AppCompatActivity implements View.OnClickListener {

    private String taskPriority = null;
    private String taskNameref = "Ime zadatka";
    private String descriptionref = "Opis zadatka";
    private String leftButton = null;
    private String rightButton = null;

    private EditText taskname;
    private EditText description;

    private CheckBox checkBox;

    private Button btnChangeDate;
    private Button btnChangeTime;
    private Button red;
    private Button yellow;
    private Button green;

    private int hour;
    private int minute;
    private int year;
    private int month;
    private int day;

    static final int DATE_DIALOG_ID = 999;
    static final int TIME_DIALOG_ID = 888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button add;
        Button cancel;

        checkBox = (CheckBox)findViewById(R.id.checkbox);

        yellow = (Button)findViewById(R.id.yellow);
        cancel = (Button)findViewById(R.id.cancel);
        green = (Button)findViewById(R.id.green);
        add = (Button)findViewById(R.id.add);
        red = (Button)findViewById(R.id.red);

        yellow.setOnClickListener(this);
        cancel.setOnClickListener(this);
        green.setOnClickListener(this);
        red.setOnClickListener(this);
        add.setOnClickListener(this);

        leftButton = getIntent().getStringExtra("leftButtonLabel");
        rightButton = getIntent().getStringExtra("rightButtonLabel");

        add.setText(leftButton);
        cancel.setText(rightButton);

        taskname = (EditText)findViewById(R.id.taskName);
        description = (EditText)findViewById(R.id.description);

        taskname.setOnClickListener(this);                                                                                              //     OnClickListener set on edit boxes so the content
        description.setOnClickListener(this);                                                                                           // can be cleared each time user touches the edit box.

        setCurrentValues();                                                                                                             //     Sets text on 'date' and 'time' button to current values
        addListenerOnButton();                                                                                                          //     Adds listeners on 'time' and 'date' button in order to initiate
    }                                                                                                                                   // time/date picker dialog when the button is pressed

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.green :
                green.setBackground(this.getResources().getDrawable(R.drawable.green_button_enabled));                                  //
                red.setBackground(this.getResources().getDrawable(R.drawable.red_button_disabled));                                     //
                yellow.setBackground(this.getResources().getDrawable(R.drawable.yellow_button_disabled));                               //
                taskPriority="green";                                                                                                   //
                break;                                                                                                                  //
                                                                                                                                        //
            case R.id.yellow :                                                                                                          //       ** When any of the red/yellow/green buttons is pressed **
                green.setBackground(this.getResources().getDrawable(R.drawable.green_button_disabled));                                 //       **      other two buttons should appear 'disabled'     **
                red.setBackground(this.getResources().getDrawable(R.drawable.red_button_disabled));                                     //
                yellow.setBackground(this.getResources().getDrawable(R.drawable.yellow_button_enabled));                                //
                taskPriority="yellow";                                                                                                  //
                break;                                                                                                                  //
                                                                                                                                        //
            case R.id.red :                                                                                                             //    This part of the case structure implements the functionality mentioned above.
                green.setBackground(this.getResources().getDrawable(R.drawable.green_button_disabled));                                 //
                red.setBackground(this.getResources().getDrawable(R.drawable.red_button_enabled));                                      //
                yellow.setBackground(this.getResources().getDrawable(R.drawable.yellow_button_disabled));                               //
                taskPriority="red";
                break;

            case R.id.add :
                if(CheckCondition())                                                                                                    //    Functionality of CheckCondition() function described below.
                {
                    Intent i3 = new Intent(this, MainActivity.class);
                    i3.putExtra("Name", taskname.getText().toString());
                    i3.putExtra("Priority", taskPriority);
                    i3.putExtra("Year", year);
                    i3.putExtra("Month", month);
                    i3.putExtra("Day", day);
                    i3.putExtra("Hour", hour);
                    i3.putExtra("Minute", minute);
                    i3.putExtra("ReminderSet", checkBox.isChecked());
                    i3.putExtra("Button", 0);

                    setResult(RESULT_OK, i3);
                    this.finish();
                }
                else
                    warning();
                break;

            case R.id.cancel :
                Intent i4 = new Intent(this, MainActivity.class);
                i4.putExtra("Name", taskname.getText().toString());
                i4.putExtra("Button", 1);
                setResult(RESULT_OK, i4);
                this.finish();
                break;
        }
    }

    public void addListenerOnButton() {
        btnChangeTime = (Button) findViewById(R.id.time);
        btnChangeDate = (Button) findViewById(R.id.date);

        btnChangeTime.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {                                                                                               //     When the 'time' button is pressed, showDialog() function
               showDialog(TIME_DIALOG_ID);                                                                                              // initiates time picker dialog.
           }
        });

        btnChangeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                                                                               //     When the 'date' button is pressed, showDialog() function
                showDialog(DATE_DIALOG_ID);                                                                                             // initiates date picker dialog.
            }
        });
    }

    public void setCurrentValues() {
        btnChangeDate = (Button)findViewById(R.id.date);
        btnChangeTime = (Button)findViewById(R.id.time);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);                                                                                                    //
        month = c.get(Calendar.MONTH);                                                                                                  //
        day = c.get(Calendar.DAY_OF_MONTH);                                                                                             //     Storing the values of the current year, month, day, hour and minute.
        hour = c.get(Calendar.HOUR_OF_DAY);                                                                                             //
        minute = c.get(Calendar.MINUTE);                                                                                                //

        btnChangeTime.setText(new StringBuilder().append(pad(hour)).append(":").append(pad(minute)));                                   //     Sets the text label on 'time' button in format " hour : minute ".
        btnChangeDate.setText(new StringBuilder().append(month + 1).append("-").append(day).append("-").append(year).append(" "));      //     Sets the text label on 'date' button in format " year-month-day ".
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:                                                                                                        //     In case 'time' button is pressed, time picker dialog is initiated.
                                                                                                                                        // After 'ok' button in dialog is pressed, time picker values are set as
                return new TimePickerDialog(this, timePickerListener, hour, minute, false);                                             // current values.

            case DATE_DIALOG_ID:                                                                                                        //     In case 'date' button is pressed, date picker dialog is initiated.
                                                                                                                                        // After 'ok' button in dialog is pressed, date picker values are set as
                return new DatePickerDialog(this, datePickerListener, year, month, day);                                                // current values.
        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
            hour = selectedHour;                                                                                                        //     Selected values overwrite the 'old current'
            minute = selectedMinute;                                                                                                    // values of 'hour' and 'minute'.

            btnChangeTime.setText(new StringBuilder().append(pad(hour)).append(":").append(pad(minute)));                               //     Sets selected time into 'time' button label.
        }
    };

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            year = selectedYear;                                                                                                        //     Selected values overwrite the 'old current'
            month = selectedMonth;                                                                                                      // values of 'year', 'month' and 'day'.
            day = selectedDay;

            btnChangeDate.setText(new StringBuilder().append(month + 1).append("-").append(day).append("-").append(year).append(" "));  //     Sets selected date into 'date' button label.
        }
    };

    private static String pad(int c) {                                                                                                  //     In case selected time (hours or minute field)
        if (c >= 10)                                                                                                                    // are smaller then 10 this function adds '0' before
            return String.valueOf(c);                                                                                                   // that field so the time format would always be the
        else                                                                                                                            // same (HH:MM).
            return "0" + String.valueOf(c);
    }
                                                                                                                                        //
    private boolean CheckCondition() {                                                                                                  //   **   'Dodaj' button requires valid parameters so it can be pressed.    **
        if(     (taskPriority != null) &&                                                                                               //   **    Valid parameters include task name, description of the task,     **
                (!taskNameref.equals(taskname.getText().toString())) &&                                                                 //   **  one of the priority buttons to be pressed and valid time and date  **
                (!descriptionref.equals(description.getText().toString())) &&                                                           //   **                  (event must to happen in future)                   **
                (!(taskname.getText().toString()).equals("")) &&                                                                        //    CheckCondition() function enables pressing button 'Dodaj' when all
                (!(description.getText().toString()).equals("")) &&                                                                     // parameters are valid.
                checkTime())
            return true;
        else
            return false;
    }

    private boolean checkTime() {                                                                                                       //    Function checkTime() enables adding only if it is not set to happen
        final Calendar c = Calendar.getInstance();                                                                                      // in the past.
        int tmpyear = c.get(Calendar.YEAR);
        int tmpmonth = c.get(Calendar.MONTH);
        int tmpday = c.get(Calendar.DAY_OF_MONTH);
        int tmphour = c.get(Calendar.HOUR_OF_DAY);
        int tmpminute = c.get(Calendar.MINUTE);

        if(tmpyear == year && tmpmonth == month && tmpday == day && tmphour == hour && tmpminute <= minute)
            return true;
        else if(tmpyear == year && tmpmonth == month && tmpday == day && tmphour < hour)
            return true;
        else if(tmpyear == year && tmpmonth == month && tmpday < day)
            return true;
        else if(tmpyear == year && tmpmonth < month)
            return true;
        else if(tmpyear < year)
            return true;
        else
            return false;
    }

    private void warning() {
        AlertDialog alertDialog = new AlertDialog.Builder(TaskCreator.this).create();
        alertDialog.setTitle("Neispravni parametri!");
        alertDialog.setMessage("Proverite da li ste uneli ime zadatka, opis zadatka, da li je oznacen prioritet zadatka i da li je uneseno vreme ispravno.");
        alertDialog.show();
    }
}