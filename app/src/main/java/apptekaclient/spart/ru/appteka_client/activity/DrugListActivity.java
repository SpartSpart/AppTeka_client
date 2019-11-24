package apptekaclient.spart.ru.appteka_client.activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import apptekaclient.spart.ru.appteka_client.R;
import apptekaclient.spart.ru.appteka_client.activity.additional.FilterByWarning;
import apptekaclient.spart.ru.appteka_client.activity.additional.SearchByNameTextWatcher;
import apptekaclient.spart.ru.appteka_client.api.model.DrugModel;
import apptekaclient.spart.ru.appteka_client.activity.additional.DrugListAdapter;
import apptekaclient.spart.ru.appteka_client.requests.DeleteDrug;
import apptekaclient.spart.ru.appteka_client.requests.GetAllDrugs;
import apptekaclient.spart.ru.appteka_client.activity.additional.DrugAppointment;
import apptekaclient.spart.ru.appteka_client.activity.additional.DrugType;

public class DrugListActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<DrugModel> listViewModels;
    DrugListAdapter drugListAdapter;
    private int selectedListPosition;
    public static String authorization;
    private ArrayList<String> types;
    private ArrayList<String> appointments;
    private EditText searchDrugByName;
    private boolean newDrug;
    private SearchByNameTextWatcher searchByNameTextWatcher;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_drug_list, menu);
        return true;
    }

    /**
     * Called when the activity is first created.
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_list);
        Toolbar toolbar = findViewById(R.id.drugListViewToolbar);
        searchDrugByName =findViewById(R.id.searchDrugByName);
        setSupportActionBar(toolbar);
        listViewModels = new ArrayList<DrugModel>();
        selectedListPosition = 0;
        authorization = authorization();

        //fill listViewModels, types, appointments
        try {
            fillData();

        } catch (Exception e) {
            e.printStackTrace();
        }
        // create adapter
        drugListAdapter = new DrugListAdapter(this, listViewModels);

        searchByNameTextWatcher =new SearchByNameTextWatcher(drugListAdapter);

        searchDrugByName.addTextChangedListener(searchByNameTextWatcher);

        //update settings parameters
        listViewSettings ();

     }

     private String authorization(){
        String authorization = (String) getIntent().getSerializableExtra("Authorization");
        return authorization;
     }


    // генерируем данные для адаптера
    void fillData() {

            ArrayList <DrugModel> drugModelArrayList = new ArrayList<>(getAllDrugs(authorization));
            listViewModels.clear();

            for (int i = 0; i < drugModelArrayList.size(); i++){
                listViewModels.add(drugModelArrayList.get(i));
            }

        try {
            types = getDrugTypes();
            appointments = getDrugAppointments();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public Collection<DrugModel> getAllDrugs(String authorization) {
        GetAllDrugs getAllDrugs = new GetAllDrugs(authorization);

        try {
            return getAllDrugs.execute().get();

        } catch (InterruptedException e) {
            Toast.makeText(this, "Error:" + e.toString(), Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            Toast.makeText(this, "Error:" + e.toString(), Toast.LENGTH_LONG).show();
        }

        return null;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_new: {
                editDrug(null);
                newDrug = true;
                return true;
            }
            case R.id.action_refresh: {
                try {
                    listViewModels.clear();
                    fillData();
                    drugListAdapter.getFilter().filter(null);
                    drugListAdapter.notifyDataSetChanged();
                    searchDrugByName.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return true;
            }
            case R.id.action_settings: {
                settings();
                return true;
            }
            case R.id.action_filter_warning_count_drugs:{
                drugListAdapter.filterByWarning = FilterByWarning.WARNING_COUNT;

                drugListAdapter.getFilter().filter("IGNORE");


                return true;
            }
            case R.id.action_filter_warning_date_drugs:{
                drugListAdapter.filterByWarning = FilterByWarning.WARNING_DATE;

                drugListAdapter.getFilter().filter("IGNORE");
                return true;
            }
            case R.id.action_filter_alarm_date_drugs:{
                drugListAdapter.filterByWarning = FilterByWarning.ALARM_DATE;

                drugListAdapter.getFilter().filter("IGNORE");
                return true;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    public void editDrug(DrugModel drugModel) {
        Intent intent = new Intent(getBaseContext(), EditDrugActivity.class);
        intent.putExtra("Types",types);
        intent.putExtra("Appointments", appointments);
        intent.putExtra("DrugModel", drugModel);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        DrugModel drugModel = (DrugModel)data.getSerializableExtra("DrugModelReturn");
        try {
            if (newDrug)  //проверка на новую запись или обновляемую
                listViewModels.add(drugModel);
            else {
                listViewModels.get(selectedListPosition).setName(drugModel.getName());
                listViewModels.get(selectedListPosition).setType(drugModel.getType());
                listViewModels.get(selectedListPosition).setCount(drugModel.getCount());
                listViewModels.get(selectedListPosition).setAppointment(drugModel.getAppointment());
                listViewModels.get(selectedListPosition).setDate(drugModel.getDate());
            }

        }
        catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

        selectedListPosition = 0;



    }

    private void refreshFilteredDrugs(){
        switch (drugListAdapter.filterByWarning){
            case NONE:{

                drugListAdapter.getFilter().filter(searchDrugByName.getText());
                break;
            }
            case WARNING_COUNT:{

                drugListAdapter.getFilter().filter("IGNORE");

                break;
            }
            case WARNING_DATE:{

                drugListAdapter.getFilter().filter("IGNORE");

                break;
            }
            case ALARM_DATE:{

                drugListAdapter.getFilter().filter("IGNORE");
                searchDrugByName.setText("");
                break;
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        refreshDrugListAdapter();
   }

    private void refreshDrugListAdapter(){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listViewModels.clear();
                fillData();
                drugListAdapter.notifyDataSetChanged();
                refreshFilteredDrugs();
           }
        });
    }



    private void listViewSettings() {
        // настраиваем список
        listView = findViewById(R.id.drugListView);
        listView.setTextFilterEnabled(true);
        listView.setFocusable(true);

        listView.setAdapter(drugListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long l) {
                editDrug((DrugModel) parent.getItemAtPosition(pos));
                selectedListPosition = pos;
                newDrug = false;
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> drugs, View arg1,
                                           int pos, long id) {
                DrugModel drugModel = (DrugModel)drugs.getItemAtPosition(pos);
                deleteDialog(drugModel.getId(),drugModel.getName(), pos);
                return true;
            }
        });

    }

    private void deleteDialog(final Long drugId, final String drugName, final int listViewPosition){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Delete Drug");
        builder.setMessage("Delete "+drugName+"?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(DrugListActivity.this, "Deleting "+drugName, Toast.LENGTH_SHORT).show();
                DeleteDrug deleteDrug = new DeleteDrug(authorization, drugId);
                deleteDrug.execute();

                listViewModels.remove(listViewPosition);
                refreshDrugListAdapter();

                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private ArrayList<String> getDrugTypes() throws ExecutionException, InterruptedException {
        ArrayList<String> types = null;
        DrugType drugType = new DrugType();
        types = drugType.getTypes();
        return types;
    }

    private ArrayList<String> getDrugAppointments() throws ExecutionException, InterruptedException {
        ArrayList<String> appoinments = null;
        DrugAppointment drugAppointment = new DrugAppointment();
        appoinments = drugAppointment.getAppointments();
        return appoinments;
    }

    private void settings(){
        Intent intent = new Intent(getBaseContext(), SettingsActivity.class);
        startActivity(intent);
    }

}