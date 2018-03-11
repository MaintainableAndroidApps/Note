package com.example.tengzhongwei.simplenote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by tengzhongwei on 3/10/18.
 */

public class NoteAdapter extends ArrayAdapter<Note> {
    public NoteAdapter(Context context, ArrayList<Note> notes) {
        super(context, 0, notes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Note note = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_layout, parent, false);
        }
        // Lookup view for data population
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        TextView noteTitle = convertView.findViewById(R.id.noteTitle);
        TextView noteContent = convertView.findViewById(R.id.noteContent);
        TextView noteDate = convertView.findViewById(R.id.noteDate);

        // Populate the data into the template view using the data object
        noteTitle.setText(note.getTitle());
        noteContent.setText(note.getContent());
        noteDate.setText(sdf.format(note.getDate()));
        // Return the completed view to render on screen
        return convertView;
    }
}
