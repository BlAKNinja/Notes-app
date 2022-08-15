package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> notes;
    static ArrayAdapter<String> arrayAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.add:
                Intent intent=new Intent(getApplicationContext(),MainActivity2.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences=this.getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
        notes=new ArrayList<String>();
        ListView listView=(ListView) findViewById(R.id.lsitView);
        try {
            notes = (ArrayList<String>) Objects.deserialize(sharedPreferences.getString("notes",Objects.serialize(new ArrayList<String>())));
            }catch (Exception e){
            e.printStackTrace();
        }
        if(notes.size()==0){
            notes.add("Example Note");
        }

        arrayAdapter=new ArrayAdapter<String >(this,android.R.layout.simple_dropdown_item_1line,notes);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(),MainActivity2.class);
                intent.putExtra("noteid",i);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are You Sure")
                        .setMessage("This will delete this note")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int j) {
                                notes.remove(i);
                                arrayAdapter.notifyDataSetChanged();
                                SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                                try {
                                    sharedPreferences.edit().putString("notes",Objects.serialize(MainActivity.notes)).apply();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton("NO",null).show();

                return true;
            }
        });


    }
}