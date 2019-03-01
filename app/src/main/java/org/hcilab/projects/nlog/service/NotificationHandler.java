package org.hcilab.projects.nlog.service;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.service.notification.StatusBarNotification;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.preference.PreferenceManager;

import org.hcilab.projects.nlog.misc.Const;
import org.hcilab.projects.nlog.misc.DatabaseHelper;

public class NotificationHandler {

	public static final String BROADCAST = "org.hcilab.projects.nlog.update";
	public static final String LOCK = "lock";

	private Context context;
	private SharedPreferences sp;

	NotificationHandler(Context context) {
		this.context = context;
		sp = PreferenceManager.getDefaultSharedPreferences(context);
	}

	void handlePosted(StatusBarNotification sbn) {
		if(sbn.isOngoing() && !sp.getBoolean(Const.PREF_ONGOING, false)) {
			if(Const.DEBUG) System.out.println("posted ongoing!");
			return;
		}

		// Log only notifications from KakaoTalk and exclude system notifications by id
		if (sbn.getPackageName().equals("com.kakao.talk") && (sbn.getId() == 2)) {
            boolean text = sp.getBoolean(Const.PREF_TEXT, true);
            String lastActivity = sp.getString(Const.PREF_LAST_ACTIVITY, null);
            NotificationObject no = new NotificationObject(context, sbn, text, -1, lastActivity);
            log(DatabaseHelper.PostedEntry.TABLE_NAME, DatabaseHelper.PostedEntry.COLUMN_NAME_CONTENT, no.toString());

            // Add interactive rating functionality

        }
	}

	void handleRemoved(StatusBarNotification sbn, int reason) {
		if(sbn.isOngoing() && !sp.getBoolean(Const.PREF_ONGOING, false)) {
			if(Const.DEBUG) System.out.println("removed ongoing!");
			return;
		}
		String lastActivity = sp.getString(Const.PREF_LAST_ACTIVITY, null);
		NotificationObject no = new NotificationObject(context, sbn, false, reason, lastActivity);
		log(DatabaseHelper.RemovedEntry.TABLE_NAME, DatabaseHelper.RemovedEntry.COLUMN_NAME_CONTENT, no.toString());
	}

	private void log(String tableName, String columnName, String content) {
		try {
			if(content != null) {
				synchronized (LOCK) {
					DatabaseHelper dbHelper = new DatabaseHelper(context);
					SQLiteDatabase db = dbHelper.getWritableDatabase();
					ContentValues values = new ContentValues();
					values.put(columnName, content);
					db.insert(tableName, "null", values);
					db.close();
					dbHelper.close();
				}

				Intent local = new Intent();
				local.setAction(BROADCAST);
				LocalBroadcastManager.getInstance(context).sendBroadcast(local);
			}
		} catch (Exception e) {
			if(Const.DEBUG) e.printStackTrace();
		}
	}

}
