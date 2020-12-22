package com.saud.app.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.saud.app.noteapp.Model.note_model;
import com.saud.app.noteapp.database.MySqliteHelper;
import com.saud.app.noteapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        MySqliteHelper helper=new MySqliteHelper(this);
        binding.btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,AllNotesActivity.class);
                startActivity(intent);
            }
        });
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                note_model model=new note_model();
                model.setTitle(binding.title.getText().toString());
                model.setDescription(binding.description.getText().toString());
                model.setDate(binding.date.getText().toString());

                if (model.getTitle().isEmpty()){
                    binding.title.setError("Enter title");
                    return;
                }
                if (model.getDescription().isEmpty()){
                    binding.description.setError("Enter description");
                    return;
                }
                if (model.getDate().isEmpty()){
                    binding.date.setError("Enter date");
                    return;
                }
                boolean r= helper.saveNote(model);
                if (r){
                    Toast.makeText(MainActivity.this, "note is saved", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "some thing want wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}