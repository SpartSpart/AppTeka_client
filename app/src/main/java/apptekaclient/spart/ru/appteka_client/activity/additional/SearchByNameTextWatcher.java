package apptekaclient.spart.ru.appteka_client.activity.additional;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Pamela on 20.11.2019.
 */

public class SearchByNameTextWatcher implements TextWatcher {
    private DrugListAdapter drugListAdapter;

    public SearchByNameTextWatcher(DrugListAdapter drugListAdapter) {
        this.drugListAdapter = drugListAdapter;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        drugListAdapter.filterByWarning = FilterByWarning.NONE;
        drugListAdapter.getFilter().filter(s);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
