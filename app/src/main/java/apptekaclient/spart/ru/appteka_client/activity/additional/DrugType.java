package apptekaclient.spart.ru.appteka_client.activity.additional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;

import apptekaclient.spart.ru.appteka_client.activity.DrugListActivity;
import apptekaclient.spart.ru.appteka_client.api.model.DrugModel;
import apptekaclient.spart.ru.appteka_client.api.model.TypeModel;
import apptekaclient.spart.ru.appteka_client.requests.GetAllDrugs;
import apptekaclient.spart.ru.appteka_client.requests.GetAllTypes;

/**
 * Created by Pamela on 16.11.2019.
 */

public class DrugType {
    private String authorization = DrugListActivity.authorization;

    public ArrayList<String> getTypes () throws ExecutionException, InterruptedException {
        ArrayList<String> types = new ArrayList<>();
        GetAllTypes getAllTypes = new GetAllTypes(authorization);
        Collection<TypeModel> typeModels = getAllTypes.execute().get();

        for (TypeModel typeModel:typeModels){
            types.add(typeModel.getType());
        }

        return types;
    }

}
