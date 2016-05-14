package ehi1vsc.saxion.twitterapp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Gijs on 11-5-2016.
 */
public class User {
    private String screen_name, name, id_str, profile_image_url;

    public User(JSONObject jsonObject) {
        try {
            screen_name = jsonObject.getString("screen_name");
            name = jsonObject.getString("name");
            id_str = jsonObject.getString("id_str");
            profile_image_url = jsonObject.getString("profile_image_url");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getScreen_name() {
        return screen_name;
    }

    public String getName() {
        return name;
    }

    public String getId_str() {
        return id_str;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }
}
