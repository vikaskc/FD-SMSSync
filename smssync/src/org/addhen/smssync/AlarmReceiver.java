package org.addhen.smssync;



import org.addhen.smssync.models.MessagesModel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class AlarmReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Toast.makeText(context, "Import Messages Invoke ",Toast.LENGTH_LONG).show();
	//	context.startService(new Intent(context,ImportMessagesTask.class));
		
		 MessagesModel.deleteAllMessages();
		
		new ProcessSms(context).importMessages();
		
		
	}

}
