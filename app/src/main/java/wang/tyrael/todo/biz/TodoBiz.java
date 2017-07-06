package wang.tyrael.todo.biz;

import android.content.Context;
import android.content.SharedPreferences;

import com.csmall.android.ApplicationHolder;
import com.example.avjindersinghsekhon.minimaltodo.MainActivity;
import com.example.avjindersinghsekhon.minimaltodo.ToDoItem;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tyraelwang on 2017/7/3 0003.
 */

public class TodoBiz {
    public static final String SHARED_PREF_DATA_SET_CHANGED = "com.avjindersekhon.datasetchanged";
    public static final String CHANGE_OCCURED = "com.avjinder.changeoccured";

    private static Context context = ApplicationHolder.getApplication();

    private StoreRetrieveData storeRetrieveData;

    public TodoBiz(){
        storeRetrieveData = new StoreRetrieveData(context, MainActivity.FILENAME);
    }

    public static void setDataChanged(){

    }

    public static void setDataChangeHandled(){
        //处理通知发生的数据改变
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_DATA_SET_CHANGED, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(CHANGE_OCCURED, false);
        editor.apply();
    }

    public void persist(List<ToDoItem> items){
        try {
            storeRetrieveData.saveToFile(items);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ToDoItem> load(){
        ArrayList<ToDoItem> items = null;

        try {
            items  = storeRetrieveData.loadFromFile();

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        if(items == null){
            items = new ArrayList<>();
        }
        return items;

    }
}
