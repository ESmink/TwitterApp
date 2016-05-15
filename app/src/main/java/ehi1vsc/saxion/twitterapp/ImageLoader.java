package ehi1vsc.saxion.twitterapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by edwin_000 on 11/05/2016.
 */
public class ImageLoader extends AsyncTask {
    private Drawable image;
    private User user;
    private ImageView view;
    private Context context;

    @Override
    protected Object doInBackground(Object[] params) {
        String url = (String) params[0];
        user = (User) params[1];
        view = (ImageView) params[2];
        context = (Context) params[3];
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            image = Drawable.createFromStream(is, "");
        } catch (FileNotFoundException e) {
            image = context.getResources().getDrawable(R.drawable.crashphoto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        user.setProfile_image(image);
        view.setImageDrawable(image);
    }
}
