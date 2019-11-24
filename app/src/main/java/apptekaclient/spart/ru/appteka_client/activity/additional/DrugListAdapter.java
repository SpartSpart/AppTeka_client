package apptekaclient.spart.ru.appteka_client.activity.additional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import apptekaclient.spart.ru.appteka_client.R;
import apptekaclient.spart.ru.appteka_client.api.model.DrugModel;

import static android.graphics.Color.*;
import static apptekaclient.spart.ru.appteka_client.activity.additional.FilterByWarning.NONE;

public class DrugListAdapter extends BaseAdapter implements Filterable {
    Context context;
    LayoutInflater lInflater;
    ArrayList<DrugModel> drugs;
    ArrayList<DrugModel> filteredDrugs;
    private SharedPreferences sharedPreferences;
    public FilterByWarning filterByWarning;

    public DrugListAdapter(Context context, ArrayList<DrugModel> drugs) {
        this.context = context;
        this.drugs = drugs;
        filteredDrugs = drugs;
        lInflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        filterByWarning = FilterByWarning.NONE;

    }

    // кол-во элементов
    @Override
    public int getCount() {
        return filteredDrugs.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return filteredDrugs.get(position);
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


        boolean checkCountStatusIsOk = checkDrugCount(view,drug.getCount());

        try {
            checkOldDrugs(view,drug.getDate(), checkCountStatusIsOk);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return view;
    }

    DrugModel getDrug(int position) {
        return ((DrugModel) getItem(position));
    }

    @SuppressLint("ResourceAsColor")
    private void checkOldDrugs(View view, String stringDrugDate, boolean checkCountStatusIsOk) throws ParseException {
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
            ((ImageView)view.findViewById(R.id.drugStatusImage)).setImageResource(R.drawable.ic_status_alarm);

        }
        else
            if ((int)(dateDifference/(24 * 60 * 60 * 1000))<limitDaysWarning) {
                ((TextView) view.findViewById(R.id.drugDate)).setTextColor(RED);
                ((TextView) view.findViewById(R.id.drugDate)).setTypeface(null, Typeface.NORMAL);
                ((ImageView)view.findViewById(R.id.drugStatusImage)).setImageResource(R.drawable.ic_status_warning);

            }
                else {
                ((TextView) view.findViewById(R.id.drugDate)).setTextColor(R.color.defaultTextColor);
                ((TextView) view.findViewById(R.id.drugDate)).setTypeface(null, Typeface.NORMAL);

                if (checkCountStatusIsOk){
                    ((ImageView)view.findViewById(R.id.drugStatusImage)).setImageResource(R.drawable.ic_status_ok);
                }

            }
    }

    @SuppressLint("ResourceAsColor")
    private boolean checkDrugCount(View view, String drugCount){
        boolean statusIsOk = true;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int alarmCount = sharedPreferences.getInt("AlarmCount",50);
        if (Integer.valueOf(drugCount)<alarmCount){
            ((TextView) view.findViewById(R.id.drugCount)).setTextColor(RED);
            ((ImageView)view.findViewById(R.id.drugStatusImage)).setImageResource(R.drawable.ic_status_warning);
            statusIsOk = false;
        }

        else {
            ((TextView) view.findViewById(R.id.drugCount)).setTextColor(R.color.defaultTextColor);
            ((ImageView)view.findViewById(R.id.drugStatusImage)).setImageResource(R.drawable.ic_status_ok);
        }
        return statusIsOk;
    }


    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filteredDrugs = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    //no constraint given, just return all the data. (no search)
                    filteredDrugs.count = drugs.size();
                    filteredDrugs.values = drugs;
                } else {//do the search
                    ArrayList<DrugModel> resultsDrugs = new ArrayList<>();
                    String searchValue = constraint.toString().toUpperCase();
                    switch (filterByWarning){
                        case NONE:
                            for (DrugModel drugModel : drugs)
                                if (drugModel.getName().toUpperCase().contains(searchValue)||
                                        drugModel.getAppointment().toUpperCase().contains(searchValue))
                                    resultsDrugs.add(drugModel);
                            break;
                        case WARNING_COUNT:
                            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                            int alarmCount = sharedPreferences.getInt("AlarmCount",50);
                            for (DrugModel drugModel : drugs)
                                if (Integer.valueOf(drugModel.getCount())<alarmCount)
                                    resultsDrugs.add(drugModel);
                            break;
                        case WARNING_DATE:
                            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                            int limitDaysWarning = sharedPreferences.getInt("WarningDate",50);
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Date curentDate = new Date();
                            long dateDifference = 0;

                            for (DrugModel drugModel : drugs) {
                                Date drugDate = new Date();
                                try {
                                    drugDate = dateFormat.parse(drugModel.getDate());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                dateDifference = drugDate.getTime() - curentDate.getTime();

                                if ((int) (dateDifference / (24 * 60 * 60 * 1000)) < limitDaysWarning) {
                                    resultsDrugs.add(drugModel);
                                }
                            }
                                break;
                        case ALARM_DATE:
                            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                            int limitDaysAlarm =0;
                            SimpleDateFormat dateAlarmFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Date curentAlarmDate = new Date();
                            long dateAlarmDifference = 0;

                            for (DrugModel drugModel : drugs) {
                                Date drugDate = new Date();
                                try {
                                    drugDate = dateAlarmFormat.parse(drugModel.getDate());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                dateAlarmDifference = drugDate.getTime() - curentAlarmDate.getTime();

                                if ((int) (dateAlarmDifference / (24 * 60 * 60 * 1000)) < limitDaysAlarm) {
                                    resultsDrugs.add(drugModel);
                                }
                            }
                            break;
                    }

                    filteredDrugs.count = resultsDrugs.size();
                    filteredDrugs.values = resultsDrugs;

                }

                return filteredDrugs;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                    filteredDrugs = (ArrayList<DrugModel>) results.values;
                    notifyDataSetChanged();

                }

        };
    }

    public void getWarningDrugs(){

    }
}