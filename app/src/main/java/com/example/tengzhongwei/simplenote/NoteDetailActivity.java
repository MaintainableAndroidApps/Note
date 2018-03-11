package com.example.tengzhongwei.simplenote;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
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
import android.widget.Toast;

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
    CheckBox checkBox;

    public void onSaveButtonClick(View view){
        String content = editNoteTextView.getText().toString();
        String title = titleTextView.getText().toString();
        Date date = myCalendar.getTime();
        boolean addReminder = checkBox.isChecked();

        if(index>-1){
            note.setContent(content);
            note.setTitle(title);
            note.setDate(date);
            MainActivity.noteList.set(index, note);
        } else {
            note = new Note(title,content,date);
            MainActivity.noteList.add(note);
        }

        if(addReminder){
            // add a event to android calendar
            // myCalendar = Calendar.getInstance();
//            Intent intent = new Intent(Intent.ACTION_EDIT);
//            intent.setType("vnd.android.cursor.item/event");
//            intent.putExtra("beginTime", myCalendar.getTimeInMillis());
//            intent.putExtra("allDay", true);
//            intent.putExtra("rrule", "FREQ=YEARLY");
//            intent.putExtra("endTime", myCalendar.getTimeInMillis()+60*60*1000);
//            intent.putExtra("title", note.getTitle());
//            startActivity(intent);
            Intent notifyIntent = new Intent(this,MyReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast
                    (this, 1, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
//            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis(),
//                    1000 * 60 * 60 * 24, pendingIntent);
            Calendar c = Calendar.getInstance();
            alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis()+3000, pendingIntent);




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

    protected void   setReminder(){

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked()) {
                    //add reminder to this note based on time user selected
                    Toast.makeText(getApplicationContext(), "Click save to add a reminder", Toast.LENGTH_SHORT).show();
                    Log.i("checkbox", String.valueOf(checkBox.isChecked()));

//                    Intent notifyIntent = new Intent(getApplicationContext(),MyReceiver.class);
//                    PendingIntent pendingIntent = PendingIntent.getBroadcast
//                            (NoteDetailActivity.this, 1, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    Intent myIntent =  new Intent(NoteDetailActivity.this, MyNewIntentService.class);
                    myIntent.putExtra("title",note.getTitle());
                    myIntent.putExtra("content", note.getContent());

                    PendingIntent pendingIntent = PendingIntent.getService(NoteDetailActivity.this, 0, myIntent, 0);

                    AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                    Calendar c = Calendar.getInstance();
                    alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis()+5000, pendingIntent);

                }else{
                    //check if a reminder was added, delete it, otherwise do nothing
                    Toast.makeText(getApplicationContext(), "Reminder removed", Toast.LENGTH_SHORT).show();
                    Log.i("checkbox", String.valueOf(checkBox.isChecked()));

//                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 2, intent, 0);
//                    Notification notification = new Notification.Builder(getApplicationContext())
//                            .setContentTitle("Hello")
//                            .setContentText("It succeed!")
//                            .setSmallIcon(android.R.drawable.ic_btn_speak_now)
//                            .setContentIntent(pendingIntent)
//                            .build();
//                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                    synchronized (notificationManager){
//                        notificationManager.notify(2, notification);
//                    }

                }
            }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        saveButton = findViewById(R.id.saveButton);
        editNoteTextView = findViewById(R.id.editNoteTextView);
        titleTextView = findViewById(R.id.titleTextView);
        dateTextView = findViewById(R.id.dateTextView);
        String myFormat = "MM/dd/yy"; //In which you need put here
        sdf = new SimpleDateFormat(myFormat, Locale.US);
        gson = new Gson();
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        dateTextView.setText(sdf.format(Calendar.getInstance().getTime()));
        getTitleContent();
        startCalendar();
        setReminder();

    }
}
