package com.customcalenderweeklydate;

/**
 * Created by tringapps-admin on 21/2/17.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.Calendar;
import java.util.Locale;

public class CustomCalendarWeeklyView extends RelativeLayout implements InfiniteCalendarView.OnDateChangeListener {

    private OnCurrentDateChangeListener mChangeListener;
    private InfiniteCalendarView mCalendarView;
    private TextView mDate;
    //private AvailableDate mAvailableDate;

    public CustomCalendarWeeklyView(Context context) {
        super(context, null, 0);
    }

    public CustomCalendarWeeklyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomCalendarWeeklyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    /*public void setAvailableDate(AvailableDate availableDate) {
        mAvailableDate = availableDate;
        mCalendarView.setAvailableDate(mAvailableDate);
    }*/

    private void initView() {
        LayoutInflater layoutInflater=((LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        layoutInflater.inflate(R.layout.custom_calendar_weekly_view, this);
        mDate = (TextView) findViewById(R.id.fullDate);
        mCalendarView = (InfiniteCalendarView) findViewById(R.id.infiniteCalenderView);
        mCalendarView.setOnDateChangeListener(this);
        mDate.setText("Today");
    }

    public void setCurrentDate(Calendar calendar) {
        mCalendarView.setCurrentlySelectedDate(calendar);
        onDateChange(calendar, true);
    }

    public Calendar getCurrentDate() {
        return mCalendarView.getCurrentlySelectedDate();
    }

    public void setFullDateVisibility(int visibility) {
        mDate.setVisibility(visibility);
    }

    @Override
    public void onDateChange(Calendar calendar, boolean isWeekChange) {
        if (mChangeListener != null) {
            mChangeListener.onCurrentDateChange(calendar, isWeekChange);
        }

        if (isCurrentDay(calendar)) {
            mDate.setText("Today");
            return;
        }

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()));
        stringBuffer.append(", ");
        stringBuffer.append(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
        stringBuffer.append(" ");
        stringBuffer.append(calendar.get(Calendar.DATE));
        //stringBuffer.append(addDateSubtraction(calendar.get(Calendar.DATE)));
        stringBuffer.append(" ");
        stringBuffer.append(calendar.get(Calendar.YEAR));
        mDate.setText(stringBuffer.toString());
    }

    private boolean isCurrentDay(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        return  day == Calendar.getInstance().get(Calendar.DATE) &&
                month == Calendar.getInstance().get(Calendar.MONTH) &&
                year == Calendar.getInstance().get(Calendar.YEAR);
    }

    public void setCalendarColor(int color) {
        mCalendarView.setBackgroundColor(color);
    }

    public void setDateTextViewBackground(int color) {
        mCalendarView.setDateTextViewBackground(color);
    }

    public void setDateTextViewColor(int color) {
        mCalendarView.setDateTextViewColor(color);
    }

    public void setLabelTextColor(int resID) {
        mCalendarView.setLabelTextColor(resID);
    }

    public interface OnCurrentDateChangeListener {
        public void onCurrentDateChange(Calendar calendar, boolean isWeekChange);
    }

    public void setOnCurrentDateChangeLister(OnCurrentDateChangeListener dateChangeListener) {
        mChangeListener = dateChangeListener;
    }

    /*private String addDateSubtraction(int date)
    {
        switch (date)
        {
            case 1:
                return date+"st";
            case 2:
                return date+"nd";
            case 3:
                return date+"rd";
            case 21:
                return date+"st";
            case 22:
                return date+"nd";
            case 23:
                return date+"rd";
            case 31:
                return date+"st";
            default:
                return date+"th";
        }
    }*/
}
