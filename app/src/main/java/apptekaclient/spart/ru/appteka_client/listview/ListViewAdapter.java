package apptekaclient.spart.ru.appteka_client.listview;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Iterator;

import apptekaclient.spart.ru.appteka_client.R;
import apptekaclient.spart.ru.appteka_client.listview.model.ListViewModel;

import android.view.LayoutInflater;


public class ListViewAdapter extends ArrayAdapter<ListViewModel>{private ArrayList<ListViewModel> listViewModels;
    private Context context;

    public ListViewAdapter(@NonNull Context context, int resource, ArrayList<ListViewModel> listViewModels) {
        super(context, resource, listViewModels);
        this.context = context;
        this.listViewModels = listViewModels;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ListViewModel listViewModel = getItem(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_view_model, null);

        EditText description = view.findViewById(R.id.description);
        EditText login = view.findViewById(R.id.login);
        EditText password = view.findViewById(R.id.password);

        description.setText(listViewModel.getDescription());
        login.setText(listViewModel.getLogin());
        password.setText(listViewModel.getPassword());

        description.addTextChangedListener(new ListTextWatcher(listViewModel,position,"DESCRIPTION"));
        login.addTextChangedListener(new ListTextWatcher(listViewModel,position,"LOGIN"));
        password.addTextChangedListener(new ListTextWatcher(listViewModel,position,"PASSWORD"));

        description.setOnLongClickListener(new ListOnLongClickListener(listViewModel));
        login.setOnLongClickListener(new ListOnLongClickListener(listViewModel));
        password.setOnLongClickListener(new ListOnLongClickListener(listViewModel));

        return view;
    }

    @Override
    public int getCount() {
        return listViewModels.size();
    }


    @Nullable
    @Override
    public ListViewModel getItem(int position) {
        return listViewModels.get(position);
    }

    public void deleteVoidRows(){
        for (Iterator<ListViewModel> iterator=listViewModels.iterator();iterator.hasNext();){
            ListViewModel model = iterator.next();
            if(
            model.getDescription().equals("")
            &&model.getLogin().equals("")
            &&model.getPassword().equals("")){

                iterator.remove();
                notifyDataSetInvalidated();
            }
        }

    }

}