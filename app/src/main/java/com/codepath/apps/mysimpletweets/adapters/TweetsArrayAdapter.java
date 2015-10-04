package com.codepath.apps.mysimpletweets.adapters;

import android.content.Context;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.LinkifiedTextView;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import org.apache.http.ParseException;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    public TweetsArrayAdapter(Context context, List<Tweet> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Tweet tweet = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }
        // Lookup view for data population
        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvScreenName = (TextView) convertView.findViewById(R.id.tvScreenName);
        LinkifiedTextView tvBody = (LinkifiedTextView) convertView.findViewById(R.id.tvBody);
        TextView tvCreatedAt = (TextView) convertView.findViewById(R.id.tvCreatedAt);
        TextView tvRetweetCount = (TextView) convertView.findViewById(R.id.tvRetweetCount);
        TextView tvFavouritesCount = (TextView) convertView.findViewById(R.id.tvFavouritesCount);

        // Populate the data into the template view using the data object
        tvUserName.setText(tweet.getUser().getName());
        tvScreenName.setText(" @" + tweet.getUser().getScreenName());

        tvBody.setText(Html.fromHtml(tweet.getBody()), TextView.BufferType.SPANNABLE);
        tvCreatedAt.setText(getRelativeTimeAgo(tweet.getCreatedAt()));
        tvRetweetCount.setText("✒ " + tweet.getRetweetCount());
        tvFavouritesCount.setText("✫ " + tweet.getFavouritesCount());

        ivProfileImage.setImageResource(0); //reset

        //        insert image using picasso
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl())
                .into(ivProfileImage);

        // Return the completed view to render on screen
        return convertView;
    }

    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = 0;
            try {
                dateMillis = sf.parse(rawJsonDate).getTime();
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.FORMAT_ABBREV_ALL).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}
