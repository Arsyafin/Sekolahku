package s.a.asic41;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {


    public DbHelper(Context context){
        super(context,"sekolahku.db",null,2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query="CREATE TABLE student (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nama_depan TEXT," +
                "nama_belakang TEXT," +
                "no_hp TEXT," +
                "gender TEXT," +
                "jenjang TEXT," +
                "hobi TEXT," +
                "alamat TEXT," +
                "tanggal TEXT)";

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}