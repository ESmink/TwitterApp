package ehi1vsc.saxion.twitterapp.Tweet;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Gijs on 11-5-2016.
 */
public class Entity {
    private int[] indices = new int[2];

    public Entity(JSONObject jsonObject) {
        try {
            indices[0] = jsonObject.getJSONArray("indices").getInt(0);
            indices[1] = jsonObject.getJSONArray("indices").getInt(1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public int[] getIndices() {
        return indices;
    }
}
