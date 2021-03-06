package gr.tsagi.jekyllforandroid.app.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import gr.tsagi.jekyllforandroid.app.data.PostsContract.PostEntry;
import gr.tsagi.jekyllforandroid.app.data.PostsContract.TagEntry;
import gr.tsagi.jekyllforandroid.app.data.PostsContract.CategoryEntry;

/**
 * Created by tsagi on 8/8/14.
 */
public class PostsDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 7;

    public static final String DATABASE_NAME = "posts.db";

    public PostsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a table to hold posts. A post consists of the title, the date and the post type
        final String SQL_CREATE_POSTS_TABLE = "CREATE TABLE " + PostEntry.TABLE_NAME + " (" +
                PostEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PostEntry.COLUMN_POST_ID + " TEXT NOT NULL, " +
                PostEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                PostEntry.COLUMN_DATETEXT + " TEXT, " +
                PostEntry.COLUMN_DRAFT + " INTEGER NOT NULL, " +
                PostEntry.COLUMN_CONTENT + " TEXT);";

        final String SQL_CREATE_TAGS_TABLE = "CREATE TABLE " + TagEntry.TABLE_NAME +
                " (" + TagEntry.COLUMN_POST_ID + " TEXT NOT NULL, " +
                TagEntry.COLUMN_TAG + " TEXT NOT NULL, " +
                "UNIQUE(" + TagEntry.COLUMN_POST_ID + ", " +
                TagEntry.COLUMN_TAG + ") ON CONFLICT REPLACE);";

        final String SQL_CREATE_CATEGORIES_TABLE = "CREATE TABLE " + CategoryEntry.TABLE_NAME +
                " (" + CategoryEntry.COLUMN_POST_ID + " TEXT NOT NULL, " +
                CategoryEntry.COLUMN_CATEGORY + " TEXT NOT NULL, " +
                "UNIQUE(" + CategoryEntry.COLUMN_POST_ID + ", " +
                CategoryEntry.COLUMN_CATEGORY + ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_POSTS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TAGS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_CATEGORIES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PostEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TagEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PostsContract.CategoryEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


    public void dropTables () {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + PostEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TagEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PostsContract.CategoryEntry.TABLE_NAME);
        onCreate(db);
    }
}
