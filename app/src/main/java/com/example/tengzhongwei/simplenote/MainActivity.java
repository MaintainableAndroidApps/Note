package com.example.tengzhongwei.simplenote;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


// TODO: Please find the permission misuse and correct it.
// TODO: Please change hardcoded string to reference
public class MainActivity extends AppCompatActivity {
    ListView noteListView;
    static ArrayList<Note> noteList;
    static NoteAdapter noteAdapter;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private SimpleDateFormat sdf;


    public void newMemo(View view){
        Intent intent = new Intent(this, NoteDetailActivity.class);
        startActivity(intent);
    }

    private void retrieveStorage(){
        String jsonNoteList = sharedPreferences.getString("noteList","");

        Type collectionType = new TypeToken<ArrayList<Note>>(){}.getType();
        noteList = gson.fromJson(jsonNoteList, collectionType);
        if(noteList == null){
            noteList = new ArrayList<Note>();
            noteList.add(new Note("Example Note", "This is an example note.", Calendar.getInstance().getTime(), false));
        }

    }

    private void setListView(){
        noteAdapter = new NoteAdapter(this, noteList);

        noteListView.setAdapter(noteAdapter);

        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, NoteDetailActivity.class);
                intent.putExtra("index", i);
                startActivity(intent);
            }
        });

        noteListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int position = i;

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete")
                        .setMessage("Do you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                noteList.remove(position);
                                String jsonNoteList = gson.toJson(noteList);
                                sharedPreferences.edit().putString("noteList", jsonNoteList).apply();
                                noteAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                ;
                return true;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noteListView = findViewById(R.id.noteListView);
        sharedPreferences = this.getSharedPreferences("com.example.tengzhongwei.simplenote", Context.MODE_PRIVATE);
        //sharedPreferences = this.getSharedPreferences("com.example.tengzhongwei.simplenote", Context.MODE_WORLD_WRITEABLE);
        gson = new Gson();

        retrieveStorage();
        setListView();
    }
}
