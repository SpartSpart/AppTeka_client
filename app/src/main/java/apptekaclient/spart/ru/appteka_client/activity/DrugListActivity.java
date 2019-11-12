package apptekaclient.spart.ru.appteka_client.activity;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import apptekaclient.spart.ru.appteka_client.R;
import apptekaclient.spart.ru.appteka_client.listview.DrugListAdapter;
import apptekaclient.spart.ru.appteka_client.listview.model.DrugListViewModel;

public class DrugListActivity extends AppCompatActivity {

    ArrayList<DrugListViewModel> listViewModels = new ArrayList<DrugListViewModel>();
    DrugListAdapter drugListAdapter;


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
        setSupportActionBar(toolbar);

        // создаем адаптер
        fillData();
        drugListAdapter = new DrugListAdapter(this, listViewModels);

        // настраиваем список
        ListView listView = findViewById(R.id.drugListView);
        listView.setAdapter(drugListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long l) {
                Toast.makeText(view.getContext(), "Adapter",
                        Toast.LENGTH_SHORT).show();
                editDrug((DrugListViewModel) parent.getItemAtPosition(pos));
            }
        });
    }

    // генерируем данные для адаптера
    void fillData() {
        for (int i = 1; i <= 20; i++) {
            listViewModels.add(new DrugListViewModel(i, String.valueOf(i + 1000), "Sirop", String.valueOf(i + 50), "Head", new Date()));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_new: {
                editDrug(null);
                return true;
            }
//            case R.id.action_save: {
//
//                return true;
//            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void editDrug(DrugListViewModel drugListViewModel) {
        Intent intent = new Intent(getBaseContext(), EditDrugActivity.class);
        intent.putExtra("DrugListViewModel", drugListViewModel);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        DrugListViewModel drugListViewModel = (DrugListViewModel)data.getSerializableExtra("DrugViewListModelReturn");
        Toast.makeText(this, "Success transfer back to Activity", Toast.LENGTH_SHORT).show();
    }
}