package com.customcalenderweeklydate;

/**
 * Created by tringapps-admin on 21/2/17.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.Calendar;

public class CalendarWeeklyView extends RelativeLayout implements OnClickListener {

    private Calendar calendar;
    private Calendar selectedDate;
    private View rootView, selectedDateView;
    /*private AvailableDate mAvailableDate;*/

    private TextView sunday, monday, tuesday, wednesday, thursday, friday, saturday;

    private OnDateClickListener onDateClickListener;

    public CalendarWeeklyView(Context context) {
        this(context, null, 0);
    }

    public CalendarWeeklyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarWeeklyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        LayoutInflater layoutInflater=((LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        layoutInflater.inflate(R.layout.calendar_weekly_view, this);
        selectedDate = Calendar.getInstance();

        rootView = findViewById(R.id.root);
        sunday = (TextView) findViewById(R.id.sunday);
        monday = (TextView) findViewById(R.id.monday);
        tuesday = (TextView) findViewById(R.id.tuesday);
        wednesday = (TextView) findViewById(R.id.wednesday);
        thursday = (TextView) findViewById(R.id.thursday);
        friday = (TextView) findViewById(R.id.friday);
        saturday = (TextView) findViewById(R.id.saturday);

        sunday.setOnClickListener(this);
        monday.setOnClickListener(this);
        tuesday.setOnClickListener(this);
        wednesday.setOnClickListener(this);
        thursday.setOnClickListener(this);
        friday.setOnClickListener(this);
        saturday.setOnClickListener(this);
    }

    public void setTextViewBackGround(int resid) {
        sunday.setBackgroundResource(resid);
        monday.setBackgroundResource(resid);
        tuesday.setBackgroundResource(resid);
        wednesday.setBackgroundResource(resid);
        thursday.setBackgroundResource(resid);
        friday.setBackgroundResource(resid);
        saturday.setBackgroundResource(resid);
    }

    public void setTextColor(int resid) {
        sunday.setTextColor(getResources().getColorStateList(resid));
        monday.setTextColor(getResources().getColorStateList(resid));
        tuesday.setTextColor(getResources().getColorStateList(resid));
        wednesday.setTextColor(getResources().getColorStateList(resid));
        thursday.setTextColor(getResources().getColorStateList(resid));
        friday.setTextColor(getResources().getColorStateList(resid));
        saturday.setTextColor(getResources().getColorStateList(resid));
    }

    public void setRootViewBackground(int background) {
        rootView.setBackgroundResource(background);
    }

    public void setCalendarData(Calendar calendar) {
        this.calendar = calendar;
        populateViews();
    }


    private void populateViews() {
        Calendar calendar = (Calendar) this.calendar.clone();

        if (selectedDateView != null) {
            selectedDateView.setSelected(false);
            selectedDateView = null;
        }

        if (isEqualToSelectedDate(calendar)) {
            selectedDateView = sunday;
        }
        if (hasSchedule(calendar)) {
            sunday.setBackgroundResource(R.drawable.selector_available_date_background);
        } else {
            sunday.setBackgroundResource(R.drawable.selector_date_background);
        }
        sunday.setText("" + calendar.get(Calendar.DATE));
        sunday.setTag(calendar.clone());

        calendar.add(Calendar.DATE, 1);
        if (isEqualToSelectedDate(calendar)) {
            selectedDateView = monday;
        }
        if (hasSchedule(calendar)) {
            monday.setBackgroundResource(R.drawable.selector_available_date_background);
        } else {
            monday.setBackgroundResource(R.drawable.selector_date_background);
        }
        monday.setText("" + calendar.get(Calendar.DATE));
        monday.setTag(calendar.clone());

        calendar.add(Calendar.DATE, 1);
        if (isEqualToSelectedDate(calendar)) {
            selectedDateView = tuesday;
        }
        if (hasSchedule(calendar)) {
            tuesday.setBackgroundResource(R.drawable.selector_available_date_background);
        } else {
            tuesday.setBackgroundResource(R.drawable.selector_date_background);
        }
        tuesday.setText("" + calendar.get(Calendar.DATE));
        tuesday.setTag(calendar.clone());

        calendar.add(Calendar.DATE, 1);
        if (isEqualToSelectedDate(calendar)) {
            selectedDateView = wednesday;
        }
        if (hasSchedule(calendar)) {
            wednesday.setBackgroundResource(R.drawable.selector_available_date_background);
        } else {
            wednesday.setBackgroundResource(R.drawable.selector_date_background);
        }
        wednesday.setText("" + calendar.get(Calendar.DATE));
        wednesday.setTag(calendar.clone());

        calendar.add(Calendar.DATE, 1);
        if (isEqualToSelectedDate(calendar)) {
            selectedDateView = thursday;
        }
        if (hasSchedule(calendar)) {
            thursday.setBackgroundResource(R.drawable.selector_available_date_background);
        } else {
            thursday.setBackgroundResource(R.drawable.selector_date_background);
        }
        thursday.setText("" + calendar.get(Calendar.DATE));
        thursday.setTag(calendar.clone());

        calendar.add(Calendar.DATE, 1);
        if (isEqualToSelectedDate(calendar)) {
            selectedDateView = friday;
        }
        if (hasSchedule(calendar)) {
            friday.setBackgroundResource(R.drawable.selector_available_date_background);
        } else {
            friday.setBackgroundResource(R.drawable.selector_date_background);
        }
        friday.setText("" + calendar.get(Calendar.DATE));
        friday.setTag(calendar.clone());

        calendar.add(Calendar.DATE, 1);
        if (isEqualToSelectedDate(calendar)) {
            selectedDateView = saturday;
        }
        if (hasSchedule(calendar)) {
            saturday.setBackgroundResource(R.drawable.selector_available_date_background);
        } else {
            saturday.setBackgroundResource(R.drawable.selector_date_background);
        }
        saturday.setText("" + calendar.get(Calendar.DATE));
        saturday.setTag(calendar.clone());

        if (selectedDateView != null) {
            selectedDateView.setSelected(true);
        }
    }

    private boolean isEqualToSelectedDate(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        return  day == selectedDate.get(Calendar.DATE) &&
                month == selectedDate.get(Calendar.MONTH) &&
                year == selectedDate.get(Calendar.YEAR);
    }

    public void setSelectedDate(Calendar calendar) {
        selectedDate = calendar;
        populateViews();
    }

    @Override
    public void onClick(View v) {
        selectedDate = (Calendar) v.getTag();
        populateViews();

        if (onDateClickListener != null) {
            onDateClickListener.onDateClick(selectedDate);
        }
    }

    /*public void setAvailableDate(AvailableDate availableDate) {
        mAvailableDate = availableDate;
        populateViews();
    }*/


    public interface OnDateClickListener {
        public void onDateClick(Calendar calendar);
    }

    public void setOnDateClickListener(OnDateClickListener dateClickListener) {
        onDateClickListener = dateClickListener;
    }

    public boolean hasSchedule(Calendar calendar) {
        /*if (mAvailableDate != null &&
                mAvailableDate.getDates() != null) {
            for (long timeInMilliSec : mAvailableDate.getDates()) {
                Calendar day = Calendar.getInstance();
                day.setTimeInMillis(timeInMilliSec);

                if (calendar.get(Calendar.DATE) == day.get(Calendar.DATE)) {
                    return true;
                }
            }
        }*/
        return false;
    }
}
