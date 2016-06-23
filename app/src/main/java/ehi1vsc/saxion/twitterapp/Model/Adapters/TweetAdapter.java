package ehi1vsc.saxion.twitterapp.Model.Adapters;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ehi1vsc.saxion.twitterapp.Model.Model;
import ehi1vsc.saxion.twitterapp.Model.Ref;
import ehi1vsc.saxion.twitterapp.Model.Tweet;
import ehi1vsc.saxion.twitterapp.Model.User;
import ehi1vsc.saxion.twitterapp.R;
import ehi1vsc.saxion.twitterapp.Activities.UserActivity;

/**
 * Created by edwin_000 on 11/05/2016.
 */
public class TweetAdapter extends ArrayAdapter<Tweet> {

    public TweetAdapter(Context context, List<Tweet> objects) {
        super(context, R.layout.tweetlayout, objects);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        //needs to reinflate all layouts every time, as to not confuse the asyncImageLoading
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.tweetlayout, parent, false);
        final User user = getItem(position).getUser();

        //makes sure weblinks are clickable
        TextView text = (TextView) view.findViewById(R.id.TweetTextTV);
        text.setText(Html.fromHtml(getItem(position).getText()));
        text.setAutoLinkMask(Linkify.WEB_URLS);

        ((TextView) view.findViewById(R.id.TweetDisplayTV)).setText(user.getScreen_name());
        ((TextView) view.findViewById(R.id.TweetNameTV)).setText("@" + user.getName());

        //gives the imageview to the asynctask to load an image if it is not already loaded
        ImageView imageView = (ImageView) view.findViewById(R.id.TweetAvatarIV);
        imageView.setImageDrawable(user.getProfile_image(imageView, getContext()));

        //adds an option to visit someone's profile by clicking their image
        if (Ref.accessToken != null) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), UserActivity.class);
                    intent.putExtra("logUser", user.getId_str());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);
                }
            });
        }

        //asynctask loading of tweet media.
        ImageView tweetImage = (ImageView) view.findViewById(R.id.TweetIV);
        tweetImage.setImageDrawable(getItem(position).getTweet_image(tweetImage, getContext()));

        return view;
    }
}
