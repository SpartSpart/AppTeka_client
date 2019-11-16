package apptekaclient.spart.ru.appteka_client.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import apptekaclient.spart.ru.appteka_client.R;
import apptekaclient.spart.ru.appteka_client.activity.additional.DateTextWatcher;
import apptekaclient.spart.ru.appteka_client.api.model.DrugModel;
import apptekaclient.spart.ru.appteka_client.requests.AddDrug;
import apptekaclient.spart.ru.appteka_client.requests.UpdateDrug;

public class EditDrugActivity extends AppCompatActivity {
    private EditText drugName;
    private EditText drugCount;
    private EditText drugDate;
    private Spinner typeSpinner;
    private Spinner appointmentSpinner;
    private DrugModel drugModel;
    private String authorization;
    private ArrayList<String> types;
    private ArrayList<String> appointments;
    private boolean newDrug;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_drug);
        Toolbar toolbar = findViewById(R.id.editDrugToolbar);
        setSupportActionBar(toolbar);
        drugName = findViewById(R.id.drugName);
        drugCount = findViewById(R.id.drugCount);
        drugDate = findViewById(R.id.drugDate);

        //TextWatcher for Date EditText
        drugDate.addTextChangedListener(new DateTextWatcher(drugDate));

        typeSpinner = findViewById(R.id.drugTypeSpinner);
        appointmentSpinner = findViewById(R.id.drugAppointmentSpinner);
        authorization = DrugListActivity.authorization;
        newDrug = true;

        drugModel = (DrugModel) getIntent().getSerializableExtra("DrugModel");
        if (drugModel != null) {

            //заполнение полей, поля Тип и Назначение заполняются методами addTypeSpinner и addAppointmentSpinner
            drugName.setText(drugModel.getName());
            drugCount.setText(drugModel.getCount());
            drugDate.setText(drugModel.getDate());
            newDrug = false;
        }
        addTypeSpinner(newDrug);
        addAppointmentSpinner(newDrug);

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
        switch (id) {
            case R.id.action_cancel: {
//                String stringDate = "2020-12-12";
//                sendDrugToDB(new DrugModel("111","Сироп","333","Горло",stringDate));
                finish();
                return true;
            }
            case R.id.action_save: {
                try {
                    saveDrug(getDrug(drugName.getText().toString(),
                            typeSpinner.getSelectedItem().toString(),
                            drugCount.getText().toString(),
                            appointmentSpinner.getSelectedItem().toString(),
                            drugDate.getText().toString()));
                } catch (ParseException e) {
                    Toast.makeText(this, "Ошибка в вызове пункта меню", Toast.LENGTH_SHORT).show();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveDrug(DrugModel drugModel) throws ExecutionException, InterruptedException {

        if (drugModel != null) {
            DrugModel newDrugModel = sendDrugToDB(drugModel);
            Intent intent = new Intent();
            intent.putExtra("DrugModelReturn", newDrugModel);
            setResult(1, intent);
            finish();
        }
    }

    private DrugModel getDrug(String name, String type, String count, String appointment, String date) throws ParseException {

        try {
            if (!name.equals("") &&
                    !type.equals("") &&
                    !count.equals("") &&
                    !appointment.equals("") &&
                    !date.equals("")&&
                    !date.contains("Y")) {

                DrugModel drugModelLocal = drugModel;

                if (drugModelLocal != null) {
                    drugModelLocal = new DrugModel(drugModel.getId(), name, type, count, appointment, date);
                } else {
                    drugModelLocal = new DrugModel(name, type, count, appointment, date);
                }

                return drugModelLocal;
            } else Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Ошибка формирования DrugModel", Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    private DrugModel sendDrugToDB(DrugModel drugModel) {

        Long id = drugModel.getId();

        try {
            if (id == 0) {
                AddDrug addDrug = new AddDrug(authorization, drugModel);
                id = addDrug.execute().get();
                drugModel.setId(id); //если новая запись, то нужно чтобы в листе отобразилась запись с id, иначе при редактировании -  создастся новая
            } else {
                UpdateDrug updateDrug = new UpdateDrug(authorization, drugModel.getId(), drugModel);
                updateDrug.execute();
            }

        } catch (InterruptedException e) {
            Toast.makeText(this, "sendDrugToDB: " + e.toString(), Toast.LENGTH_SHORT).show();
        } catch (ExecutionException e) {
            Toast.makeText(this, "sendDrugToDB: " + e.toString(), Toast.LENGTH_SHORT).show();
        }
        return drugModel;
    }

    private void addTypeSpinner(boolean newDrug) {
        types = (ArrayList<String>) getIntent().getSerializableExtra("Types");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner, R.id.row, types);
        typeSpinner.setAdapter(adapter);

        if (!newDrug) {
            for (int i = 0; i < types.size(); i++) {
                if (types.get(i).equals(drugModel.getType())) {
                    typeSpinner.setSelection(i);
                    break;
                }
            }
        }
    }

    private void addAppointmentSpinner(boolean newDrug) {
        appointments = (ArrayList<String>) getIntent().getSerializableExtra("Appointments");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner, R.id.row, appointments);
        appointmentSpinner.setAdapter(adapter);

        if (!newDrug) {
            for (int i = 0; i < appointments.size(); i++) {
                if (appointments.get(i).equals(drugModel.getAppointment())) {
                    appointmentSpinner.setSelection(i);
                    break;
                }
            }
        }
    }
}
