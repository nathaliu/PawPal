package com.example.pawpal;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "pawpal.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE utilisateurs (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT," +
                    "petName TEXT," +
                    "species TEXT," +
                    "race TEXT," +
                    "gender TEXT," +
                    "age INTEGER," +
                    "size TEXT," +
                    "description TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS utilisateurs";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void addUtilisateur(Utilisateur utilisateur) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("username", utilisateur.getUsername());
        values.put("petName", utilisateur.getPetName());
        values.put("species", utilisateur.getSpecies());
        values.put("race", utilisateur.getRace());
        values.put("gender", utilisateur.getGender());
        values.put("age", utilisateur.getAge());
        values.put("size", utilisateur.getSize());
        values.put("description", utilisateur.getDescription());
        db.insert("utilisateurs", null, values);
        db.close();
    }

    @SuppressLint("Range")
    public List<Utilisateur> getAllUtilisateurs() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM utilisateurs", null);
        if (cursor.moveToFirst()) {
            do {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setId(cursor.getLong(cursor.getColumnIndex("id")));
                utilisateur.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                utilisateur.setPetName(cursor.getString(cursor.getColumnIndex("petName")));
                utilisateur.setSpecies(cursor.getString(cursor.getColumnIndex("species")));
                utilisateur.setRace(cursor.getString(cursor.getColumnIndex("race")));
                utilisateur.setGender(cursor.getString(cursor.getColumnIndex("gender")));
                utilisateur.setAge(cursor.getInt(cursor.getColumnIndex("age")));
                utilisateur.setSize(cursor.getString(cursor.getColumnIndex("size")));
                utilisateur.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                utilisateurs.add(utilisateur);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return utilisateurs;
    }

    // Autres méthodes pour la mise à jour, la suppression, etc.
}
