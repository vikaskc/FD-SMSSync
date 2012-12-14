package org.addhen.smssync;





import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Toast.makeText(context, "Import Messages Invoke ",Toast.LENGTH_LONG).show();
			
		context.startService(new Intent(context,Activity.class));
		
	//	Intent intent = new Intent(AlarmReceiver.this,Activity.class);
	
	
		
		// Import all messages 
		new ProcessSms(context).importMessages();
	}


		
	}


