package rtrk.pnrs1.ra94_2013.projekat1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyService extends Service {

    private MyNotification mNotifications;
    private ReminderThread mThread;

    @Override
    public void onCreate(){
        mThread = new ReminderThread(this);
        mThread.start();
        mNotifications = new MyNotification(this);
        super.onCreate();
    }

    @Override
    public void onDestroy(){
        mThread.exit();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mNotifications;
    }
}
