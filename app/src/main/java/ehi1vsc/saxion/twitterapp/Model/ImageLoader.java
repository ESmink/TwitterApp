package ehi1vsc.saxion.twitterapp.Model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import ehi1vsc.saxion.twitterapp.R;

/**
 * Created by edwin_000 on 11/05/2016.
 */
public class ImageLoader extends AsyncTask<Object, Object, Drawable> {
    private Object parent;
    private ImageView view;

    @Override
    protected Drawable doInBackground(Object[] params) {
        String url = (String) params[0];
        parent = params[1];
        view = (ImageView) params[2];
        Context context = (Context) params[3];
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            return Drawable.createFromStream(is, "");
        } catch (FileNotFoundException e) {
            if (parent instanceof User) {
                return context.getResources().getDrawable(R.drawable.crashphoto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Drawable o) {
        if (o != null) {
            if (parent instanceof User) {
                ((User) parent).setProfile_image(o);
            } else {
                ((Tweet) parent).setTweet_image(o);
            }
            view.setImageDrawable(o);
        }
    }
}
