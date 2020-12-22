package com.saud.app.noteapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.saud.app.noteapp.Model.note_model;

import java.util.ArrayList;
import java.util.List;


public class MySqliteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="note.db";
    private static final int VERSION=1;
    public MySqliteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String table1="create table "+TableSchema.note.TABLE_NAME+"("+TableSchema.note.ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+TableSchema.note.TITLE+" TEXT, "+TableSchema.note.DESCRIPTION+" TEXT, "+TableSchema.note.DATE+" TEXT );";
        sqLiteDatabase.execSQL(table1);
    }

    public boolean saveNote(note_model model){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(TableSchema.note.TITLE,model.getTitle());
        cv.put(TableSchema.note.DESCRIPTION,model.getDescription());
        cv.put(TableSchema.note.DATE,model.getDate());
        long id= database.insert(TableSchema.note.TABLE_NAME,null,cv);
        if (id==-1){
            return false;
        }
        return true;
    }
    public List<note_model> getAllNotes(){
        SQLiteDatabase database= this.getReadableDatabase();
        String[] cols={TableSchema.note.ID,TableSchema.note.TITLE,TableSchema.note.DESCRIPTION,TableSchema.note.DATE};
        Cursor cursor=database.query(TableSchema.note.TABLE_NAME,cols,null,null,null,null,TableSchema.note.ID+" DESC");
        ArrayList<note_model> list=new ArrayList<>();
        while (cursor.moveToNext()){
            note_model model=new note_model();
            model.setId(cursor.getInt(cursor.getColumnIndex(TableSchema.note.ID)));
            model.setTitle(cursor.getString(cursor.getColumnIndex(TableSchema.note.TITLE)));
            model.setDescription(cursor.getString(cursor.getColumnIndex(TableSchema.note.DESCRIPTION)));
            model.setDate(cursor.getString(cursor.getColumnIndex(TableSchema.note.DATE)));
            list.add(model);
        }
        cursor.close();
        database.close();
        return list;
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i<i1){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TableSchema.note.TABLE_NAME);
        }

    }
}
