package apptekaclient.spart.ru.appteka_client.activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.widget.SeekBar;
import android.widget.TextView;

import apptekaclient.spart.ru.appteka_client.R;
import apptekaclient.spart.ru.appteka_client.activity.listeners.SeekBarListener;

public class SettingsActivity extends AppCompatActivity {
    private SeekBar alarmCountSeekBar;
    private SeekBar warningDateSeekBar;
    private TextView alarmCountTxtView;
    private TextView warningDateTxtView;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.settingToolbar);
        setSupportActionBar(toolbar);
        alarmCountSeekBar = findViewById(R.id.alarmCountSeekBar);
        warningDateSeekBar = findViewById(R.id.warningDateSeekBar);

        alarmCountTxtView = findViewById(R.id.alarmCountTxtView);
        warningDateTxtView = findViewById(R.id.warningDateTxtView);

        alarmCountSeekBar.setOnSeekBarChangeListener(new SeekBarListener(alarmCountSeekBar,alarmCountTxtView));
        warningDateSeekBar.setOnSeekBarChangeListener(new SeekBarListener(warningDateSeekBar,warningDateTxtView));

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            alarmCountSeekBar.setProgress(sharedPreferences.getInt("AlarmCount",50));
            warningDateSeekBar.setProgress(sharedPreferences.getInt("WarningDate",50));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_save: {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("AlarmCount", Integer.valueOf((String) alarmCountTxtView.getText()));
                editor.putInt("WarningDate", Integer.valueOf((String) warningDateTxtView.getText()));
                editor.commit();
                finish();
                return true;
            }
            case R.id.action_cancel:{
                finish();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }


//    String getHost(){return sharedPreferences.getString("Host","");}
//
//    String getPort(){
//        return sharedPreferences.getString("Port","");
//    }


}
