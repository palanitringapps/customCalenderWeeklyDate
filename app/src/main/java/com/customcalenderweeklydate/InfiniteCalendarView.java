package com.customcalenderweeklydate;

/**
 * Created by tringapps-admin on 21/2/17.
 */

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.Calendar;

public class InfiniteCalendarView extends RelativeLayout implements OnGestureListener, CalendarWeeklyView.OnDateClickListener {
    private final int NO_OF_DAYS_IN_A_WEEK = 7;
    private boolean isAnimating = false;
    private Calendar calendar;
    private Calendar currentlySelectedDate = Calendar.getInstance();
    private CalendarWeeklyView leftCalendarView, middleCalendarView, rightCalendarView;
    private TextView sundayLabel, mondayLabel, tuesdayLabel,
            wednesdayLabel, thursdayLabel, fridayLabel, saturdayLabel;
    private int translationWidth;
    private GestureDetectorCompat gestureDetectorCompat;

    private OnDateChangeListener onDateChangeListener;

    public InfiniteCalendarView(Context context) {
        this(context, null, 0);
    }

    public InfiniteCalendarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Calendar getCurrentlySelectedDate() {
        return currentlySelectedDate;
    }

    public InfiniteCalendarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater mInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.infinite_view, this);
        leftCalendarView = (CalendarWeeklyView) findViewById(R.id.leftView);
        middleCalendarView = (CalendarWeeklyView) findViewById(R.id.middleView);
        rightCalendarView = (CalendarWeeklyView) findViewById(R.id.rightView);
        leftCalendarView.setOnDateClickListener(this);
        rightCalendarView.setOnDateClickListener(this);
        middleCalendarView.setOnDateClickListener(this);
        gestureDetectorCompat = new GestureDetectorCompat(getContext(), this);

        sundayLabel = (TextView) findViewById(R.id.sunday_label);
        mondayLabel = (TextView) findViewById(R.id.monday_label);
        tuesdayLabel = (TextView) findViewById(R.id.tuesday_label);
        wednesdayLabel = (TextView) findViewById(R.id.wednesday_label);
        thursdayLabel = (TextView) findViewById(R.id.thursday_label);
        fridayLabel = (TextView) findViewById(R.id.friday_label);
        saturdayLabel = (TextView) findViewById(R.id.saturday_label);

        this.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {
                getViewTreeObserver().removeOnPreDrawListener(this);
                translationWidth = middleCalendarView.getWidth();
                leftCalendarView.setTranslationX(-translationWidth);
                rightCalendarView.setTranslationX(translationWidth);
                return false;
            }
        });
        initViews();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean b = onTouchEvent(ev);

        return b ? super.onInterceptTouchEvent(ev) : b;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);

        return this.gestureDetectorCompat.onTouchEvent(event);
    }

    private void initViews() {
        translationWidth = middleCalendarView.getWidth();
        middleCalendarView.setTranslationX(0);
        leftCalendarView.setTranslationX(-translationWidth);
        rightCalendarView.setTranslationX(translationWidth);

        calendar = (Calendar) currentlySelectedDate.clone();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        int deltaToStartOfTheWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(Calendar.DATE, -deltaToStartOfTheWeek);

        Calendar leftCalendar = (Calendar) calendar.clone();
        leftCalendar.add(Calendar.DATE, -NO_OF_DAYS_IN_A_WEEK);
        leftCalendarView.setCalendarData(leftCalendar);

        Calendar middleCalendar = (Calendar) calendar.clone();
        middleCalendarView.setCalendarData(middleCalendar);

        Calendar rightCalendar = (Calendar) calendar.clone();
        rightCalendar.add(Calendar.DATE, NO_OF_DAYS_IN_A_WEEK);
        rightCalendarView.setCalendarData(rightCalendar);
    }

    public void setCalendarWeeklyViewBackground(int color) {
        leftCalendarView.setBackgroundColor(color);
        rightCalendarView.setBackgroundColor(color);
        middleCalendarView.setBackgroundColor(color);
    }

    public void setDateTextViewBackground(int color) {
        leftCalendarView.setTextViewBackGround(color);
        rightCalendarView.setTextViewBackGround(color);
        middleCalendarView.setTextViewBackGround(color);
    }

    public void setDateTextViewColor(int color) {
        leftCalendarView.setTextColor(color);
        rightCalendarView.setTextColor(color);
        middleCalendarView.setTextColor(color);
    }

    public void setLabelTextColor(int resid) {
        sundayLabel.setTextColor(getResources().getColorStateList(resid));
        mondayLabel.setTextColor(getResources().getColorStateList(resid));
        tuesdayLabel.setTextColor(getResources().getColorStateList(resid));
        wednesdayLabel.setTextColor(getResources().getColorStateList(resid));
        thursdayLabel.setTextColor(getResources().getColorStateList(resid));
        fridayLabel.setTextColor(getResources().getColorStateList(resid));
        saturdayLabel.setTextColor(getResources().getColorStateList(resid));
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        if (isAnimating) {
            return true;
        }

        final int direction;

        if (e1.getX() > e2.getX()) {
            direction = 1;
        } else {
            direction = -1;
        }

        leftCalendarView.animate().translationXBy(direction * -translationWidth);
        middleCalendarView.animate().translationXBy(direction * -translationWidth);
        rightCalendarView.animate().translationXBy(direction * -translationWidth).setListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                isAnimating = true;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                CalendarWeeklyView viewToReOrder = null;

                if (direction == 1) {
                    if (leftCalendarView.getTranslationX() < -translationWidth) {
                        viewToReOrder = leftCalendarView;
                    } else if (middleCalendarView.getTranslationX() < -translationWidth) {
                        viewToReOrder = middleCalendarView;
                    } else if (rightCalendarView.getTranslationX() < -translationWidth) {
                        viewToReOrder = rightCalendarView;
                    }

                } else {
                    if (leftCalendarView.getTranslationX() > translationWidth) {
                        viewToReOrder = leftCalendarView;
                    } else if (middleCalendarView.getTranslationX() > translationWidth) {
                        viewToReOrder = middleCalendarView;
                    } else if (rightCalendarView.getTranslationX() > translationWidth) {
                        viewToReOrder = rightCalendarView;
                    }
                }

                calendar.add(Calendar.DATE, direction * NO_OF_DAYS_IN_A_WEEK);

                if (viewToReOrder != null) {
                    Calendar calendar = (Calendar) InfiniteCalendarView.this.calendar.clone();
                    calendar.add(Calendar.DATE, direction * NO_OF_DAYS_IN_A_WEEK);
                    viewToReOrder.setTranslationX(direction * translationWidth);
                    viewToReOrder.setCalendarData(calendar);
                }

                isAnimating = false;
                currentlySelectedDate.add(Calendar.DATE, direction * NO_OF_DAYS_IN_A_WEEK);
                onDateClick(currentlySelectedDate, true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {}
        });
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {}

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {return true;}

    @Override
    public void onShowPress(MotionEvent e) {}

    @Override
    public boolean onSingleTapUp(MotionEvent e) {return true;}

    @Override
    public void onDateClick(Calendar calendar) {
        onDateClick(calendar, false);
    }

    public void onDateClick(Calendar calendar, boolean isWeekChange) {
        leftCalendarView.setSelectedDate(calendar);
        middleCalendarView.setSelectedDate(calendar);
        rightCalendarView.setSelectedDate(calendar);
        currentlySelectedDate = calendar;

        if (onDateChangeListener != null) {
            onDateChangeListener.onDateChange(currentlySelectedDate, isWeekChange);
        }
    }

    /*public void setAvailableDate(AvailableDate availableDate) {
        leftCalendarView.setAvailableDate(availableDate);
        middleCalendarView.setAvailableDate(availableDate);
        rightCalendarView.setAvailableDate(availableDate);
    }*/

    public interface OnDateChangeListener {
        public void onDateChange(Calendar calendar, boolean isItaWeekChange);
    }

    public void setOnDateChangeListener(OnDateChangeListener dateChangeListener) {
        onDateChangeListener = dateChangeListener;
    }

    public void setCurrentlySelectedDate(Calendar calendar) {
        currentlySelectedDate = calendar;
        leftCalendarView.setSelectedDate(calendar);
        middleCalendarView.setSelectedDate(calendar);
        rightCalendarView.setSelectedDate(calendar);
        initViews();
    }
}
