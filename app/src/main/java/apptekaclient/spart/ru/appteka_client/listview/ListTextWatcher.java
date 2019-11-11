package apptekaclient.spart.ru.appteka_client.listview;

import android.text.Editable;
import android.text.TextWatcher;

import apptekaclient.spart.ru.appteka_client.activity.MainActivity;
import apptekaclient.spart.ru.appteka_client.listview.model.DrugListViewModel;

public class ListTextWatcher implements TextWatcher {
    private DrugListViewModel listViewModel;
    private String atributeType;
    private int position;

    ListTextWatcher(DrugListViewModel listViewModel, int position, String atributeType) {
        this.listViewModel = listViewModel;
        this.atributeType = atributeType;
        this.position = position;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {

        switch (atributeType) {
            case "NAME": {
                listViewModel.setName(s.toString());
                break;
            }

        }

        MainActivity.changedID.add(position);
    }


}
