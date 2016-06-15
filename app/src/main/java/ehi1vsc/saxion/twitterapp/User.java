package ehi1vsc.saxion.twitterapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Gijs on 11-5-2016.
 */
public class User {
    private String screen_name, name, id_str, profile_image_url, followers_count, friends_count, description;
    private Drawable profile_image;

    public User(JSONObject jsonObject) {
        try {
            screen_name = jsonObject.getString("screen_name");
            name = jsonObject.getString("name");
            id_str = jsonObject.getString("id_str");
            followers_count = jsonObject.getString("followers_count");
            friends_count = jsonObject.getString("friends_count");
            description = jsonObject.getString("description");
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

    public Drawable getProfile_image(ImageView image, Context context) {
        if(profile_image == null){
            ImageLoader loader = new ImageLoader();
            loader.execute(profile_image_url, this, image, context);
        }
        return profile_image;
    }

    public void setProfile_image(Drawable profile_image) {
        this.profile_image = profile_image;
    }

    public String getFollowers_count() {
        return followers_count;
    }

    public String getFriends_count() {
        return friends_count;
    }

    public String getDescription(){
        return description;
    }
}
