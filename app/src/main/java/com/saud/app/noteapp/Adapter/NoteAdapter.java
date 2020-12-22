package com.saud.app.noteapp.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saud.app.noteapp.Model.note_model;
import com.saud.app.noteapp.R;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.viewHolder>{

    List<note_model> list;
    Activity activity;

    public NoteAdapter(Activity activity,List<note_model> list) {
        this.list=list;
        this.activity=activity;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.row_note,null,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        note_model model=list.get(position);
            holder.title.setText(model.getTitle());
            holder.description.setText(model.getDescription());
            holder.date.setText(model.getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder{
        TextView title,description,date;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            description=itemView.findViewById(R.id.description);
            date=itemView.findViewById(R.id.date);
        }
    }
}
