package ehi1vsc.saxion.twitterapp.Tweet;

/**
 * Created by Gijs on 11-5-2016.
 */
public class Hashtag extends Entiteit {
    private String text;

    public Hashtag(int start, int eind, String text) {
        super(start, eind);
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
