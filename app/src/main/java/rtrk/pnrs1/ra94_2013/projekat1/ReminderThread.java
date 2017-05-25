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
    private String mNotificationString;
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
        mNotificationString = "";
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
            for(listElement mListElement : MainActivity.mTaskList)
            {
                if(mListElement.getTaskDate().equals(mContext.getResources().getString(R.string.today)) && mListElement.getTaskReminder()){
                    Calendar mCurrentDate = Calendar.getInstance();

                    if(mListElement.getTaskHour() == (int)mCurrentDate.get(Calendar.HOUR_OF_DAY)){
                        if((mListElement.getTaskMinute() - mCurrentDate.get(Calendar.MINUTE)) == 15){
                            if(mNotificationString.equals(""))
                                mNotificationString = mListElement.getTaskName() + " (" + mListElement.getTaskTime() + ")";
                            else
                                mNotificationString += ", " + mListElement.getTaskName() + " (" + mListElement.getTaskTime() + ")";

                            mShowNotification = true;
                            mListElement.setmTaskReminder(false);
                        }
                    }
                    else if((mListElement.getTaskHour() - mCurrentDate.get(Calendar.HOUR_OF_DAY)) == 1){
                        if((mListElement.getTaskMinute() + 60 - mCurrentDate.get(Calendar.MINUTE)) == 15 ){
                            if(mNotificationString.equals(""))
                                mNotificationString = mListElement.getTaskName() + " (" + mListElement.getTaskTime() + ")";
                            else
                                mNotificationString += ", " + mListElement.getTaskName() + " (" + mListElement.getTaskTime() + ")";

                            mShowNotification = true;
                            mListElement.setmTaskReminder(false);
                        }
                    }
                }
            }

            if(mShowNotification){
                mNotificationBuilder.setContentText(mNotificationString);
                mNotificationManager.notify(notifNum, mNotificationBuilder.build());
                mShowNotification = false;
                notifNum++;
                mNotificationString = "";
            }

            try {
                sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
