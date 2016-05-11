package ehi1vsc.saxion.twitterapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Gijs on 11-5-2016.
 */
public class User {
    private String screen_name, name, id_str;
    private int[] indices = new int[2];

    public User(JSONObject jsonObject, JSONArray jsonArray) {
        try {
            this.screen_name = jsonObject.getString("screen_name");
            this.name = jsonObject.getString("name");
            this.id_str = jsonObject.getString("id_str");
            this.indices[0] = jsonArray.getInt(0);
            this.indices[1] = jsonArray.getInt(1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
