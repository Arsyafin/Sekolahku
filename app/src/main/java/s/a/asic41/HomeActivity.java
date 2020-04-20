package s.a.asic41;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    StudentItemAdapter studentItemAdapter;
    StudentDataSource studentDataSource;
    List<Student> studentList;
    ListView lvStudent;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        hubungkanKeLayout();
        studentDataSource = new StudentDataSource(this);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int menuId = item.getItemId();

        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        long studentId = studentList.get(adapterContextMenuInfo.position).getId();

        if (menuId == R.id.edit_action) {
            Intent intent = new Intent(this, FormActivity.class);
            intent.putExtra("student_id", studentId);
            startActivity(intent);

        } else {
            studentList.clear();
            studentDataSource.deleteStudent(studentId);
            List<Student> studentListNew = studentDataSource.getAllStudent();
            studentList.addAll(studentListNew);

            studentItemAdapter.notifyDataSetChanged();
        }

        return super.onContextItemSelected(item);
    }

    private void hubungkanKeLayout() {
        lvStudent = findViewById(R.id.lv_student);
        lvStudent.setOnItemClickListener(this);
        registerForContextMenu(lvStudent);
        searchView = findViewById(R.id.sv_student);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();

        if (menuId == R.id.form_action) {
            startActivity(new Intent(this, FormActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        studentList = studentDataSource.getAllStudent();
        studentItemAdapter = new StudentItemAdapter(this, studentList);
        lvStudent.setAdapter(studentItemAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {


                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                studentList.clear();

                List<Student> studentListSearch = studentDataSource.getAllStudentSearch(s);
                studentList.addAll(studentListSearch);

                studentItemAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        long StudentId = studentList.get(i).getId();

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("student_id", StudentId);
        startActivity(intent);
    }

}