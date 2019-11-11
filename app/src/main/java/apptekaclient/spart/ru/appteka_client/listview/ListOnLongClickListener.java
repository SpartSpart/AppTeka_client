package apptekaclient.spart.ru.appteka_client.listview;

import android.view.View;
import android.widget.Toast;

import apptekaclient.spart.ru.appteka_client.listview.model.DrugListViewModel;

public class ListOnLongClickListener implements View.OnLongClickListener {

    private DrugListViewModel listViewModel;

    ListOnLongClickListener(DrugListViewModel listViewModel) {
        this.listViewModel = listViewModel;

    }


    @Override //first variant of longClick
    public boolean onLongClick(View v) {
        Toast.makeText(v.getContext(),
                listViewModel.getId() + "\n" + listViewModel.getName(),
                Toast.LENGTH_SHORT).show();
        return true;
    }

}



