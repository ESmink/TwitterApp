package ehi1vsc.saxion.twitterapp.Tweet;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ehi1vsc.saxion.twitterapp.ImageLoader;
import ehi1vsc.saxion.twitterapp.Model;
import ehi1vsc.saxion.twitterapp.User;

/**
 * Created by edwin_000 on 25/04/2016.
 */
public class Tweet {
    private String id_str, text, image_url;
    private User user;
    private Drawable tweet_image;

    public Tweet(JSONObject jsonObject) {
        try {
            //filling Tweet
            this.id_str = jsonObject.getString("id_str");
            this.text = jsonObject.getString("text");

            ArrayList<Htmltext> htmlList = new ArrayList<>();
            JSONArray entity;

            //managing Hashtags
            entity = jsonObject.getJSONObject("entities").getJSONArray("hashtags");
            for (int x = 0; entity.length() > x; x++) {
                JSONArray indices = entity.getJSONObject(x).getJSONArray("indices");
                // makes new Htmltext, then puts it in the list.
                new Htmltext("<font color='blue'>#" + entity.getJSONObject(x).getString("text") +
                        "</font>", indices.getInt(0), indices.getInt(1)).addToList(htmlList);
            }

            //managing Usermentions
            entity = jsonObject.getJSONObject("entities").getJSONArray("user_mentions");
            for (int x = 0; entity.length() > x; x++) {
                JSONArray indices = entity.getJSONObject(x).getJSONArray("indices");

                new Htmltext("<font color='green'>@" + entity.getJSONObject(x).getString("screen_name") +
                        "</font>", indices.getInt(0), indices.getInt(1)).addToList(htmlList);
            }
            //managing user
            user = Model.getInstance().addUser(new User(jsonObject.getJSONObject("user")));

            //managing media
            entity = jsonObject.getJSONObject("entities").getJSONArray("media");
            if (entity.length() > 0) {
                image_url = entity.getJSONObject(0).getString("media_url_https");
            }

            //merging text with Htmltexts
            for (Htmltext html : htmlList) {
                text = html.insert(text);
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

    public User getUser() {
        return user;
    }

    public Drawable getTweet_image(ImageView image, Context context) {
        if (image_url != null && tweet_image == null) {
            ImageLoader loader = new ImageLoader();
            loader.execute(image_url, this, image, context);
        }
        return tweet_image;
    }

    public void setTweet_image(Drawable tweet_image) {
        this.tweet_image = tweet_image;
    }
}
