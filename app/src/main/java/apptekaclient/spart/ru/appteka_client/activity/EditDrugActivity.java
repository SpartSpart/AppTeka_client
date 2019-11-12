package apptekaclient.spart.ru.appteka_client.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import apptekaclient.spart.ru.appteka_client.R;
import apptekaclient.spart.ru.appteka_client.api.model.DrugModel;
import apptekaclient.spart.ru.appteka_client.listview.model.DrugListViewModel;

public class EditDrugActivity extends AppCompatActivity {
    private EditText drugName;
    private EditText drugType;
    private EditText drugCount;
    private EditText drugAppointment;
    private EditText drugDate;
    private DrugListViewModel drugModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_drug);
        Toolbar toolbar = findViewById(R.id.editDrugToolbar);
        setSupportActionBar(toolbar);
        drugName = findViewById(R.id.drugName);
        drugType = findViewById(R.id.drugType);
        drugCount = findViewById(R.id.drugCount);
        drugAppointment = findViewById(R.id.drugAppointment);
        drugDate = findViewById(R.id.drugDate);

        drugModel = (DrugListViewModel)getIntent().getSerializableExtra("DrugListViewModel");
        if (drugModel!=null){
            drugName.setText(drugModel.getName());
            drugType.setText(drugModel.getType());
            drugCount.setText(drugModel.getCount());
            drugAppointment.setText(drugModel.getAppointment());
            drugDate.setText(drugModel.getDate().toString());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_drug, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (id){
        case R.id.action_cancel:{
                finish();
                return true;
            }
            case R.id.action_save: {
                try {
                    saveDrug(getDrug(drugName.getText().toString(),
                            drugType.getText().toString(),
                            drugCount.getText().toString(),
                            drugAppointment.getText().toString(),
                            drugDate.getText().toString()));
                } catch (ParseException e) {
                    Toast.makeText(this, "Ошибка в вызове пункта меню", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        }
      return super.onOptionsItemSelected(item);
    }

    private void saveDrug (DrugListViewModel drugListViewModel){
        Intent intent = new Intent();
        intent.putExtra("DrugListViewModelReturn", drugListViewModel);
        if (drugListViewModel!=null)
            setResult(RESULT_OK, intent);
//        else
//            setResult(RESULT_CANCELED, intent);
        finish();
    }

    private DrugListViewModel getDrug(String name, String type, String count, String appointment, String stringDate) throws ParseException {

        try {
            Date date =new SimpleDateFormat("dd.MM.yyyy").parse(stringDate);
            if (name != "" &&
                    type != "" &&
                    count != "" &&
                    appointment != "" &&
                    stringDate != "") {
                DrugListViewModel drugListViewModel = new DrugListViewModel(1, name, type, count, appointment, date);
                return drugListViewModel;
            }
            else Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this, "Ошибка формирования DrugListViewModel", Toast.LENGTH_SHORT).show();
        }

        return null;
    }

}
