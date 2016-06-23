package ehi1vsc.saxion.twitterapp.Model.Adapters;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ehi1vsc.saxion.twitterapp.Model.Ref;
import ehi1vsc.saxion.twitterapp.Model.User;
import ehi1vsc.saxion.twitterapp.R;
import ehi1vsc.saxion.twitterapp.Activities.UserActivity;

/**
 * Created by edwin_000 on 22/06/2016.
 */
public class UserAdapter extends ArrayAdapter<User> {
    //this class is a copy of the TweetAdapter with the tweet-part Stripped out.

    public UserAdapter(Context context, List<User> objects) {
        //the tweetlayout is being reused.
        super(context, R.layout.tweetlayout, objects);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final User user = getItem(position);

        //needs to reinflate all layouts every time, as to not confuse the asyncImageLoading
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.tweetlayout, parent, false);

        ((TextView) view.findViewById(R.id.TweetDisplayTV)).setText(user.getScreen_name());
        ((TextView) view.findViewById(R.id.TweetNameTV)).setText("@" + user.getName());

        ImageView imageView = (ImageView) view.findViewById(R.id.TweetAvatarIV);
        imageView.setImageDrawable(user.getProfile_image(imageView, getContext()));

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
        return view;
    }
}
