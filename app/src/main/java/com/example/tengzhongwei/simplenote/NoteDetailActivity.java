package com.example.tengzhongwei.simplenote;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

public class NoteDetailActivity extends AppCompatActivity {
    EditText editNoteTextView;
    EditText dateTextView;
    EditText titleTextView;
    SimpleDateFormat sdf;
    Button saveButton;
    Calendar myCalendar;
    Note note;
    int index;
    Gson gson;

    public void onSaveButtonClick(View view){
        String content = editNoteTextView.getText().toString();
        String title = titleTextView.getText().toString();
        Date date = myCalendar.getTime();

        if(index>-1){
            note.setContent(content);
            note.setTitle(title);
            note.setDate(date);
            MainActivity.noteList.set(index, note);
        } else {
            note = new Note(title,content,date);
            MainActivity.noteList.add(note);
        }
        // TODO... need to add Private Mode to allow only this app store data on this device (internal storage)
        // TODO... File creation mode: the default mode, where the created file can only be accessed by the calling application (or all applications sharing the same user ID).
        //SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.tengzhongwei.simplenote", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.tengzhongwei.simplenote", Context.MODE_MULTI_PROCESS);
        sharedPreferences.edit().putString("noteList", gson.toJson(MainActivity.noteList)).apply();
        MainActivity.noteAdapter.notifyDataSetChanged();
        finish();
    }

    private void updateLabel() {
        if(myCalendar != null){
            dateTextView.setText(sdf.format(myCalendar.getTime()));
        } else {
            dateTextView.setText(sdf.format(Calendar.getInstance().getTime()));
        }

    }

    private void startCalendar(){
        myCalendar = Calendar.getInstance();
        dateTextView= findViewById(R.id.dateTextView);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(NoteDetailActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void getTitleContent(){
        Intent intent = getIntent();
        index = intent.getIntExtra("index", -1);

        if (index>-1){
            note = MainActivity.noteList.get(index);
            titleTextView.setText(note.getTitle());
            editNoteTextView.setText(note.getContent());
            dateTextView.setText(sdf.format(note.getDate()));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        saveButton = findViewById(R.id.saveButton);
        editNoteTextView = findViewById(R.id.editNoteTextView);
        titleTextView = findViewById(R.id.titleTextView);
        String myFormat = "MM/dd/yy"; //In which you need put here
        sdf = new SimpleDateFormat(myFormat, Locale.US);
        gson = new Gson();

        getTitleContent();


        startCalendar();

        updateLabel();



    }
}
