package wang.tyrael.todo.presenter.main;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.csmall.android.ApplicationHolder;
import com.csmall.android.ToastUtil;
import com.example.avjindersinghsekhon.minimaltodo.AddToDoActivity;
import com.example.avjindersinghsekhon.minimaltodo.MainActivity;
import com.example.avjindersinghsekhon.minimaltodo.StoreRetrieveData;
import com.example.avjindersinghsekhon.minimaltodo.ToDoItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import wang.tyrael.todo.R;
import wang.tyrael.todo.biz.TodoAlarmBiz;
import wang.tyrael.todo.biz.theme.ThemeBiz;
import wang.tyrael.todo.eventbus.OperateEvent;
import wang.tyrael.todo.fragment.MainFragment;

import static wang.tyrael.todo.biz.theme.ThemeBiz.LIGHTTHEME;

/**
 * Created by Administrator on 2017/7/2 0002.
 */

public class MainPresenter {
    public static final String EVENT_REMOVE = "MainPresenter.EVENT_REMOVE";

    public static final String FILENAME = "todoitems.json";
    private static final Context context = ApplicationHolder.getApplication();

    private List<ToDoItem> items;


    private StoreRetrieveData storeRetrieveData;
    /**
     * 表格的事情委托给adapter 来处理
     */
    private MainAdapter adapter = new MainAdapter();
    public Object eventHandler = new Object(){
        @Subscribe(threadMode = ThreadMode.MAIN)
        public void onMessageEvent(OperateEvent event) {
            switch(event.typeId){
                case EVENT_REMOVE:
                    removeItem(0);
                    break;
            }
        };
    };

    public MainPresenter(){
        storeRetrieveData = new StoreRetrieveData(context, FILENAME);
    }

    public void connect(){
        EventBus.getDefault().register(eventHandler);
    }

    public void disconnect(){
        EventBus.getDefault().unregister(eventHandler);
    }

    //*********************
    public void moveItem(int from, int to){

    }

    public void removeItem(int position){
        ToastUtil.show("removeItem");
//        ToDoItem mJustDeletedToDoItem =  items.remove(position);
//        int mIndexOfDeletedToDoItem = position;
//
//        new TodoAlarmBiz().deleteAlarm( mJustDeletedToDoItem.getIdentifier().hashCode());
//        adapter.notifyItemRemoved(position);

//            String toShow = (mJustDeletedToDoItem.getToDoText().length()>20)?mJustDeletedToDoItem.getToDoText().substring(0, 20)+"...":mJustDeletedToDoItem.getToDoText();
//        String toShow = "事项";
//        Snackbar.make(mCoordLayout, "已删除 "+toShow,Snackbar.LENGTH_LONG)
//                .setAction("取消删除", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        items.add(mIndexOfDeletedToDoItem, mJustDeletedToDoItem);
//                        mJustDeletedToDoItem.checkSetAlarm();
//                        notifyItemInserted(mIndexOfDeletedToDoItem);
//                    }
//                }).show();
    }

    public void setDone(int position){

    }


    //****************************

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
