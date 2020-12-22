package com.saud.app.noteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.saud.app.noteapp.Adapter.NoteAdapter;
import com.saud.app.noteapp.database.MySqliteHelper;
import com.saud.app.noteapp.databinding.ActivityAllNotesBinding;

public class AllNotesActivity extends AppCompatActivity {
    private NoteAdapter adapter;
    ActivityAllNotesBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityAllNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        MySqliteHelper helper=new MySqliteHelper(this);
        adapter=new NoteAdapter(this,helper.getAllNotes());
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
    }
}