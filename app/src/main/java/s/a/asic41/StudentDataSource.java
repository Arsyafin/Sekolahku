package s.a.asic41;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class StudentDataSource {

    DbHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;

    public StudentDataSource(Context context) {
        dbHelper = new DbHelper(context);
    }

    private void openDb() {
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }


    private void closeDb() {
        sqLiteDatabase = dbHelper.getReadableDatabase();
    }

    public boolean addStudent(Student student) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("nama_depan", student.getNamaDepan());
        contentValues.put("nama_belakang", student.getNamaBelakang());
        contentValues.put("no_hp", student.getNoHp());
        contentValues.put("gender", student.getGender());
        contentValues.put("jenjang", student.getJenjang());
        contentValues.put("hobi", student.getHobi());
        contentValues.put("alamat", student.getAlamat());
        contentValues.put("tanggal", student.getTanggal());

        openDb();
        long status = sqLiteDatabase.insert("student", "null", contentValues);
        closeDb();
        return status > 0;
    }

    public boolean editStudent(Student student) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("nama_depan", student.getNamaDepan());
        contentValues.put("nama_belakang", student.getNamaBelakang());
        contentValues.put("no_hp", student.getNoHp());
        contentValues.put("gender", student.getGender());
        contentValues.put("jenjang", student.getJenjang());
        contentValues.put("hobi", student.getHobi());
        contentValues.put("alamat", student.getAlamat());
        contentValues.put("tanggal", student.getTanggal());

        openDb();
        long status = sqLiteDatabase.update("student", contentValues,"id="+student.getId(), null);
        closeDb();
        return status > 0;
    }

    private Student fetchToPojo(Cursor cursor) {
        Student student = new Student();

        student.setId(cursor.getLong(0));
        student.setNamaDepan(cursor.getString(1));
        student.setNamaBelakang(cursor.getString(2));
        student.setNoHp(cursor.getString(3));
        student.setGender(cursor.getString(4));
        student.setJenjang(cursor.getString(5));
        student.setHobi(cursor.getString(6));
        student.setAlamat(cursor.getString(7));
        student.setTanggal(cursor.getString(8));

        return student;
    }

    public List<Student> getAllStudent() {

        String quuery = "SELECT * FROM student";
        openDb();
        Cursor cursor = sqLiteDatabase.rawQuery(quuery, null);
        closeDb();
        cursor.moveToFirst();

        List<Student> studentList = new ArrayList<>();

        while (!cursor.isAfterLast()) {
            Student student = fetchToPojo(cursor);
            studentList.add(student);
            cursor.moveToNext();
        }

        return studentList;
    }

    public List<Student> getAllStudentSearch(String keyword) {

        String quuery = "SELECT * FROM student WHERE nama_depan LIKE ? OR nama_belakang LIKE ?";
        openDb();
        Cursor cursor = sqLiteDatabase.rawQuery(quuery, new String[]{"%"+keyword+"%","%"+keyword+"%"});
        closeDb();
        cursor.moveToFirst();

        List<Student> studentList = new ArrayList<>();

        while (!cursor.isAfterLast()) {
            Student student = fetchToPojo(cursor);
            studentList.add(student);
            cursor.moveToNext();
        }

        return studentList;
    }

    public Student getStudent(long id) {
        String query = "SELECT * FROM student WHERE id=" + id;
        openDb();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        closeDb();
        cursor.moveToFirst();
        return fetchToPojo(cursor);
    }

    public void deleteStudent(long id){
        openDb();
        sqLiteDatabase.delete("student","id="+id,null);
        closeDb();
    }
}