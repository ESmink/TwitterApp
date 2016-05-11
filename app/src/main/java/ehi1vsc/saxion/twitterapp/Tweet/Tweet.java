package ehi1vsc.saxion.twitterapp.Tweet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by edwin_000 on 25/04/2016.
 */
public class Tweet {
    private String id_str, text;
    private ArrayList<JSONObject> entities = new ArrayList<>();

    public Tweet(JSONObject jsonObject) {
        try {
            this.id_str = jsonObject.getString("id_str");
            this.text =  jsonObject.getString("text");

            for (int i = 0; i < jsonObject.getJSONArray("entities").length(); i++) {
                entities.add(jsonObject.getJSONArray("entities").getJSONObject(i));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getId_str() {
        return id_str;
    }

    public String getText() {
        return text;
    }

    public ArrayList<JSONObject> getEntities() {
        return entities;
    }
}
