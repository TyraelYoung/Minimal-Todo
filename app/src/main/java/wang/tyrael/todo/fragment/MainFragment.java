package wang.tyrael.todo.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.csmall.android.ApplicationHolder;
import com.csmall.android.ToastUtil;
import com.example.avjindersinghsekhon.minimaltodo.AddToDoActivity;
import com.example.avjindersinghsekhon.minimaltodo.CustomRecyclerScrollViewListener;
import com.example.avjindersinghsekhon.minimaltodo.RecyclerViewEmptySupport;
import com.example.avjindersinghsekhon.minimaltodo.ToDoItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import wang.tyrael.todo.R;
import wang.tyrael.todo.biz.TodoAlarmBiz;
import wang.tyrael.todo.biz.TodoBiz;
import wang.tyrael.todo.biz.theme.ThemeBiz;
import wang.tyrael.todo.eventbus.UpdateUiEvent;
import wang.tyrael.todo.presenter.main.MainPresenter;
import wang.tyrael.todo.service.TodoNotificationService;

import static wang.tyrael.todo.biz.theme.ThemeBiz.LIGHTTHEME;

public class MainFragment extends Fragment {
    public static final String EVENT_TO_TODO_DETAIL = "EVENT_TO_TODO_DETAIL";

    private View rootView;
    private Toolbar toolbar;

    private RecyclerViewEmptySupport mRecyclerView;
    private FloatingActionButton mAddToDoItemFAB;

    private CoordinatorLayout mCoordLayout;
    public static final String TODOITEM = "com.avjindersinghsekhon.com.avjindersinghsekhon.minimaltodo.MainActivity";

    private static final int REQUEST_ID_TODO_ITEM = 100;

    public static final String DATE_TIME_FORMAT_12_HOUR = "MMM d, yyyy  h:mm a";
    public static final String DATE_TIME_FORMAT_24_HOUR = "MMM d, yyyy  k:mm";
    public static final String FILENAME = "todoitems.json";

    private CustomRecyclerScrollViewListener customRecyclerScrollViewListener;
    public static final String SHARED_PREF_DATA_SET_CHANGED = "com.avjindersekhon.datasetchanged";
    public static final String CHANGE_OCCURED = "com.avjinder.changeoccured";
    private int mTheme = -1;
    private String theme = "name_of_the_theme";

    private MainPresenter presenter = new MainPresenter();

    private Object updateEventHandler = new Object(){
        @Subscribe(threadMode = ThreadMode.MAIN)
        public void onMessageEvent(UpdateUiEvent event) {
            switch(event.typeId){
                case EVENT_TO_TODO_DETAIL:
                    ToDoItem toDoItem= (ToDoItem) event.data;
                    toToDoDetail(toDoItem);
                    break;
            }
        };
    };

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    public Toolbar getToolbar(){
        return toolbar;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        theme = ThemeBiz.getThemeId();
        mTheme = ThemeBiz.getStyle();
        EventBus.getDefault().register(updateEventHandler);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        rootView = inflater.inflate(R.layout.activity_main, container, false);
        TodoBiz.setDataChangeHandled();

        presenter.loadTodoList();

        toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);





        mCoordLayout = (CoordinatorLayout)findViewById(R.id.myCoordinatorLayout);
        mAddToDoItemFAB = (FloatingActionButton)findViewById(R.id.addToDoItemFAB);

        mAddToDoItemFAB.setOnClickListener(new View.OnClickListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                Context context = getActivity();
                Intent newTodo = new Intent(context, AddToDoActivity.class);
                ToDoItem item = new ToDoItem("", false, null);
                int color = ColorGenerator.MATERIAL.getRandomColor();
                item.setTodoColor(color);
                newTodo.putExtra(TODOITEM, item);
                startActivityForResult(newTodo, REQUEST_ID_TODO_ITEM);
            }
        });

        mRecyclerView = (RecyclerViewEmptySupport)findViewById(R.id.toDoRecyclerView);
        if(theme.equals(LIGHTTHEME)){
            mRecyclerView.setBackgroundColor(getResources().getColor(R.color.primary_lightest));
        }
        mRecyclerView.setEmptyView(findViewById(R.id.toDoEmptyView));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));


//解决滑到底部看不见的问题
        customRecyclerScrollViewListener = new CustomRecyclerScrollViewListener() {
            @Override
            public void show() {
                mAddToDoItemFAB.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
            }

            @Override
            public void hide() {

                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)mAddToDoItemFAB.getLayoutParams();
                int fabMargin = lp.bottomMargin;
                mAddToDoItemFAB.animate().translationY(mAddToDoItemFAB.getHeight()+fabMargin).setInterpolator(new AccelerateInterpolator(2.0f)).start();
            }
        };
        mRecyclerView.addOnScrollListener(customRecyclerScrollViewListener);


//        ItemTouchHelper.Callback callback = new ItemTouchHelperClass(adapter);
//        itemTouchHelper = new ItemTouchHelper(callback);
//        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        ItemTouchHelper itemTouchHelper2 = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END |ItemTouchHelper.UP | ItemTouchHelper.DOWN;

                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                presenter.moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                switch(direction){
                    case ItemTouchHelper.UP:
                        presenter.moveTofirst(viewHolder.getAdapterPosition());
                        break;
                    case ItemTouchHelper.DOWN:
                        presenter.moveToLast(viewHolder.getAdapterPosition());
                        break;
                    case ItemTouchHelper.START:
                        // 左滑删除
                        presenter.deleteItem(viewHolder.getAdapterPosition());
                        break;
                    case ItemTouchHelper.END:
                        //右滑完成
                        presenter.setDone(viewHolder.getAdapterPosition());
                        break;

                }
            }
        });
        itemTouchHelper2.attachToRecyclerView(mRecyclerView);

        mRecyclerView.setAdapter(presenter.getAdapter());
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_DATA_SET_CHANGED, Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean(CHANGE_OCCURED, false)){

            presenter.loadTodoList();
            mRecyclerView.setAdapter(presenter.getAdapter());

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(CHANGE_OCCURED, false);
            editor.apply();
        }
    }

    /**
     * 每次离开该页面，数据都可能修改，在这里加载数据，
     * 是否考虑使用总线、监听器等方式？
     */
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.persist();
    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(updateEventHandler);
        super.onDestroy();
        mRecyclerView.removeOnScrollListener(customRecyclerScrollViewListener);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!= Activity.RESULT_CANCELED && requestCode == REQUEST_ID_TODO_ITEM){
            ToDoItem item =(ToDoItem) data.getSerializableExtra(TODOITEM);
            if(item.getToDoText().length()<=0){
                return;
            }

            if(item.hasReminder() && item.getToDoDate()!=null){
                Intent i = new Intent(getContext(), TodoNotificationService.class);
                i.putExtra(TodoNotificationService.TODOTEXT, item.getToDoText());
                i.putExtra(TodoNotificationService.TODOUUID, item.getIdentifier());
                new TodoAlarmBiz().createAlarm(i, item.getIdentifier().hashCode(), item.getToDoDate().getTime());
//                Log.d("OskarSchindler", "Alarm Created: "+item.getToDoText()+" at "+item.getToDoDate());
            }

            if(!presenter.updateItem(item)) {
                presenter.insertItem(item);
            }


        }
    }

    private View findViewById(int id){
        return rootView.findViewById(id);
    }

    private void toToDoDetail(ToDoItem item){
        Context context = ApplicationHolder.getApplication();
        Intent i = new Intent(context, AddToDoActivity.class);
        i.putExtra(TODOITEM, item);
        startActivityForResult(i, REQUEST_ID_TODO_ITEM);
    }
}
