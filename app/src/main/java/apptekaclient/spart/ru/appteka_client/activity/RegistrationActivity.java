package apptekaclient.spart.ru.appteka_client.activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import apptekaclient.spart.ru.appteka_client.R;
import apptekaclient.spart.ru.appteka_client.api.ApiService;
import apptekaclient.spart.ru.appteka_client.api.ApiConnection;
import apptekaclient.spart.ru.appteka_client.requests.AddUser;

public class RegistrationActivity extends AppCompatActivity {
    private EditText loginTxt;
    private EditText passwordTxt;
    private EditText confirmPasswordTxt;
    private EditText emailTxt;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        loginTxt = findViewById(R.id.loginText);
        passwordTxt = findViewById(R.id.passwordText);
        confirmPasswordTxt = findViewById(R.id.confirmPasswordText);
        emailTxt = findViewById(R.id.emailText);
        Toolbar toolbar = findViewById(R.id.registrationToolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        Button registrationBtn = findViewById(R.id.registrationBtn);
        ApiService apiService = ApiConnection.getApiService();

        registrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login = loginTxt.getText().toString();
                String password = passwordTxt.getText().toString();
                String confirmPassword = confirmPasswordTxt.getText().toString();
                String email = emailTxt.getText().toString();

                if (!login.equals("")&&
                        !password.equals("")&&
                        !confirmPassword.equals("")&&
                        !email.equals("")&&
                         email.contains("@")) {
                    if (checkPasswords(password, confirmPassword)) {
                        AddUser addUser = new AddUser(login, password, email);
                        try {
                            if (addUser.execute().get()) {
                                rememberLoginPasswordToMemory();
                                Toast.makeText(getApplicationContext(), "Successfull registration ", Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else
                                Toast.makeText(getApplicationContext(), "User or e-mail is already exist", Toast.LENGTH_LONG).show();
                        } catch (InterruptedException e) {
                            Toast.makeText(getApplicationContext(), "Error:" + e.toString(), Toast.LENGTH_LONG).show();
                        } catch (ExecutionException e) {
                            Toast.makeText(getApplicationContext(), "Error:" + e.toString(), Toast.LENGTH_LONG).show();
                        }
                    } else
                        Toast.makeText(getApplicationContext(), "Confirm the password correctly", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "Check the information", Toast.LENGTH_LONG).show();
            }
        });

    }


    boolean checkPasswords (String password, String confirmPassword){
        return password.equals(confirmPassword);
    }

//    private void fillLoginPasswordFromMemory(){
//        String login = sharedPreferences.getString("Login","");
//        String password = sharedPreferences.getString("Password","");
//        boolean checked = sharedPreferences.getBoolean("RememberIsChecked",false);
//        loginEditTxt.setText(login);
//        passwordEditTxt.setText(password);
//        rememberCheckBox.setChecked(checked);
//    }

    private void rememberLoginPasswordToMemory(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Login", loginTxt.getText().toString());
            editor.putString("Password", "");
            editor.commit();
    }

}
