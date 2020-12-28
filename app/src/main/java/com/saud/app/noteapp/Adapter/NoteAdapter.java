package com.saud.app.noteapp.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saud.app.noteapp.Model.note_model;
import com.saud.app.noteapp.R;
import com.saud.app.noteapp.database.MySqliteHelper;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.viewHolder>{

    List<note_model> list;
    Activity activity;
    OnNoteItemClickListener listener;
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
            holder.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    PopupMenu menu=new PopupMenu(activity,holder.more);
                    menu.getMenuInflater().inflate(R.menu.more_menu,menu.getMenu());
                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()){
                                case R.id.edit:
                                    listener.OnEdit(model);
                                    break;
                                case R.id.delete:
                                    listener.Onclick(model);
                                    break;
                            }
                            return false;
                        }
                    });
                    menu.show();
                }
            });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder{
        TextView title,description,date;
        ImageView more;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            description=itemView.findViewById(R.id.description);
            date=itemView.findViewById(R.id.date);
            more=itemView.findViewById(R.id.more);

        }
    }
    public interface OnNoteItemClickListener{
        void Onclick(note_model model);
        void OnEdit(note_model model);
    }
    public void setOnNoteItemClickListener(OnNoteItemClickListener listener){
        this.listener=listener;
    }
}
