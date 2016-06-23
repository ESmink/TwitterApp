package ehi1vsc.saxion.twitterapp.Model;

import java.util.List;

/**
 * Created by edwin_000 on 15/05/2016.
 */
public class Htmltext {
    private String text;
    private int start, end;
    //a simple class for handling the adding of html to a text

    public Htmltext(String text, int start, int end) {
        this.text = text;
        this.start = start;
        this.end = end;
    }

    public String insert(String text) {
        return text.substring(0, start) + this.text + text.substring(end);
    }

    //this method adds the class to a list of its type.
    // it always attempts to sort itself on farthest start-value
    public void addToList(List<Htmltext> list) {
        int current = 0;
        for (Htmltext other : list) {
            if (other.start < this.start) {
                list.add(current, this);
                return;
            }
            current++;
        }
        list.add(this);
    }
}
