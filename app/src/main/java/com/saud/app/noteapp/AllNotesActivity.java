package com.saud.app.noteapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.saud.app.noteapp.Adapter.NoteAdapter;
import com.saud.app.noteapp.Model.note_model;
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
        fetchNotes();
    }

    private void fetchNotes() {
        MySqliteHelper helper=new MySqliteHelper(this);
        adapter=new NoteAdapter(this,helper.getAllNotes());
        adapter.setOnNoteItemClickListener(new NoteAdapter.OnNoteItemClickListener() {
            @Override
            public void Onclick(note_model model) {
                AlertDialog.Builder builder=new AlertDialog.Builder(AllNotesActivity.this);
                builder.setTitle("Important");
                builder.setMessage("Are your sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MySqliteHelper helper=new MySqliteHelper(AllNotesActivity.this);
                        boolean check=helper.delNoteById(String.valueOf(model.getId()));
                        if (check){
                            fetchNotes();
                            Toast.makeText(AllNotesActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(AllNotesActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                        }
                        dialogInterface.dismiss();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();

            }

            @Override
            public void OnEdit(note_model model) {
                AlertDialog.Builder builder=new AlertDialog.Builder(AllNotesActivity.this);
                View view= LayoutInflater.from(AllNotesActivity.this).inflate(R.layout.edit_note,null,false);
                EditText title=view.findViewById(R.id.title);
                EditText des=view.findViewById(R.id.description);
                EditText date=view.findViewById(R.id.date);
                Button btnUpdate=view.findViewById(R.id.btnUpdate);
                title.setText(model.getTitle());
                des.setText(model.getDescription());
                date.setText(model.getDate());
                builder.setView(view);
                AlertDialog alertDialog=builder.create();
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        note_model model1=new note_model();
                        model1.setId(model.getId());
                        model1.setTitle(title.getText().toString());
                        model1.setDescription(des.getText().toString());
                        model1.setDate(date.getText().toString());
                        updateNote(model1);
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
    }

    private void updateNote(note_model model) {
        MySqliteHelper helper=new MySqliteHelper(AllNotesActivity.this);
        boolean check=helper.updateNoteById(model);
        if (check){
            fetchNotes();
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

}