package org.addhen.smssync;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TimePicker;

public class FixAlarm extends Activity {
	TimePicker myTimePicker;
	 Button buttonstartSetDialog;
	 Button buttonCancelAlarm;
	 Button buttonStopAlarm;
	 TextView textAlarmPrompt;
	 
	 TimePicker timePicker;
	 CheckBox optRepeat;
	 
	 final static int RQS_1 = 1;
//	 private static final int NOTIFICATION_ID=1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fixalarm);
        
        timePicker = (TimePicker)findViewById(R.id.picker);
        optRepeat = (CheckBox)findViewById(R.id.optrepeat);
        textAlarmPrompt = (TextView)findViewById(R.id.alarmprompt);
        
     // set sharedpreferences
        SharedPreferences settings= getSharedPreferences("MyPrefs",0);
        
       textAlarmPrompt.setText(settings.getString("txt", ""));
       optRepeat.setChecked(settings.getBoolean("check", true));
     // timePicker.setCurrentHour(settings.getLong("pick",0));
        
        buttonstartSetDialog = (Button)findViewById(R.id.startSetDialog);
        buttonstartSetDialog.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar calNow = Calendar.getInstance();
			    Calendar calSet = (Calendar) calNow.clone();

			    calSet.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
			    calSet.set(Calendar.MINUTE, timePicker.getCurrentMinute());
			    calSet.set(Calendar.SECOND, 0);
			    calSet.set(Calendar.MILLISECOND, 0);
			//    initNotification();
			    
			    if(calSet.compareTo(calNow) <= 0){
			     //Today Set time passed, count to tomorrow
			     calSet.add(Calendar.DATE, 1);
			    }
			    
			    setAlarm(calSet, optRepeat.isChecked());
			}
	/**		
			private void initNotification() {
				// TODO Auto-generated method stub
				String ns = Context.NOTIFICATION_SERVICE;
				NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
				int icon = R.drawable.alarm1;
				CharSequence tickerText = "Alarm";
				long when = System.currentTimeMillis();

				Notification notification = new Notification(icon, tickerText, when);
				notification.flags= Notification.FLAG_ONGOING_EVENT;	
				Context context= getApplicationContext();
				CharSequence contentTitle="Alarm Clock";
				CharSequence contentText="This is Alarm notification";
				Intent notificationIntent= new Intent(RepeatAlarm.this,RepeatAlarm.class);
				PendingIntent contentIntent=PendingIntent.getActivity(context, 0, notificationIntent,0);
				notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
				mNotificationManager.notify(NOTIFICATION_ID, notification);
			}
			*/
		});

  

        buttonCancelAlarm = (Button)findViewById(R.id.cancel);
        buttonCancelAlarm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cancelAlarm();
		//		cancelNotification();
			}
	/**		private void cancelNotification() {
				// TODO Auto-generated method stub
				String ns = Context.NOTIFICATION_SERVICE;
				NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
				mNotificationManager.cancel(NOTIFICATION_ID);
			}    
			*/
		});
      
        buttonStopAlarm = (Button)findViewById(R.id.stop);
        buttonStopAlarm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
		//	stopService(new Intent(getBaseContext(),MediaPlayerService.class));
				
			}
		});
        

   
        
    }

 @SuppressLint("NewApi")
private void setAlarm(Calendar targetCal, boolean repeat){
  
  Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
  PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
  AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
  alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);

  if(repeat){
   alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 
     targetCal.getTimeInMillis(), 
     TimeUnit.HOURS.toMillis(1),
     pendingIntent);
   
   textAlarmPrompt.setText(
     "\n\n***\n"
     + "Alarm is set@ " + targetCal.getTime() + "\n"
     + "Repeat after every 1 Hour\n"
     + "***\n");
  }else{
   alarmManager.set(AlarmManager.RTC_WAKEUP, 
     targetCal.getTimeInMillis(),  
     pendingIntent);
   
   textAlarmPrompt.setText(
     "\n\n***\n"
     + "Alarm is set@ " + targetCal.getTime() + "\n"
     + "One shot\n"
     + "***\n");
  }

 }
 
 private void cancelAlarm(){

  textAlarmPrompt.setText(
    "\n\n***\n"
    + "Alarm Cancelled! \n"
    + "***\n");
  
  Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
  PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
  AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
  alarmManager.cancel(pendingIntent);

 }
 @Override
 protected void onStop(){
	 super.onStop();
	 SharedPreferences settings= getSharedPreferences("MyPrefs",0);
	 SharedPreferences.Editor editor= settings.edit();
	 editor.putString("txt",textAlarmPrompt.getText().toString());
	 editor.putBoolean("check", optRepeat.isChecked());
	 editor.commit();
	 }
 

    
}
