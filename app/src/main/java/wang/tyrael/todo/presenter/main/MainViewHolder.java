package wang.tyrael.todo.presenter.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import wang.tyrael.todo.R;

/**
 * Created by tyraelwang on 2017/7/5 0005.
 */

public class MainViewHolder extends RecyclerView.ViewHolder {

    View mView;
    LinearLayout linearLayout;
    TextView mToDoTextview;
    //            TextView mColorTextView;
    ImageView mColorImageView;
    TextView mTimeTextView;
//            int color = -1;

    public MainViewHolder(View v) {
        super(v);
        mView = v;
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    ToDoItem item = items.get(ViewHolder.this.getAdapterPosition());
//                    Intent i = new Intent(context, AddToDoActivity.class);
//                    i.putExtra(TODOITEM, item);
//                    context.startActivityForResult(i, REQUEST_ID_TODO_ITEM);
            }
        });
        mToDoTextview = (TextView) v.findViewById(R.id.toDoListItemTextview);
        mTimeTextView = (TextView) v.findViewById(R.id.todoListItemTimeTextView);
//                mColorTextView = (TextView)v.findViewById(R.id.toDoColorTextView);
        mColorImageView = (ImageView) v.findViewById(R.id.toDoListItemColorImageView);
        linearLayout = (LinearLayout) v.findViewById(R.id.listItemLinearLayout);
    }
}
