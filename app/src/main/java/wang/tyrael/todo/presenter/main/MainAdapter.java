package wang.tyrael.todo.presenter.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amulyakhare.textdrawable.TextDrawable;
import com.csmall.android.ApplicationHolder;
import com.example.avjindersinghsekhon.minimaltodo.AddToDoActivity;
import com.example.avjindersinghsekhon.minimaltodo.MainActivity;
import com.example.avjindersinghsekhon.minimaltodo.ToDoItem;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import wang.tyrael.todo.R;
import wang.tyrael.todo.biz.theme.ThemeBiz;
import wang.tyrael.todo.eventbus.UpdateUiEvent;
import wang.tyrael.todo.fragment.MainFragment;

import static wang.tyrael.todo.biz.theme.ThemeBiz.LIGHTTHEME;

/**
 * Created by tyraelwang on 2017/7/5 0005.
 */

public class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {
    public static final String TODOITEM = "com.avjindersinghsekhon.com.avjindersinghsekhon.minimaltodo.MainActivity";
    private static final int REQUEST_ID_TODO_ITEM = 100;
    private static final Context context = ApplicationHolder.getApplication();

    private List<ToDoItem> items;

    public void updateData( List<ToDoItem> items){
        this.items = items;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_circle_try, parent, false);
        return new MainViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MainViewHolder holder, final int position) {
        String theme = ThemeBiz.getThemeId();
        ToDoItem item = items.get(position);
        //Background color for each to-do item. Necessary for night/day mode
        int bgColor;
        //color of title text in our to-do item. White for night mode, dark gray for day mode
        int todoTextColor;
        if (theme.equals(LIGHTTHEME)) {
            bgColor = Color.WHITE;
            todoTextColor = context.getResources().getColor(R.color.secondary_text);
        } else {
            bgColor = Color.DKGRAY;
            todoTextColor = Color.WHITE;
        }
        holder.linearLayout.setBackgroundColor(bgColor);

        if (item.hasReminder() && item.getToDoDate() != null) {
            holder.mToDoTextview.setMaxLines(1);
            holder.mTimeTextView.setVisibility(View.VISIBLE);
//                holder.mToDoTextview.setVisibility(View.GONE);
        } else {
            holder.mTimeTextView.setVisibility(View.GONE);
            holder.mToDoTextview.setMaxLines(2);
        }
        holder.mToDoTextview.setText(item.getToDoText());
        holder.mToDoTextview.setTextColor(todoTextColor);

        TextDrawable myDrawable = TextDrawable.builder().beginConfig()
                .textColor(Color.WHITE)
                .useFont(Typeface.DEFAULT)
                .toUpperCase()
                .endConfig()
                .buildRound(item.getToDoText().substring(0, 1), item.getTodoColor());

//            TextDrawable myDrawable = TextDrawable.builder().buildRound(item.getToDoText().substring(0,1),holder.color);
        holder.mColorImageView.setImageDrawable(myDrawable);
        if (item.getToDoDate() != null) {
            String timeToShow;
            Context context = ApplicationHolder.getApplication();
            if (android.text.format.DateFormat.is24HourFormat(context)) {
                timeToShow = AddToDoActivity.formatDate(MainActivity.DATE_TIME_FORMAT_24_HOUR, item.getToDoDate());
            } else {
                timeToShow = AddToDoActivity.formatDate(MainActivity.DATE_TIME_FORMAT_12_HOUR, item.getToDoDate());
            }
            holder.mTimeTextView.setText(timeToShow);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDoItem item = items.get(position);
                UpdateUiEvent event = new UpdateUiEvent();
                event.typeId = MainFragment.EVENT_TO_TODO_DETAIL;
                event.data = item;
                EventBus.getDefault().post(event);
//                Intent i = new Intent(MainActivity.this, AddToDoActivity.class);
//                i.putExtra(TODOITEM, item);
//                startActivityForResult(i, REQUEST_ID_TODO_ITEM);
            }
        });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
