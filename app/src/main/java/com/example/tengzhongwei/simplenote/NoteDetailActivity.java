package com.example.tengzhongwei.simplenote;

import android.Manifest;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

// TODO: Please change hardcoded string to reference
public class NoteDetailActivity extends AppCompatActivity {
    EditText editNoteTextView;
    EditText dateTextView;
    EditText titleTextView;
    EditText timeTextView;
    SimpleDateFormat dateFormat;
    SimpleDateFormat timeFormat;
    Button saveButton;
    Calendar myCalendar;
    Note note;
    int index;
    Gson gson;
    CheckBox checkBox;
    PendingIntent pendingIntent;
    Intent notifyIntent;


    // Save note and add/cancel reminder
    public void onSaveButtonClick(View view){
        String content = editNoteTextView.getText().toString();
        String title = titleTextView.getText().toString();
        Date date = myCalendar.getTime();
        boolean addReminder = checkBox.isChecked();

        if(index>-1){
            note.setContent(content);
            note.setTitle(title);
            note.setDate(date);
            note.setRemind(addReminder);
            MainActivity.noteList.set(index, note);
        } else {
            note = new Note(title, content, date, addReminder);
            MainActivity.noteList.add(note);
            index = MainActivity.noteList.size()-1;
        }

        if(addReminder){

            // Below is the version using broad cast -> this app is sending a broadcast to it self, itself is a receiver
            // permission is self-defined -> when receive the broadcast successfully, onReceive method will send the intent to intent service -> alarm
            notifyIntent = new Intent(getApplicationContext(), MyReceiver.class);
            notifyIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            //TODO: SET ACTION DOES NOT WORK
            notifyIntent.setAction("com.example.tengzhongwei.simplenote.actions.notification_action");
            notifyIntent.putExtra("title",note.getTitle());
            notifyIntent.putExtra("content", note.getContent());
            //sendBroadcast(notifyIntent, "android.myPermission.broadcast");

            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), index+100, notifyIntent, 0 );
//
//            try {
//                pendingIntent.send(this, 0, notifyIntent, null, null, "android.myPermission.broadcast");
//            } catch (PendingIntent.CanceledException e) {
//                e.printStackTrace();
//            }

            AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
            Calendar c = Calendar.getInstance();
            alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
//            alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), "send_notify", new AlarmManager.OnAlarmListener() {
//                @Override
//                public void onAlarm() {
//                    try{
//                        pendingIntent.send(getApplicationContext(), 0, notifyIntent, null, null, "android.myPermission.broadcast");
//                    } catch (PendingIntent.CanceledException e) {
//
//                    }
//                }
//            }, null);


              // Below is the version not using Broadcast but using pending intent -> intent service -> alarm
//            Intent myIntent =  new Intent(NoteDetailActivity.this, MyNewIntentService.class);
//            myIntent.putExtra("title",note.getTitle());
//            myIntent.putExtra("content", note.getContent());

              // REQUEST C0DE SHOULD BE UNIQUE FOR EACH EVENT
//            PendingIntent pendingIntent = PendingIntent.getService(NoteDetailActivity.this, index+100, notifyIntent, 0);
//            AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

            //For test, get current time
            //Calendar c = Calendar.getInstance();

//            alarmManager.set(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(), pendingIntent);
        } else {
            // Cancel
            Intent myIntent =  new Intent(NoteDetailActivity.this, MyNewIntentService.class);
            PendingIntent pendingIntent = PendingIntent.getService(NoteDetailActivity.this, index+100, myIntent, 0);
            AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
        }

        // TODO... need to add Private Mode to allow only this app store data on this device (internal storage)
        // TODO... File creation mode: the default mode, where the created file can only be accessed by the calling application (or all applications sharing the same user ID).
        //SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.tengzhongwei.simplenote", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.tengzhongwei.simplenote", Context.MODE_MULTI_PROCESS);
        sharedPreferences.edit().putString("noteList", gson.toJson(MainActivity.noteList)).apply();
        MainActivity.noteAdapter.notifyDataSetChanged();
        finish();
    }

    //update date and time textview
    private void updateLabel() {
        if(myCalendar != null){
            dateTextView.setText(dateFormat.format(myCalendar.getTime()));
            timeTextView.setText(timeFormat.format(myCalendar.getTime()));
        }
    }

    // Initialize calendar object and set up date and time diaglog
    private void startCalendar(){
        myCalendar = Calendar.getInstance();
        dateTextView= findViewById(R.id.dateTextView);
        timeTextView = findViewById(R.id.timeTextView);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };


        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int min) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hour);
                myCalendar.set(Calendar.MINUTE, min);
                updateLabel();
            }
        };


        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(NoteDetailActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        timeTextView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new TimePickerDialog(NoteDetailActivity.this,
                                time,
                                myCalendar.get(Calendar.HOUR_OF_DAY),
                                myCalendar.get(Calendar.MINUTE),
                                true)
                                .show();
                    }
                }
                );
    }

    // Retrieve saved note if it's not newly created
    private void getTitleContent(){
        Intent intent = getIntent();
        index = intent.getIntExtra("index", -1);

        if (index>-1){
            note = MainActivity.noteList.get(index);
            titleTextView.setText(note.getTitle());
            editNoteTextView.setText(note.getContent());
            dateTextView.setText(dateFormat.format(note.getDate()));
            timeTextView.setText(timeFormat.format(note.getDate()));
            checkBox.setChecked(note.getRemind());
        }
    }

    //Make Toast when reminder is clicked
    protected void  setReminder(){

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked()) {
                    //add reminder to this note based on time user selected
                    Toast.makeText(getApplicationContext(), "Click save to add a reminder", Toast.LENGTH_SHORT).show();
                }else{
                    //check if a reminder was added, delete it, otherwise do nothing
                    Toast.makeText(getApplicationContext(), "Reminder removed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize variables
        setContentView(R.layout.activity_note_detail);
        saveButton = findViewById(R.id.saveButton);
        editNoteTextView = findViewById(R.id.editNoteTextView);
        titleTextView = findViewById(R.id.titleTextView);
        dateTextView = findViewById(R.id.dateTextView);
        timeTextView = findViewById(R.id.timeTextView);
        dateFormat = new SimpleDateFormat("MM/dd/yy", Locale.US);
        timeFormat = new SimpleDateFormat("HH:mm", Locale.US);
        gson = new Gson();
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        dateTextView.setText(dateFormat.format(Calendar.getInstance().getTime()));
        timeTextView.setText(timeFormat.format(Calendar.getInstance().getTime()));


        getTitleContent();
        startCalendar();
        setReminder();

    }
}
