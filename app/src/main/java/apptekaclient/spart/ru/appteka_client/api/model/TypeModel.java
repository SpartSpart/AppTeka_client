package apptekaclient.spart.ru.appteka_client.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Pamela on 16.11.2019.
 */

public class TypeModel implements Serializable {

    @SerializedName("type")
    @Expose
    private String type;

    public TypeModel(String type) {
        this.type = type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;

    }
}
