package apptekaclient.spart.ru.appteka_client.listview;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Iterator;

import apptekaclient.spart.ru.appteka_client.R;
import apptekaclient.spart.ru.appteka_client.listview.model.DrugListViewModel;

import android.view.LayoutInflater;


public class ListViewAdapter extends ArrayAdapter<DrugListViewModel>{private ArrayList<DrugListViewModel> listViewModels;
    private Context context;

    public ListViewAdapter(@NonNull Context context, int resource, ArrayList<DrugListViewModel> listViewModels) {
        super(context, resource, listViewModels);
        this.context = context;
        this.listViewModels = listViewModels;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        DrugListViewModel listViewModel = getItem(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.drug_list_view_model, null);

            return view;
    }

    @Override
    public int getCount() {
        return listViewModels.size();
    }


    @Nullable
    @Override
    public DrugListViewModel getItem(int position) {
        return listViewModels.get(position);
    }

    public void deleteVoidRows(){
        for (Iterator<DrugListViewModel> iterator = listViewModels.iterator(); iterator.hasNext();){
            DrugListViewModel model = iterator.next();
            if(model.getName().equals("")) {
                iterator.remove();
                notifyDataSetInvalidated();
            }
        }

    }

}
