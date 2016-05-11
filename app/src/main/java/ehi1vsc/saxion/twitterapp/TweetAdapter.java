package ehi1vsc.saxion.twitterapp;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by edwin_000 on 11/05/2016.
 */
public class TweetAdapter extends ArrayAdapter<Tweet>{

    public TweetAdapter(Context context, List<Tweet> objects) {
        super(context, R.layout.tweetlayout, objects);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(view == null){
            LayoutInflater inflater = (LayoutInflater)getContext()
                    .getSystemService(Service.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.tweetlayout, parent, false);
        }
        //edit empty layout



        return view;
    }
}
