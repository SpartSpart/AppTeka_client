package apptekaclient.spart.ru.appteka_client.activity.additional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import apptekaclient.spart.ru.appteka_client.R;
import apptekaclient.spart.ru.appteka_client.api.model.DrugModel;

import static android.graphics.Color.*;

public class DrugListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater lInflater;
    ArrayList<DrugModel> objects;
    private SharedPreferences sharedPreferences;

    public DrugListAdapter(Context context, ArrayList<DrugModel> drugs) {
        this.context = context;
        objects = drugs;
        lInflater = (LayoutInflater) this.context
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
    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.drug_list_view_model, parent, false);
        }

        final DrugModel drug = getDrug(position);

        // заполняем View в пункте списка данными

        ((TextView) view.findViewById(R.id.drugName)).setText(String.valueOf(drug.getName())+" ("+drug.getType()+")");
        ((TextView) view.findViewById(R.id.drugCount)).setText(drug.getCount());
        ((TextView) view.findViewById(R.id.drugAppointment)).setText(drug.getAppointment());
        ((TextView) view.findViewById(R.id.drugDate)).setText(drug.getDate());


        checkDrugCount(view,drug.getCount());

        try {
            checkOldDrugs(view,drug.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return view;
    }

    DrugModel getDrug(int position) {
        return ((DrugModel) getItem(position));
    }

    @SuppressLint("ResourceAsColor")
    private void checkOldDrugs(View view, String stringDrugDate) throws ParseException {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int limitDaysWarning = sharedPreferences.getInt("WarningDate",50);
        int limitDaysAlarm = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date curentDate = new Date();
        Date drugDate = dateFormat.parse(stringDrugDate);
        long dateDifference = drugDate.getTime() - curentDate.getTime();

        if ((int)(dateDifference/(24 * 60 * 60 * 1000))<limitDaysAlarm) {
            ((TextView) view.findViewById(R.id.drugDate)).setTextColor(RED);
            ((TextView) view.findViewById(R.id.drugDate)).setTypeface(null, Typeface.BOLD);
            ((TextView) view.findViewById(R.id.drugDate)).setTypeface(null, Typeface.BOLD_ITALIC);

        }
        else
            if ((int)(dateDifference/(24 * 60 * 60 * 1000))<limitDaysWarning) {
                ((TextView) view.findViewById(R.id.drugDate)).setTextColor(RED);
                ((TextView) view.findViewById(R.id.drugDate)).setTypeface(null, Typeface.NORMAL);


            }
                else {
                ((TextView) view.findViewById(R.id.drugDate)).setTextColor(R.color.defaultTextColor);
                ((TextView) view.findViewById(R.id.drugDate)).setTypeface(null, Typeface.NORMAL);
            }
    }

    @SuppressLint("ResourceAsColor")
    private void checkDrugCount(View view, String drugCount){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int alarmCount = sharedPreferences.getInt("AlarmCount",50);
        if (Integer.valueOf(drugCount)<alarmCount)
            ((TextView) view.findViewById(R.id.drugCount)).setTextColor(RED);
        else
            ((TextView) view.findViewById(R.id.drugCount)).setTextColor(R.color.defaultTextColor);
    }


}