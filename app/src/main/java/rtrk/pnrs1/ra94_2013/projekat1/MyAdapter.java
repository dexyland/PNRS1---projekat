package rtrk.pnrs1.ra94_2013.projekat1;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter{

    private Context mContext;
    private ArrayList<listElement> mTaskList;
    private MyDbHelper mDb;

    public MyAdapter(Context context)
    {
        mContext = context;
        mTaskList = new ArrayList<listElement>();
    }

    public void addTask(listElement mListElement)
    {
        mTaskList.add(mListElement);
        notifyDataSetChanged();
    }

    public void update(listElement[] mTasks){
        mTaskList.clear();

        for(listElement mTask: mTasks)
                mTaskList.add(mTask);

        Log.d("ADAPTEEEEEEEEER", "PUNIM LISTUUU");
        notifyDataSetChanged();
    }

    public ArrayList<listElement> getTasks(){
        return mTaskList;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public int getCount()
    {
        return mTaskList.size();
    }

    @Override
    public Object getItem(int position)
    {
        Object mReturnValue = null;
        try
        {
            mReturnValue = mTaskList.get(position);
        }
        catch (IndexOutOfBoundsException e)
        {
            e.printStackTrace();
        }

        return mReturnValue;
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View mView = convertView;
        if(mView == null)
        {
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = mInflater.inflate(R.layout.list_element, null);
            ViewHolder mViewHolder = new ViewHolder();
            mViewHolder.mName = (TextView) mView.findViewById(R.id.TaskName);
            mViewHolder.mDate = (TextView) mView.findViewById(R.id.date);
            mViewHolder.mTime = (TextView) mView.findViewById(R.id.time);
            mViewHolder.mPriority= (ImageView) mView.findViewById(R.id.priority);
            mViewHolder.mReminder = (ImageView) mView.findViewById(R.id.reminder);
            mViewHolder.mCheckbox = (CheckBox) mView.findViewById(R.id.check);
            mView.setTag(mViewHolder);
        }

        listElement mListElement = (listElement) getItem(position);
        final ViewHolder mViewHolder = (ViewHolder) mView.getTag();
        mViewHolder.mName.setText(mListElement.mTaskName);
        String mTaskDate = pad(mListElement.getTaskDay()) + "-" + pad(mListElement.getTaskMonth()+1) + "-" + mListElement.getTaskYear();
        mViewHolder.mDate.setText(mTaskDate);
        String mTaskTime = pad(mListElement.getTaskHour()) +":"+ pad(mListElement.getTaskMinute());
        mViewHolder.mTime.setText(mTaskTime);

        switch (mListElement.mTaskPriority){
            case "low":
                        mViewHolder.mPriority.setImageResource(R.drawable.green_list_icon);
                        break;
            case "medium":
                        mViewHolder.mPriority.setImageResource(R.drawable.yellow_list_icon);
                        break;
            case "high":
                        mViewHolder.mPriority.setImageResource(R.drawable.red_list_icon);
                        break;
        }

        if(mListElement.mTaskReminder == 1)
            mViewHolder.mReminder.setImageResource(R.drawable.bell);
        else
            mViewHolder.mReminder.setImageResource(R.drawable.bellmute);

        mViewHolder.mCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    mViewHolder.mName.setPaintFlags(mViewHolder.mName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    listElement mTask = MainActivity.mDb.readTask(String.valueOf(position+1));
                    mTask.setTaskFinished(1);
                    MainActivity.mDb.updateTask(mTask, String.valueOf(position+1));
                    Log.d("ADAPTEEEEER","MENJAM FINISHED"+position);
                }
                else
                {
                    mViewHolder.mName.setPaintFlags(mViewHolder.mName.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                    listElement mTask = MainActivity.mDb.readTask(String.valueOf(position+1));
                    mTask.setTaskFinished(0);
                    MainActivity.mDb.updateTask(mTask, String.valueOf(position+1));
                }
            }
        });
        return mView;
    }

    private class ViewHolder
    {
        public TextView mName = null;
        public TextView mDate = null;
        public TextView mTime = null;
        public ImageView mPriority = null;
        public ImageView mReminder = null;
        public CheckBox mCheckbox = null;
    }

    private static String pad(int c) {                                                              // Adds '0' to single number dates so the date format would always stay the same (DD-MM-YYYY)
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
}
