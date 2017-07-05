package wang.tyrael.todo.eventbus;

/**
 * Created by tyraelwang on 2017/7/5 0005.
 */

public class Event {
    /**
     * 一般建议在接受事件的类里定义常量，通常一个事件只有一个类接受。
     * 加前缀避免重复
     */
    public String typeId;
    public Object data;
}
