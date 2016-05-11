package ehi1vsc.saxion.twitterapp.Tweet;

/**
 * Created by Gijs on 11-5-2016.
 */
public class Entity {
    private int[] indices = new int[2];

    public Entity(int start, int eind) {
        this.indices[0] = start;
        this.indices[1] = eind;
    }

    public int[] getIndices() {
        return indices;
    }
}
