package org.hse.android;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.hse.basetimetable.R;

public class ViewHolder extends RecyclerView.ViewHolder {
    private final Context context;
    private final TextView start;
    private final TextView end;
    private final TextView type;
    private final TextView name;
    private final TextView place;
    private final TextView teacher;

    public ViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        start = itemView.findViewById(R.id.start);
        end = itemView.findViewById(R.id.finish);
        type = itemView.findViewById(R.id.type);
        name = itemView.findViewById(R.id.lesson_name);
        place = itemView.findViewById(R.id.place);
        teacher = itemView.findViewById(R.id.lesson_teacher);
    }

    public void bind(final ScheduleItem data) {
        start.setText(data.getStart());
        end.setText(data.getEnd());
        type.setText(data.getType());
        name.setText(data.getName());
        place.setText(data.getPlace());
        teacher.setText(data.getTeacher());
    }
}
