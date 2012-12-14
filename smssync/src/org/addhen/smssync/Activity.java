package org.addhen.smssync;

import org.addhen.smssync.fragments.PendingMessages;

public class Activity extends PendingMessages implements Runnable{
	
	/**
	 * Delete all messages. 0 - Successfully deleted. 1 - There is nothing to be
	 * deleted.
	 */

	@Override
	public void run() {
		// TODO Auto-generated method stub
		getActivity().setProgressBarIndeterminateVisibility(true);
		boolean result = false;

		int deleted = 0;

		if (adapter.getCount() == 0) {
			deleted = 1;
		} else {
			result = model.deleteAllMessages();
		}

		try {
			if (deleted == 1) {
				toastLong(R.string.no_messages_to_delete);
			} else {
				if (result) {

					toastLong(R.string.messages_deleted);
					showMessages();
				} else {
					toastLong(R.string.messages_deleted_failed);
				}
			}
			getActivity().setProgressBarIndeterminateVisibility(false);
		} catch (Exception e) {
			return;
		}
	}
	
	

}
