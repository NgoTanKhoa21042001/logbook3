package com.example.logbook3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DataBaseHandler extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "logbook3.1.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "table_image";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_URL = "image_url";
    public DataBaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    // onCreate(): Đây là nơi để chúng ta viết những câu lệnh tạo bảng.
    // Nó được gọi khi database đã được tạo.
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_URL + " TEXT) ; " ;
        db.execSQL(query);
    }

    @Override
    // onUpgrade(): Nó được gọi khi database được nâng cấp,
    // ví dụ như chỉnh sửa cấu trúc các bảng, thêm những thay đổi cho database,..
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME);
        onCreate(db);
    }
    void addImage(String url) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_URL, url);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Add successfully", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}
