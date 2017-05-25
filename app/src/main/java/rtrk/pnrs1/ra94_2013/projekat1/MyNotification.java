package rtrk.pnrs1.ra94_2013.projekat1;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.RemoteException;

public class MyNotification extends IMyAidlInterface.Stub{
    private NotificationManager mNotificationManager;
    private Notification.Builder mNotificationBuilder;
    private Context mContext;
    private int notifyId = 1;

    public MyNotification(Context context){
        mContext = context;
    }

    public void onNotificationAdd(String taskName) throws RemoteException{
        mNotificationBuilder = new Notification.Builder(mContext);
        mNotificationBuilder.setContentTitle("Task manager");
        mNotificationBuilder.setSmallIcon(R.drawable.small_icon);
        mNotificationBuilder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(),R.drawable.notif_icon));
        mNotificationBuilder.setContentText("Task '" + taskName + "' added!");

        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(notifyId, mNotificationBuilder.build());
    }

    public void onNotificationEdit(String taskName) throws RemoteException{
        mNotificationBuilder = new Notification.Builder(mContext);
        mNotificationBuilder.setContentTitle("Task manager");
        mNotificationBuilder.setSmallIcon(R.drawable.small_icon);
        mNotificationBuilder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(),R.drawable.notif_icon));
        mNotificationBuilder.setContentText("Task '" + taskName + "' edited!");

        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(notifyId, mNotificationBuilder.build());
    }
    public void onNotificationDelete(String taskName) throws RemoteException{
        mNotificationBuilder = new Notification.Builder(mContext);
        mNotificationBuilder.setContentTitle("Task manager");
        mNotificationBuilder.setSmallIcon(R.drawable.small_icon);
        mNotificationBuilder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(),R.drawable.notif_icon));
        mNotificationBuilder.setContentText("Task deleted!");

        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(notifyId, mNotificationBuilder.build());
    }
}
