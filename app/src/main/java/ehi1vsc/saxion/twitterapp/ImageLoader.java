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
    Drawable image;
    ImageView view;

    @Override
    protected Object doInBackground(Object[] params) {
        String url = (String)params[0];
        view = (ImageView)params[1];
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            image = Drawable.createFromStream(is, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        view.setImageDrawable(image);
    }
}
