package apptekaclient.spart.ru.appteka_client.listview;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import apptekaclient.spart.ru.appteka_client.R;
import apptekaclient.spart.ru.appteka_client.activity.DrugListActivity;
import apptekaclient.spart.ru.appteka_client.activity.EditDrugActivity;
import apptekaclient.spart.ru.appteka_client.activity.RegistrationActivity;
import apptekaclient.spart.ru.appteka_client.listview.model.DrugListViewModel;

import static android.support.v4.content.ContextCompat.startActivity;

public class DrugListAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<DrugListViewModel> objects;


    public DrugListAdapter(Context context, ArrayList<DrugListViewModel> products) {
        ctx = context;
        objects = products;
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

        final DrugListViewModel drug = getDrug(position);

        // заполняем View в пункте списка данными

        Date date = drug.getDate();
        SimpleDateFormat formatForDate = new SimpleDateFormat("dd.MM.yyyy");


        ((TextView) view.findViewById(R.id.drugName)).setText(String.valueOf(drug.getName())+" ("+drug.getType()+")");
        ((TextView) view.findViewById(R.id.drugCount)).setText(drug.getCount());
        ((TextView) view.findViewById(R.id.drugAppointment)).setText(drug.getAppointment());
        ((TextView) view.findViewById(R.id.drugDate)).setText(formatForDate.format(date));

//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(view.getContext(), "Adapter"+drug.getName(),
//                        Toast.LENGTH_SHORT).show();
//                //startActivity(new Intent(DrugListActivity.this, EditDrugActivity.class));
//            }
//        });

        return view;
    }


    DrugListViewModel getDrug(int position) {
        return ((DrugListViewModel) getItem(position));
    }

}