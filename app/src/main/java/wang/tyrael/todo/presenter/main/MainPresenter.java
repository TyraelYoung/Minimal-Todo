package wang.tyrael.todo.presenter.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.csmall.android.ApplicationHolder;
import com.csmall.android.ToastUtil;
import wang.tyrael.todo.biz.StoreRetrieveData;
import com.example.avjindersinghsekhon.minimaltodo.ToDoItem;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import wang.tyrael.todo.biz.TodoAlarmBiz;

/**
 * Created by Administrator on 2017/7/2 0002.
 */

public class MainPresenter {
    public static final String FILENAME = "todoitems.json";
    private static final Context context = ApplicationHolder.getApplication();

    private List<ToDoItem> items;

    private StoreRetrieveData storeRetrieveData;
    /**
     * 表格的事情委托给adapter 来处理
     */
    private MainAdapter adapter = new MainAdapter();

    public MainPresenter(){
        storeRetrieveData = new StoreRetrieveData(context, FILENAME);
        loadTodoList();
        adapter.updateData(items);
    }

    public RecyclerView.Adapter getAdapter(){
        return adapter;
    }

    //*********************
    public void moveTofirst(int position){
        moveItem(position, 0);
    }

    public void moveToLast(int position){
        moveItem(position, items.size()-1);
    }

    public void moveItem(int fromPosition, int toPosition){
        //不仅仅是移动两个元素
        if(fromPosition<toPosition){
            for(int i=fromPosition; i<toPosition; i++){
                Collections.swap(items, i, i+1);
            }
        }
        else{
            for(int i=fromPosition; i > toPosition; i--){
                Collections.swap(items, i, i-1);
            }
        }
        adapter.updateData(items);
        adapter.notifyDataSetChanged();
        //TODO 从0扔到最后一个，会显示不出来。
//        adapter.notifyItemMoved(fromPosition, toPosition);
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
        items.add(0, item);
        adapter.updateData(items);
        adapter.notifyItemInserted(0);
    }

    public void deleteItem(int position){
        ToDoItem mJustDeletedToDoItem =  items.remove(position);

        new TodoAlarmBiz().deleteAlarm( mJustDeletedToDoItem.getIdentifier().hashCode());
        adapter.updateData(items);
        adapter.notifyItemRemoved(position);

        ToastUtil.show("已删除");
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
        ToDoItem mJustDeletedToDoItem =  items.remove(position);

        new TodoAlarmBiz().deleteAlarm( mJustDeletedToDoItem.getIdentifier().hashCode());
        adapter.updateData(items);
        adapter.notifyItemRemoved(position);

        ToastUtil.show("已完成 ");
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
