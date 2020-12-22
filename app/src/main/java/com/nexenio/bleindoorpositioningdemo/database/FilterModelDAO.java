package com.nexenio.bleindoorpositioningdemo.database;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class FilterModelDAO {
    public static final String TABLE_NAME = "filters";
    public static final String ID_FIELD_NAME = "id";
    public static final String UUID_FIELD_NAME = "uuid";
    public static final String MINOR_FIELD_NAME = "minor";
    public static final String MAJOR_FIELD_NAME = "major";
    public static final String NAME_FIELD_NAME = "name";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            ID_FIELD_NAME + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            UUID_FIELD_NAME + " TEXT, " +
            MINOR_FIELD_NAME + " TEXT, " +
            NAME_FIELD_NAME + " TEXT, " +
            MAJOR_FIELD_NAME + " TEXT)";

    SqliteDbHelper db;

    public FilterModelDAO(SqliteDbHelper db) {
        this.db = db;
    }

    public ContentValues toContentValues(FilterModel filter) {
        ContentValues values = new ContentValues();
        values.put(UUID_FIELD_NAME, filter.uuid);
        values.put(MAJOR_FIELD_NAME, filter.major);
        values.put(MINOR_FIELD_NAME, filter.minor);
        values.put(NAME_FIELD_NAME, filter.name);
        return values;
    }

    public FilterModel fromCursor(Cursor c) {
        int id = c.getInt(c.getColumnIndex(ID_FIELD_NAME));
        String uuid = c.getString(c.getColumnIndex(UUID_FIELD_NAME));
        String major = c.getString(c.getColumnIndex(MAJOR_FIELD_NAME));
        String minor = c.getString(c.getColumnIndex(MINOR_FIELD_NAME));
        String name = c.getString(c.getColumnIndex(NAME_FIELD_NAME));
        System.out.println("from cursor uuid: " + uuid + " minor: " + minor + " major: " + major + " name: " + name);

        FilterModel filterModel = new FilterModel(uuid, minor, major, name);
        filterModel.id = id;
        return filterModel;
    }

    public void addNewFilter(FilterModel filter) {
        ContentValues values = toContentValues(filter);

        // Insert the new row, returning the primary key value of the new row
        long id = db.getWritableDatabase().insert(TABLE_NAME, null, values);
        filter.id = (int) id;
    }

    public void deleteFilter(int id) {
        db.getWritableDatabase().delete(TABLE_NAME, ID_FIELD_NAME + " = " + id, new String[]{});
    }

    public List<FilterModel> getFilters() {
        List<FilterModel> result = new ArrayList<>();
        Cursor c = db.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME, new String[]{});
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                FilterModel filterModel = fromCursor(c);
                result.add(filterModel);
            } while (c.moveToNext());
            c.close();
        }

        return result;
    }
}
