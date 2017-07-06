package wang.tyrael.todo.presenter.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.csmall.android.ApplicationHolder;
import com.csmall.android.ToastUtil;
import com.example.avjindersinghsekhon.minimaltodo.StoreRetrieveData;
import com.example.avjindersinghsekhon.minimaltodo.ToDoItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import wang.tyrael.todo.biz.TodoAlarmBiz;
import wang.tyrael.todo.eventbus.OperateEvent;

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
                    deleteItem(0);
                    break;
            }
        };
    };

    public MainPresenter(){
        storeRetrieveData = new StoreRetrieveData(context, FILENAME);
    }

    public RecyclerView.Adapter getAdapter(){
        return adapter;
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

    public boolean updateItem(ToDoItem item){
        for(int i = 0; i<items.size();i++){
            if(item.getIdentifier().equals(items.get(i).getIdentifier())){
                items.set(i, item);
                adapter.notifyDataSetChanged();
                return true;
            }
        }
        return false;
    }

    public void insertItem(ToDoItem item){
        items.add(item);
        adapter.updateData(items);
        adapter.notifyItemInserted(items.size() - 1);
    }

    public void deleteItem(int position){
        ToastUtil.show("deleteItem");
        ToDoItem mJustDeletedToDoItem =  items.remove(position);

        new TodoAlarmBiz().deleteAlarm( mJustDeletedToDoItem.getIdentifier().hashCode());
        adapter.notifyItemRemoved(position);

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

    public void persist(){
        try {
            storeRetrieveData.saveToFile(items);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<ToDoItem> loadTodoList(){
        items = MainPresenter.getLocallyStoredData(storeRetrieveData);
        new TodoAlarmBiz().setAlarms(items);
        return items;
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
