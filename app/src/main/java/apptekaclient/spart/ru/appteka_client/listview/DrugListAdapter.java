package apptekaclient.spart.ru.appteka_client.listview;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import apptekaclient.spart.ru.appteka_client.R;
import apptekaclient.spart.ru.appteka_client.listview.model.DrugListViewModel;

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
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.drug_list_view_model, parent, false);
        }

        DrugListViewModel drug = getDrug(position);

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        Date date = drug.getDate();
        SimpleDateFormat formatForDate = new SimpleDateFormat("dd.MM.yyyy");


        ((TextView) view.findViewById(R.id.drugName)).setText(String.valueOf(drug.getId())+" ("+drug.getType()+")");
        ((TextView) view.findViewById(R.id.drugCount)).setText(drug.getCount());
        ((TextView) view.findViewById(R.id.drugAppointment)).setText(drug.getAppointment());
        ((TextView) view.findViewById(R.id.drugDate)).setText(formatForDate.format(date));

        CheckBox cbBuy = (CheckBox) view.findViewById(R.id.cbBox);
        // присваиваем чекбоксу обработчик
        cbBuy.setOnCheckedChangeListener(myCheckChangeList);
        // пишем позицию
        cbBuy.setTag(position);
        // заполняем данными из товаров: в корзине или нет
        //cbBuy.setChecked(drug.box);
        return view;
    }

    // товар по позиции
    DrugListViewModel getDrug(int position) {
        return ((DrugListViewModel) getItem(position));
    }

    // содержимое корзины
//    ArrayList<Product> getBox() {
//        ArrayList<Product> box = new ArrayList<Product>();
//        for (Product p : objects) {
//            // если в корзине
//            if (p.box)
//                box.add(p);
//        }
//        return box;
//    }

    // обработчик для чекбоксов
    OnCheckedChangeListener myCheckChangeList = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // меняем данные товара (в корзине или нет)
           // getDrug((Integer) buttonView.getTag()).box = isChecked;
        }
    };
}