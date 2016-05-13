package ehi1vsc.saxion.twitterapp.Tweet;

import ehi1vsc.saxion.twitterapp.User;

/**
 * Created by Gijs on 11-5-2016.
 */
public class UserMention extends Entity {
    private User user;

    public UserMention(int start, int eind) {
        super(start, eind);
    }
}
