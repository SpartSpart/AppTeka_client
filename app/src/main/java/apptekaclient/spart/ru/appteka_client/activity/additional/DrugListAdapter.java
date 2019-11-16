package apptekaclient.spart.ru.appteka_client.activity.additional;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import apptekaclient.spart.ru.appteka_client.R;
import apptekaclient.spart.ru.appteka_client.api.model.DrugModel;

public class DrugListAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<DrugModel> objects;


    public DrugListAdapter(Context context, ArrayList<DrugModel> drugs) {
        ctx = context;
        objects = drugs;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {return position;}

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.drug_list_view_model, parent, false);
        }

        final DrugModel drug = getDrug(position);

        // заполняем View в пункте списка данными

//        String date = drug.getDate();
//        SimpleDateFormat formatForDate = new SimpleDateFormat("dd/MM/yyyy");


        ((TextView) view.findViewById(R.id.drugName)).setText(String.valueOf(drug.getName())+" ("+drug.getType()+")");
        ((TextView) view.findViewById(R.id.drugCount)).setText(drug.getCount());
        ((TextView) view.findViewById(R.id.drugAppointment)).setText(drug.getAppointment());
        ((TextView) view.findViewById(R.id.drugDate)).setText(drug.getDate());


        return view;
    }



    DrugModel getDrug(int position) {
        return ((DrugModel) getItem(position));
    }

}