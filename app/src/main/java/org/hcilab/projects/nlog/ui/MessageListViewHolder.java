package org.hcilab.projects.nlog.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.hcilab.projects.nlog.R;

class MessageListViewHolder extends RecyclerView.ViewHolder {

	LinearLayout item;
	public ImageView icon;
	public TextView name;
	public TextView preview;
	public TextView text;

	MessageListViewHolder(View view) {
		super(view);
		item = view.findViewById(R.id.item);
		icon = view.findViewById(R.id.icon);
		name = view.findViewById(R.id.name);
		preview = view.findViewById(R.id.preview);
		text = view.findViewById(R.id.text);
	}

}
