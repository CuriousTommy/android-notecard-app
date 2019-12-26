package com.example.homework3.androidController;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


import com.example.homework3.javaModel.CardSetDataModel;
import com.example.homework3.javaModel.NoteCardDataModel;
import com.example.homework3.javaModel.generic.GenericDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SQLManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cardDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table CardSet
    private static final String TABLE_NAME_CARDSET = "CardSet";
    private static final String ATTRIBUTE_ID = "id";
    private static final String ATTRIBUTE_SUBJECT = "Subject";
    private static final String ATTRIBUTE_DESCRIPTION = "Description";

    // Table Card
    private static final String TABLE_NAME_CARD = "Card";
//    private static final String ATTRIBUTE_ID;
//    private static final String ATTRIBUTE_SUBJECT;
//    private static final String ATTRIBUTE_DESCRIPTION;
    private static final String ATTRIBUTE_CARDSET_ID = "CardSet_ID";


    public SQLManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder create_table_card_set = new StringBuilder();
        create_table_card_set.append("CREATE TABLE " + TABLE_NAME_CARDSET + "(");
        create_table_card_set.append(ATTRIBUTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ");
        create_table_card_set.append(ATTRIBUTE_SUBJECT + " VARCHAR(150), ");
        create_table_card_set.append(ATTRIBUTE_DESCRIPTION + " VARCHAR(500) ");
        create_table_card_set.append(");");

        StringBuilder create_table_card = new StringBuilder();
        create_table_card.append("CREATE TABLE " + TABLE_NAME_CARD + "(");
        create_table_card.append(ATTRIBUTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ");
        create_table_card.append(ATTRIBUTE_CARDSET_ID + " REFERENCES " + TABLE_NAME_CARDSET + "(" + ATTRIBUTE_ID + "), ");
        create_table_card.append(ATTRIBUTE_SUBJECT + " VARCHAR(150), ");
        create_table_card.append(ATTRIBUTE_DESCRIPTION + " VARCHAR(500) ");
        create_table_card.append(");");

        db.execSQL(create_table_card_set.toString());
        db.execSQL(create_table_card.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME_CARDSET));
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME_CARD));
        onCreate(db);
    }

    //
    // CardSet Functions
    //

    public long insertCardSet(CardSetDataModel glm) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(ATTRIBUTE_SUBJECT, glm.getSubject());
        value.put(ATTRIBUTE_DESCRIPTION, glm.getContent());

        long result = db.insert(TABLE_NAME_CARDSET, null, value);
        db.close();

        return result;
    }

    public void updateCardSet(CardSetDataModel glm) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ATTRIBUTE_SUBJECT, glm.getSubject());
        values.put(ATTRIBUTE_DESCRIPTION, glm.getContent());

        // https://developer.android.com/reference/android/databSQLMaase/sqlite/SQLiteDatabase.html#update(java.lang.String,%20android.content.ContentValues,%20java.lang.String,%20java.lang.String[])
        String whereClause = String.format(Locale.US,"%s=%d", ATTRIBUTE_ID, glm.getId());
        db.update(TABLE_NAME_CARDSET, values, whereClause, null);

        db.close();
    }

    public void deleteCardSet(CardSetDataModel glm) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_CARD, String.format(Locale.US,"%s=%d", ATTRIBUTE_CARDSET_ID, glm.getId()), null);
        db.delete(TABLE_NAME_CARDSET, String.format(Locale.US, "%s=%d", ATTRIBUTE_ID, glm.getId()), null);
        db.close();
    }

    //
    //  NodeCard Function
    //

    public long insertNodeCard(NoteCardDataModel ncd) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(ATTRIBUTE_CARDSET_ID, ncd.getParentID());
        value.put(ATTRIBUTE_SUBJECT, ncd.getSubject());
        value.put(ATTRIBUTE_DESCRIPTION, ncd.getContent());

        long result = db.insert(TABLE_NAME_CARD, null, value);
        db.close();

        return result;
    }

    public void updateNodeCard(NoteCardDataModel ncd) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ATTRIBUTE_SUBJECT, ncd.getSubject());
        values.put(ATTRIBUTE_DESCRIPTION, ncd.getContent());

        String whereClause = String.format(Locale.US, "%s=%d", ATTRIBUTE_ID, ncd.getId());
        db.update(TABLE_NAME_CARD, values, whereClause, null);

        db.close();
    }

    public void deleteNoteCard(NoteCardDataModel ncd) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_CARD, String.format(Locale.US, "%s=%d", ATTRIBUTE_ID, ncd.getId()), null);
        db.close();
    }

    //
    // Get List of Data
    //

    public ArrayList<CardSetDataModel> getListOfCardSets() {
        ArrayList<CardSetDataModel> generic_list_model = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * ");
        sb.append("FROM "+ TABLE_NAME_CARDSET + " ");
        sb.append("ORDER BY " + ATTRIBUTE_SUBJECT);
//        sb.append("ORDER BY " + ATTRIBUTE_SUBJECT + " DESC");

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sb.toString(), null);

        final int ID = cursor.getColumnIndex(ATTRIBUTE_ID);
        final int SUBJECT = cursor.getColumnIndex(ATTRIBUTE_SUBJECT);
        final int DESCRIPTION = cursor.getColumnIndex(ATTRIBUTE_DESCRIPTION);
        if (cursor.moveToFirst()) {
            do {
                CardSetDataModel cardset_dm = new CardSetDataModel();
                cardset_dm.setId(cursor.getInt(ID));
                cardset_dm.setSubject(cursor.getString(SUBJECT));
                cardset_dm.setContent(cursor.getString(DESCRIPTION));
                generic_list_model.add(cardset_dm);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return generic_list_model;
    }

    public ArrayList<NoteCardDataModel> getListOfCardsFromGroupID(long group_id) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * ");
        sb.append(String.format(Locale.US, "FROM %s ", TABLE_NAME_CARD));
        sb.append(String.format(Locale.US, "WHERE %s.%s = %d ", TABLE_NAME_CARD, ATTRIBUTE_CARDSET_ID, group_id));
        sb.append(String.format(Locale.US, "ORDER BY %s.%s ", TABLE_NAME_CARD, ATTRIBUTE_SUBJECT));

        return getListFromDatabase(sb);
    }

//    public List<NoteCardDataModel> getListOfCards() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("SELECT * ");
//        sb.append(String.format(Locale.US, "FROM %s ", TABLE_NAME_CARD));
//        sb.append(String.format(Locale.US, "ORDER BY %s.%s ", TABLE_NAME_CARD, ATTRIBUTE_SUBJECT));
//
//        return getListFromDatabase(sb);
//    }

    private ArrayList<NoteCardDataModel> getListFromDatabase(StringBuilder sb) {
        ArrayList<NoteCardDataModel> note_card_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sb.toString(), null);

        final int ID = cursor.getColumnIndex(ATTRIBUTE_ID);
        final int SUBJECT = cursor.getColumnIndex(ATTRIBUTE_SUBJECT);
        final int DESCRIPTION = cursor.getColumnIndex(ATTRIBUTE_DESCRIPTION);
        final int CARDSET_ID = cursor.getColumnIndex(ATTRIBUTE_CARDSET_ID);
        if (cursor.moveToFirst()) {
            do {
                NoteCardDataModel ncl = new NoteCardDataModel();
                ncl.setId(cursor.getInt(ID));
                ncl.setParentID(cursor.getInt(CARDSET_ID));
                ncl.setSubject(cursor.getString(SUBJECT));
                ncl.setContent(cursor.getString(DESCRIPTION));
                note_card_list.add(ncl);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return note_card_list;
    }
}
