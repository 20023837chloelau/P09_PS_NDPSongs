 package sg.edu.rp.c346.id20023837.p09psndpsongs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ndpsongs.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_SONGS = "song";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_SINGERS = "singers";
    private static final String COLUMN_YEARS = "years";
    private static final String COLUMN_STARS = "stars";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNoteTableSql = "CREATE TABLE " + TABLE_SONGS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_SINGERS + " TEXT,"
                + COLUMN_YEARS + " INTEGER,"
                + COLUMN_STARS + " INTEGER )";
        db.execSQL(createNoteTableSql);
        Log.i("info", "created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONGS);
        onCreate(db);
    }

    public long insertSong(String title, String singers, int year, int stars) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_SINGERS, singers);
        values.put(COLUMN_YEARS, year);
        values.put(COLUMN_STARS,stars);

        long result = db.insert(TABLE_SONGS, null, values);
        db.close();
        Log.d("SQL Insert", "ID:" + result); //id returned, shouldn’t be -1
        return result;
    }

    public ArrayList<Song> getAllSongsByStars(int starFilter) {
        ArrayList<Song> songslist = new ArrayList<Song>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_SINGERS, COLUMN_YEARS, COLUMN_STARS};
        String condition = COLUMN_STARS + ">=?";
        String[] args = {String.valueOf(starFilter)};

        Cursor cursor;
        cursor = db.query(TABLE_SONGS, columns, condition, args, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singer = cursor.getString(2);
                int year = cursor.getInt(3);
                int stars = cursor.getInt(4);


                Song newSong = new Song(id, title, singer, year, stars);
                songslist.add(newSong);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songslist;

    }

    public ArrayList<Song> getAllSongs() {
        ArrayList<Song> songs = new ArrayList<Song>();

        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_TITLE + ","
                + COLUMN_SINGERS + ","
                + COLUMN_YEARS + ","
                + COLUMN_STARS + " FROM " + TABLE_SONGS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int years = cursor.getInt(3);
                int stars = cursor.getInt(4);

                Song song = new Song(id, title, singers, years, stars);
                songs.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }

    public int updateSong(Song data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, data.getTitle());
        values.put(COLUMN_SINGERS, data.getSingers());
        values.put(COLUMN_YEARS, data.getYear());
        values.put(COLUMN_STARS, data.getStar());

        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.get_id())};

        int result = db.update(TABLE_SONGS, values, condition, args);
        db.close();
        return result;
    }

    public int deleteSong(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};

        int result = db.delete(TABLE_SONGS, condition, args);
        db.close();
        return result;
    }
}