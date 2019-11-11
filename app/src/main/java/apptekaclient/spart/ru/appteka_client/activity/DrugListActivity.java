package apptekaclient.spart.ru.appteka_client.activity;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
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

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_list);
        Toolbar toolbar = findViewById(R.id.drugListViewToolbar);
        setSupportActionBar(toolbar);



        // создаем адаптер
        fillData();
        drugListAdapter = new DrugListAdapter(this, listViewModels);

        // настраиваем список
        ListView listView = (ListView) findViewById(R.id.drugListView);
        listView.setAdapter(drugListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                // по позиции получаем выбранный элемент
                DrugListViewModel drugListViewModel = (DrugListViewModel) parent.getItemAtPosition(position);
                Toast.makeText(v.getContext(), "Yeahhh",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    // генерируем данные для адаптера
    void fillData() {
        for (int i = 1; i <= 20; i++) {
            listViewModels.add(new DrugListViewModel(i,String.valueOf(i+1000),"Sirop",String.valueOf(i+50),"Head", new Date()));
        }
    }



    // выводим информацию о корзине
//    public void showResult(View v) {
//        String result = "Товары в корзине:";
//        for (Product p : drugListAdapter.getBox()) {
//            if (p.box)
//                result += "\n" + p.name;
//        }
//        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
//    }
}