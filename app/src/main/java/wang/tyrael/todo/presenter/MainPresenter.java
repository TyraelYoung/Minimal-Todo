package wang.tyrael.todo.presenter;

import android.app.Activity;
import android.content.Context;

import com.csmall.android.ApplicationHolder;
import com.example.avjindersinghsekhon.minimaltodo.StoreRetrieveData;
import com.example.avjindersinghsekhon.minimaltodo.ToDoItem;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/2 0002.
 */

public class MainPresenter {
    public static final String FILENAME = "todoitems.json";

    private StoreRetrieveData storeRetrieveData;
    private static final Context context = ApplicationHolder.getApplication();

    public MainPresenter(){
        storeRetrieveData = new StoreRetrieveData(context, FILENAME);
    }

    public void persist(ArrayList<ToDoItem> items){
        try {
            storeRetrieveData.saveToFile(items);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ToDoItem> loadTodoList(){
        return MainPresenter.getLocallyStoredData(storeRetrieveData);
    }

    public static ArrayList<ToDoItem> getLocallyStoredData(StoreRetrieveData storeRetrieveData){
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
