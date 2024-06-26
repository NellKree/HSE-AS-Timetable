package org.hse.android;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.hse.basetimetable.R;

public class ViewHolderHeader extends RecyclerView.ViewHolder {
    private final Context context;
    private final TextView title;
    public ViewHolderHeader(View itemView, Context context) {
        super(itemView);
        this.context=context;
        title=itemView.findViewById(R.id.title);
    }
    public void bind(String date) {
        title.setText(date);
    }
}
