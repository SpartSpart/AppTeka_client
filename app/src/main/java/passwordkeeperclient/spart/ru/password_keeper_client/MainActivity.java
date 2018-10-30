package passwordkeeperclient.spart.ru.password_keeper_client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;

import passwordkeeperclient.spart.ru.password_keeper_client.gridView.GridViewAdapter;
import passwordkeeperclient.spart.ru.password_keeper_client.gridView.GridViewModel;

public class MainActivity extends AppCompatActivity {
    private GridView mainGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        mainGrid = findViewById(R.id.mainGrid);
        testPushGrid();
    }

    public void testPushGrid(){
//        ArrayList<String> data=new ArrayList<String>(Arrays.asList("one","two","three","four","five","six","seven","eight","nine","ten"));
//        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);

        ArrayList<GridViewModel> gridViewModels = new ArrayList<>();
        for (int i=0;i<10;i++){
            String s = String.valueOf(i);
            gridViewModels.add(new GridViewModel(s,s,s));
        }

        GridViewAdapter adapter1 = new GridViewAdapter(getApplicationContext(),android.R.layout.simple_list_item_1, gridViewModels);
        mainGrid.setAdapter(adapter1);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
