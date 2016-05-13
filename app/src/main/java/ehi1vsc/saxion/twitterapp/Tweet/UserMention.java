package ehi1vsc.saxion.twitterapp.Tweet;

import org.json.JSONObject;

import ehi1vsc.saxion.twitterapp.User;

/**
 * Created by Gijs on 11-5-2016.
 */
public class UserMention extends Entity {
    private User user;

    public UserMention(JSONObject jsonObject) {
        super(jsonObject);
    }
}
