package ehi1vsc.saxion.twitterapp;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by edwin_000 on 11/05/2016.
 */
public class ImageLoader extends AsyncTask {

    @Override
    protected Object doInBackground(Object[] params) {
        String url = (String)params[0];
        ImageView view = (ImageView)params[1];
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            view.setImageDrawable(Drawable.createFromStream(is, ""));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
