package s.a.asic41;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        hubungkanKeLayout();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = "a";
                String password = "a";

                if (!username.equals(etUsername.getText().toString()) &&
                        !password.equals(etPassword.getText().toString())) {
                    alertDialog("Username and Password Wrong !!!");

                } else if (!username.equals(etUsername.getText().toString()) ||
                        !password.equals(etPassword.getText().toString())) {
                    alertDialog("Username or Password Wrong !!!");

                } else {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                }


            }
        });
    }

    private void alertDialog(String massage) {
        DialogInterface.OnClickListener click = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                switch (i) {
                    case DialogInterface.BUTTON_POSITIVE:
                        dialogInterface.dismiss();
                        break;

                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(massage).setPositiveButton("Ok", click)
                .setNegativeButton("Cancel", click).show();
    }

    private void showToast(String massage) {
        Toast.makeText(this, massage, Toast.LENGTH_SHORT).show();
    }

    private void hubungkanKeLayout() {
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
    }

}