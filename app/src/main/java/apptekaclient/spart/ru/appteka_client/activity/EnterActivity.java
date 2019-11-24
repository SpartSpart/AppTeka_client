package apptekaclient.spart.ru.appteka_client.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import apptekaclient.spart.ru.appteka_client.R;
import apptekaclient.spart.ru.appteka_client.cryptography.Crypto;
import apptekaclient.spart.ru.appteka_client.requests.LogIn;

public class EnterActivity extends AppCompatActivity {
    private EditText loginEditTxt;
    private EditText passwordEditTxt;
    private CheckBox rememberCheckBox;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setContentView(R.layout.activity_enter);
        loginEditTxt = findViewById(R.id.loginText);
        passwordEditTxt = findViewById(R.id.passwordText);
        rememberCheckBox = findViewById(R.id.checkRememberLoginPassword);
        Toolbar toolbar = findViewById(R.id.enterToolbar);
        setSupportActionBar(toolbar);
        fillLoginPasswordFromMemory();

    }

    @Override
    protected void onResume() {
        super.onResume();
        fillLoginPasswordFromMemory();
    }

    public void showDrugListActivity(View view) throws Exception {
        String authorization = loginEditTxt.getText() + ":" + passwordEditTxt.getText();
        LogIn logIn = new LogIn(authorization);
        String sessionId;

        {
            try {
                sessionId = logIn.execute().get();
                if (sessionId != null) {
                    Toast.makeText(getApplicationContext(), "Login Correct", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getBaseContext(),DrugListActivity.class);
                    intent.putExtra("Authorization", authorization);
                    Crypto.setKeys(loginEditTxt.getText().toString(), passwordEditTxt.getText().toString());
//                    new Crypto().setKey(loginEditTxt.getText().toString());
                    rememberLoginPasswordToMemory(rememberCheckBox.isChecked());
                    startActivity(intent);
                } else
                    Toast.makeText(getApplicationContext(), "Login/Password Incorrect", Toast.LENGTH_LONG).show();
            } catch (InterruptedException e) {
                Toast.makeText(getApplicationContext(), "Error: "+e.toString(), Toast.LENGTH_LONG).show();
            } catch (ExecutionException e) {
                Toast.makeText(getApplicationContext(), "Error: "+e.toString(), Toast.LENGTH_LONG).show();
            }
            catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Check Connection Settings", Toast.LENGTH_LONG).show();
            }
        }
    }



    public void showRegistrationActivity(View view) {
        Intent intObj = new Intent(this, RegistrationActivity.class);
        startActivity(intObj);
    }

    private void fillLoginPasswordFromMemory(){
        String login = sharedPreferences.getString("Login","");
        String password = sharedPreferences.getString("Password","");
        boolean checked = sharedPreferences.getBoolean("RememberIsChecked",false);
        loginEditTxt.setText(login);
        passwordEditTxt.setText(password);
        rememberCheckBox.setChecked(checked);
    }

    private void rememberLoginPasswordToMemory(boolean checked){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (checked){
            editor.putString("Login", loginEditTxt.getText().toString());
            editor.putString("Password", passwordEditTxt.getText().toString());
            editor.putBoolean("RememberIsChecked",checked);
        }
        else {
            editor.putString("Login", "");
            editor.putString("Password", "");
            editor.putBoolean("RememberIsChecked",false);
        }
        editor.commit();
    }

    public void forgetPassword(View view){
        Toast.makeText(this, "Doesn't work yet.", Toast.LENGTH_SHORT).show();
    }


}
