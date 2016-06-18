package ehi1vsc.saxion.twitterapp;

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

import ehi1vsc.saxion.twitterapp.Tweet.Tweet;

/**
 * Created by edwin_000 on 11/05/2016.
 */
public class TweetAdapter extends ArrayAdapter<Tweet> {

    public TweetAdapter(Context context, List<Tweet> objects) {
        super(context, R.layout.tweetlayout, objects);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Service.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.tweetlayout, parent, false);
        }

        TextView text = (TextView) view.findViewById(R.id.TweetTextTV);
        text.setText(Html.fromHtml(getItem(position).getText()));
        text.setAutoLinkMask(Linkify.WEB_URLS);

        ((TextView) view.findViewById(R.id.TweetDisplayTV)).setText(getItem(position).getUser().getScreen_name());
        ((TextView) view.findViewById(R.id.TweetNameTV)).setText("@" + getItem(position).getUser().getName());

        ImageView imageView = (ImageView) view.findViewById(R.id.TweetAvatarIV);
        imageView.setImageDrawable(getItem(position).getUser().getProfile_image(imageView, getContext()));
        final User user = getItem(position).getUser();
        if (Ref.accessToken != null) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), UserActivity.class);
                    intent.putExtra("logUser", Model.getInstance().users.indexOf(user));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);
                }
            });
        }

        return view;
    }
}
