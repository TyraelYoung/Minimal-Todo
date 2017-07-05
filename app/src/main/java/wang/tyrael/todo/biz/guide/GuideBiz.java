package wang.tyrael.todo.biz.guide;

import com.example.avjindersinghsekhon.minimaltodo.ToDoItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tyraelwang on 2017/7/5 0005.
 */

public class GuideBiz {


    public List<ToDoItem> getGuideData(){
        List<ToDoItem> list = new ArrayList<>();
        ToDoItem toDoItem = new ToDoItem();
        list.add(toDoItem);
        return list;
    }
}
