package s.a.asic41;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FormActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText etNamaDepan, etNamaBelakang, etNoHp, etAlamat, etTanggal;
    RadioGroup rgGender;
    RadioButton rbPria, rbWanita;
    Spinner spJenjang;
    CheckBox cbMembaca, cbMenulis, cbMenggambar;
    Button btnSimpan;

    Student student;
    StudentDataSource studentDataSource;

    long studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        hubungkanKeLayout();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        studentDataSource = new StudentDataSource(this);


        studentId = getIntent().getLongExtra("student_id", 0);

        if (studentId > 0) {

            Student student = new Student();
            student = studentDataSource.getStudent(studentId);

            etNamaDepan.setText(student.getNamaDepan());
            etNamaBelakang.setText(student.getNamaBelakang());
            etNoHp.setText(student.getNoHp());
            etTanggal.setText(student.getTanggal());
            etAlamat.setText(student.getAlamat());

            if (student.getGender().equals("Pria")) {
                rbPria.setChecked(true);
            } else {
                rbWanita.setChecked(true);
            }

            if (student.getJenjang().equals("SD")) {
                spJenjang.setSelection(0);
            } else if (student.getJenjang().equals("SMP")) {
                spJenjang.setSelection(1);
            } else {
                spJenjang.setSelection(2);
            }

            String pisahHobi[] = student.getHobi().split(",");

            for (int i = 0; i < pisahHobi.length; i++) {
                if (pisahHobi[i].equals("Membaca")) {
                    cbMembaca.setChecked(true);
                } else if (pisahHobi[i].equals("Menulis")) {
                    cbMenulis.setChecked(true);
                } else {
                    cbMenggambar.setChecked(true);
                }
            }


        }

        etTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDate();
            }
        });
        ;
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String namaDepan = etNamaDepan.getText().toString();
                String namaBelakang = etNamaBelakang.getText().toString();
                String noHp = etNoHp.getText().toString();
                String alamat = etAlamat.getText().toString();
                String jenjang = spJenjang.getSelectedItem().toString();
                String tanggal = etTanggal.getText().toString();

                int radioId = rgGender.getCheckedRadioButtonId();
                String gender = "";

                switch (radioId) {
                    case R.id.rb_pria:
                        gender = "Pria";
                        break;
                    default:
                        gender = "Wanita";
                        break;
                }

                List<String> hobi = new ArrayList<>();

                if (cbMembaca.isChecked()) {
                    hobi.add("Membaca");
                }
                if (cbMenggambar.isChecked()) {
                    hobi.add("Menggambar");
                }
                if (cbMenulis.isChecked()) {
                    hobi.add("Menulis");
                }

                String gabungHobi = TextUtils.join(",", hobi);

                String massage = "  1. Nama Depan : " + namaDepan;
                massage = massage + "\n2. Nama Belakang :" + namaBelakang;
                massage = massage + "\n3. No Hp :" + noHp;
                massage = massage + "\n4. Gender :" + gender;
                massage = massage + "\n5. Jenjang :" + jenjang;
                massage = massage + "\n6. Hobi :" + gabungHobi;
                massage = massage + "\n7. Alamat :" + alamat;


                if (namaDepan.isEmpty()) {
                    etNamaDepan.setError("Nama Depan Kosong!!");
                    return;
                }
                if (namaBelakang.isEmpty()) {
                    etNamaBelakang.setError("Nama Belakang Kosong!!");
                    return;
                }
                if (noHp.isEmpty()) {
                    etNoHp.setError("No Hp!!");
                    return;
                }
                if (alamat.isEmpty()) {
                    etAlamat.setError("Alamat Kosong!!");
                    return;
                }


                student = new Student();

                student.setNamaDepan(namaDepan);
                student.setNamaBelakang(namaBelakang);
                student.setNoHp(noHp);
                student.setGender(gender);
                student.setJenjang(jenjang);
                student.setHobi(gabungHobi);
                student.setAlamat(alamat);
                student.setTanggal(tanggal);

                boolean cekInput;

                if (studentId > 0) {
                    student.setId(studentId);
                    cekInput = studentDataSource.editStudent(student);
                } else {
                    cekInput = studentDataSource.addStudent(student);
                }

                if (cekInput) {
                    showToast("Berhasil Input atau Edit");
                    finish();
                }

            }
        });

        /*
        Perhitungan objek1 = new Perhitungan();

        objek1.setA(5);
        objek1.setB(10);

        int a = objek1.getA();
        int b = objek1.getB();

        int hasil1 = a + b;
        int hasil2 = objek1.getPenambahan();


        Log.d("Proses 1", "Penambahan Tidak Langsung : " + hasil1);

        Log.d("Proses 2", "Penambahan Langsung : " + hasil2);
        */

    }

    private void hubungkanKeLayout() {
        etNamaDepan = findViewById(R.id.et_nama_depan);
        etNamaBelakang = findViewById(R.id.et_nama_belakang);
        etNoHp = findViewById(R.id.et_no_hp);
        etAlamat = findViewById(R.id.et_alamat);
        rgGender = findViewById(R.id.rg_gender);
        rbPria = findViewById(R.id.rb_pria);
        rbWanita = findViewById(R.id.rb_wanita);
        spJenjang = findViewById(R.id.sp_jenjang);
        cbMembaca = findViewById(R.id.cb_membaca);
        cbMenggambar = findViewById(R.id.cb_menggambar);
        cbMenulis = findViewById(R.id.cb_menulis);
        btnSimpan = findViewById(R.id.btn_simpan);
        etTanggal = findViewById(R.id.et_tanggal);
    }

    private void showToast(String massage) {
        Toast.makeText(this, massage, Toast.LENGTH_SHORT).show();
    }

    private void showDate() {
        Calendar calendar = Calendar.getInstance();

        new DatePickerDialog(this, this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE)).show();
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        etTanggal.setText(year + "/" + (month + 1) + "/" + day);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();

        if (menuId == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}