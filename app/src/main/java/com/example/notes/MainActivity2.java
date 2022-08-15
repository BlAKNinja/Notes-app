package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.io.IOException;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        EditText editText=(EditText) findViewById(R.id.editText);
        Intent intent=getIntent();
        int nodeid=intent.getIntExtra("noteid",-1);
        if(nodeid!=-1){
            editText.setText(MainActivity.notes.get(nodeid));
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    MainActivity.notes.set(nodeid,String.valueOf(charSequence));
                    MainActivity.arrayAdapter.notifyDataSetChanged();
                    SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                    try {
                        sharedPreferences.edit().putString("notes",Objects.serialize(MainActivity.notes)).apply();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }else{
            MainActivity.notes.add("");
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    MainActivity.notes.set(MainActivity.notes.size()-1,String.valueOf(charSequence));
                    MainActivity.arrayAdapter.notifyDataSetChanged();
                    SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences("com.example.notes", Context.MODE_PRIVATE);
                    try {
                        sharedPreferences.edit().putString("notes",Objects.serialize(MainActivity.notes)).apply();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

        }

    }
}