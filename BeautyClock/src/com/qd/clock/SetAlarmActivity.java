package com.qd.clock;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SetAlarmActivity extends Activity{
		
	private TimePicker mTimePicker;
	
	private ToggleButton  mToggleBtn_Mon,mToggleBtn_Tue,mToggleBtn_Wed,mToggleBtn_Thu,mToggleBtn_Fri,mToggleBtn_Sat,mToggleBtn_Sun;
   
	private static final int MONDAY=1;
	private static final int TUESDAY=2;
	private static final int WEDNESDAY=3;
	private static final int THURSDAY=4;
	private static final int FRIDAY=5;
	private static final int SATURDAY=6;
	private static final int SUNDAY=0;
		
	private static final String TIME_FORMAT="HH:mm:ss";
	
	private static final String TAG="alarmClock_tag";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm_setting_view);
		initView();
	}
	
	private void initView(){
	
		mTimePicker=(TimePicker) findViewById(R.id.timePicker1);

		//ToggleButton
		mToggleBtn_Mon=(ToggleButton) findViewById(R.id.toggleButton1);
		mToggleBtn_Tue=(ToggleButton) findViewById(R.id.toggleButton2);
		mToggleBtn_Wed=(ToggleButton) findViewById(R.id.toggleButton3);
		mToggleBtn_Thu=(ToggleButton) findViewById(R.id.toggleButton4);
		mToggleBtn_Fri=(ToggleButton) findViewById(R.id.toggleButton5);
		mToggleBtn_Sat=(ToggleButton) findViewById(R.id.toggleButton6);
		mToggleBtn_Sun=(ToggleButton) findViewById(R.id.toggleButton7);			

	}
	public void onAlarmConfirmClick(View v) {
		int mHour=mTimePicker.getCurrentHour();
		int mMinute=mTimePicker.getCurrentMinute();
		
		Calendar calendar=Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis()); 
		calendar.setFirstDayOfWeek(Calendar.MONDAY);	
        calendar.set(Calendar.HOUR_OF_DAY, mHour); 
        calendar.set(Calendar.MINUTE, mMinute); 
        calendar.set(Calendar.SECOND, 0); 
        calendar.set(Calendar.MILLISECOND, 0); 
        Intent intent = new Intent(this, AlarmActivity.class); 
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0); 
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE); 
              
        //===========时间设置早于当前时间：+1=========	===
        if (calendar.before(Calendar.getInstance())) {
        	calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
        while(!getWeekAlarmTime().contains(calendar.get(Calendar.DAY_OF_WEEK)-1)){
        	
        	calendar.add(Calendar.DAY_OF_MONTH, 1);			
		}
         //=========================
        String s=(String) DateFormat.format(TIME_FORMAT, calendar.getTimeInMillis());        
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        
        String tmps = "设置闹钟时间为" + format(mHour) + ":" +format(mMinute)+getTimeUntilNextAlarmMessage(calendar.getTimeInMillis());
        Toast.makeText(getApplicationContext(), tmps, Toast.LENGTH_SHORT).show();
   
	}
	
	public void onAlarmCancleClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, AlarmActivity.class);
	    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
	    AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
	    alarmManager.cancel(pendingIntent);
	}	
	//		
	private ArrayList<Integer> getWeekAlarmTime() {
	
		ArrayList<Integer> weeks=new ArrayList<Integer>();
		weeks.clear();
		if (mToggleBtn_Mon.isChecked()) {

			weeks.add(MONDAY);
		}
		if (mToggleBtn_Tue.isChecked()) {
			
			weeks.add(TUESDAY);
		}
		if (mToggleBtn_Wed.isChecked()) {
			
			weeks.add(WEDNESDAY);
		}
		if (mToggleBtn_Thu.isChecked()) {
			weeks.add(THURSDAY);
			
		}
		if (mToggleBtn_Fri.isChecked()) {
			weeks.add(FRIDAY);
			
		}
		if (mToggleBtn_Sat.isChecked()) {
			weeks.add(SATURDAY);
			
		}
		if (mToggleBtn_Sun.isChecked()) {
			weeks.add(SUNDAY);
			
		}
		//默认每天都循环
		if (weeks.size()<=0) {
			for (int i = 0; i < 7; i++) {
				weeks.add(i+1);
			}
		}
		return  weeks;
	}
	
	
	
	 private String format(int time){ 
	        String str = "" + time; 
	        if(str.length() == 1){ 
	            str = "0" + str; 
	        } 
	        return str; 
	    } 
	 
	 public String getTimeUntilNextAlarmMessage(long settingTimeMillis){
			long timeDifference =settingTimeMillis - System.currentTimeMillis();
			long days = timeDifference / (1000 * 60 * 60 * 24);
			long hours = timeDifference / (1000 * 60 * 60) - (days * 24);
			long minutes = timeDifference / (1000 * 60) - (days * 24 * 60) - (hours * 60);
			long seconds = timeDifference / (1000) - (days * 24 * 60 * 60) - (hours * 60 * 60) - (minutes * 60);
			String alert = "；闹钟将在 ";
			if (days > 0) {
				alert += String.format(
						"%d 天, %d 小时, %d 分 钟and %d 秒", days,
						hours, minutes, seconds);
			} else {
				if (hours > 0) {
					alert += String.format("%d 小时, %d 分钟 and %d 秒",
							hours, minutes, seconds);
				} else {
					if (minutes > 0) {
						alert += String.format("%d 分钟, %d 秒钟", minutes,
								seconds);
					} else {
						alert += String.format("%d 秒", seconds);
					}
				}
			}
			return alert;
		}
	 
}
