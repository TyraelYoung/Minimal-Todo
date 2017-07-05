package wang.tyrael.todo.biz;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.csmall.android.ApplicationHolder;
import com.example.avjindersinghsekhon.minimaltodo.ToDoItem;

import java.util.ArrayList;
import java.util.Date;

import wang.tyrael.todo.service.TodoNotificationService;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by tyraelwang on 2017/7/3 0003.
 */

public class TodoAlarmBiz {
    Context context = ApplicationHolder.getApplication();

    public void setAlarms(ArrayList<ToDoItem> mToDoItemsArrayList){
        if(mToDoItemsArrayList!=null){
            for(ToDoItem item : mToDoItemsArrayList){
                if(item.hasReminder() && item.getToDoDate()!=null){
                    if(item.getToDoDate().before(new Date())){
                        item.setToDoDate(null);
                        continue;
                    }
                    Intent i = new Intent(context, TodoNotificationService.class);
                    i.putExtra(TodoNotificationService.TODOUUID, item.getIdentifier());
                    i.putExtra(TodoNotificationService.TODOTEXT, item.getToDoText());
                    createAlarm(i, item.getIdentifier().hashCode(), item.getToDoDate().getTime());
                }
            }
        }
    }

    public void createAlarm(ToDoItem mJustDeletedToDoItem){
        Context context = ApplicationHolder.getApplication();
        Intent i = new Intent(context, TodoNotificationService.class);
        i.putExtra(TodoNotificationService.TODOTEXT, mJustDeletedToDoItem.getToDoText());
        i.putExtra(TodoNotificationService.TODOUUID, mJustDeletedToDoItem.getIdentifier());

        AlarmManager am = getAlarmManager();
        int requestCode = mJustDeletedToDoItem.getIdentifier().hashCode();
        long timeInMillis = mJustDeletedToDoItem.getToDoDate().getTime();
        PendingIntent pi = PendingIntent.getService(context,requestCode, i, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP, timeInMillis, pi);
//        Log.d("OskarSchindler", "createAlarm "+requestCode+" time: "+timeInMillis+" PI "+pi.toString());
    }

    public void createAlarm(Intent i, int requestCode, long timeInMillis){
        AlarmManager am = getAlarmManager();
        PendingIntent pi = PendingIntent.getService(context,requestCode, i, PendingIntent.FLAG_UPDATE_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP, timeInMillis, pi);
//        Log.d("OskarSchindler", "createAlarm "+requestCode+" time: "+timeInMillis+" PI "+pi.toString());
    }

    public void deleteAlarm(int requestCode){
        Context context = ApplicationHolder.getApplication();
        Intent i = new Intent(context,TodoNotificationService.class);
        if(doesPendingIntentExist(i, requestCode)){
            PendingIntent pi = PendingIntent.getService(context, requestCode,i, PendingIntent.FLAG_NO_CREATE);
            pi.cancel();
            getAlarmManager().cancel(pi);
            Log.d("OskarSchindler", "PI Cancelled " + doesPendingIntentExist(i, requestCode));
        }
    }


    /**
     * @deprecated
     * @param i
     * @param requestCode
     */
    public void deleteAlarm(Intent i, int requestCode){
        this.deleteAlarm(requestCode);
    }

    private AlarmManager getAlarmManager(){
        return (AlarmManager)context.getSystemService(ALARM_SERVICE);
    }

    private boolean doesPendingIntentExist(Intent i, int requestCode){
        PendingIntent pi = PendingIntent.getService(context,requestCode, i, PendingIntent.FLAG_NO_CREATE);
        return pi!=null;
    }
}
