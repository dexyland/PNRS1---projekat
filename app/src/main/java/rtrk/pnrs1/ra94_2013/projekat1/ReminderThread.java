package rtrk.pnrs1.ra94_2013.projekat1;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import java.util.Calendar;

public class ReminderThread  extends Thread{
    private boolean mShowNotification;
    private boolean mCheck;

    private Context mContext;

    private NotificationManager mNotificationManager;
    private Notification.Builder mNotificationBuilder;
    private String mNotificationString = null;
    private static int SLEEP_TIME = 5000;
    private Uri mUri;
    private int notifNum;

    public ReminderThread(Context context){
        mCheck = true;
        mContext = context;

        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationBuilder = new Notification.Builder(mContext);

        mNotificationBuilder.setContentTitle("Task deadline approaching!");
        mNotificationBuilder.setSmallIcon(R.drawable.small_icon);
        mUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mNotificationBuilder.setSound(mUri);
        notifNum = 0;
    }

    @Override
    public synchronized void start(){
        mCheck = true;
        super.start();
    }

    public synchronized void exit(){
        mCheck = false;
    }

    public void run(){
        super.run();
        while(mCheck){
            if(!MainActivity.mTaskList.isEmpty()) {
                Calendar mCurrentDate = Calendar.getInstance();
                for (listElement mListElement : MainActivity.mTaskList) {
                    if (mListElement.getTaskReminder() == 1){
                        if (mListElement.getTaskCalendar().getTimeInMillis() - mCurrentDate.getTimeInMillis() < 900000) {
                            mNotificationString = mListElement.getTaskName() + " (" + pad(mListElement.getTaskHour()) + ":" + pad(mListElement.getTaskMinute()) + ")";
                            mShowNotification = true;
                        }
                    }
                }

                if (mShowNotification) {
                    mNotificationBuilder.setContentText(mNotificationString);
                    mNotificationManager.notify(notifNum, mNotificationBuilder.build());
                    mShowNotification = false;
                    notifNum++;
                }

                try {
                    sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
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
}
