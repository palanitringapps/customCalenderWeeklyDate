package com.customcalenderweeklydate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private CustomCalendarWeeklyView mCalenderView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCalenderView =(CustomCalendarWeeklyView) findViewById(R.id.calenderView);
        Calendar calendar = (Calendar) Calendar.getInstance().clone();
        mCalenderView.setCurrentDate(calendar);
    }
}
