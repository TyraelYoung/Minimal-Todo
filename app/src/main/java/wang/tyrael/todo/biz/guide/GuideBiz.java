package wang.tyrael.todo.biz.guide;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.csmall.android.PreferenceHelper;
import com.example.avjindersinghsekhon.minimaltodo.ToDoItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import wang.tyrael.todo.biz.TodoBiz;

/**
 * Created by tyraelwang on 2017/7/5 0005.
 */

public class GuideBiz {
    private static final String IS_FIRST_USE = "IS_FIRST_USE";

    public boolean isFirstUse(){
        //找不到值则是第一次使用
        return new PreferenceHelper().getBoolean(IS_FIRST_USE, true);
    }

    public void setUsed(){
        new PreferenceHelper().putBoolean(IS_FIRST_USE, false);
    }

    /**
     * 用户第一次使用，做一些初始化
     */
    public void onFirstUse(){
        List<ToDoItem> guideData = getGuideData();
        // 实际上第一次使用不存在用户数据，这里，我心虚了，万一错误调用就清除用户数据了，
        // 保险一点。
        TodoBiz todoBiz = new TodoBiz();
        List<ToDoItem> useData = todoBiz.load();
        guideData.addAll(useData);
        todoBiz.persist(guideData);
    }

    public List<ToDoItem> getGuideData(){
        List<ToDoItem> list = new ArrayList<>();
        ToDoItem toDoItem = new ToDoItem();
        toDoItem.setToDoText("别忘了预约、买票、别人欠你的钱！！等等。用笔头把他们记下来，每天看看还有什么事没做。");
        toDoItem.setTodoColor(ColorGenerator.MATERIAL.getRandomColor());
        list.add(toDoItem);

        toDoItem = new ToDoItem();
        toDoItem.setToDoText("试一试：交换顺序：长按3秒，拖动");
        toDoItem.setTodoColor(ColorGenerator.MATERIAL.getRandomColor());
        list.add(toDoItem);

        toDoItem = new ToDoItem();
        toDoItem.setToDoText("试一试：置顶：按住略向右滑，再快速往上扔，可以扔到第一条");
        toDoItem.setTodoColor(ColorGenerator.MATERIAL.getRandomColor());
        list.add(toDoItem);

        toDoItem = new ToDoItem();
        toDoItem.setToDoText("试一试：右滑完成：事情做完了");
        toDoItem.setTodoColor(ColorGenerator.MATERIAL.getRandomColor());
        list.add(toDoItem);

        toDoItem = new ToDoItem();
        toDoItem.setToDoText("试一试：左滑删除：事情取消了");
        toDoItem.setTodoColor(ColorGenerator.MATERIAL.getRandomColor());
        list.add(toDoItem);

        return list;
    }
}
