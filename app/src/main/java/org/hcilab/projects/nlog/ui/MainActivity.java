package org.hcilab.projects.nlog.ui;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.hcilab.projects.nlog.R;
import org.hcilab.projects.nlog.misc.Const;
import org.hcilab.projects.nlog.misc.DatabaseHelper;
import org.hcilab.projects.nlog.misc.ExportTask;
import org.hcilab.projects.nlog.service.NotificationHandler;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private RecyclerView recyclerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        context = this;

		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
		recyclerView = findViewById(R.id.message_list);
		recyclerView.setLayoutManager(layoutManager);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_settings:
				settings();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void settings() {
	    Intent intent = new Intent(context, SettingsActivity.class);
	    startActivity(intent);
    }

    private void update() {
	    MessageListAdapter adapter = new MessageListAdapter(this);
	    recyclerView.setAdapter(adapter);

	    if (adapter.getItemCount() == 0) {
	        Toast.makeText(context, R.string.empty_log_file, Toast.LENGTH_LONG).show();
        }
    }
}