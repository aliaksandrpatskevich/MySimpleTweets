package com.codepath.apps.mysimpletweets.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

@Table(name = "tweets")
public class Tweet extends Model implements Serializable {
    // Define table fields
    @Column(name = "id_str", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private String id_str;

    @Column(name = "body")
    private String body;

    @Column(name = "uid_str", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    private String uid_str;

    @Column(name = "user")
    private User user;

    @Column(name = "createdAt")
    private String createdAt;

    @Column(name = "retweetCount")
    private String retweetCount;

    @Column(name = "favouritesCount")
    private String favouritesCount;


    public Tweet() {
        super();
    }

    // Parse model from JSON
    public Tweet(JSONObject jsonObject) {
        super();

        try {
            this.body = jsonObject.getString("text");
            this.id_str = jsonObject.getString("id_str");

            this.createdAt = jsonObject.getString("created_at");
            this.user = User.findOrCreateFromJson(jsonObject.getJSONObject("user"));
            this.uid_str = this.user.getUid_str();
            this.retweetCount = jsonObject.getString("retweet_count");
            this.favouritesCount = jsonObject.getString("favorite_count");

            this.save();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = new Tweet(tweetJson);
                if (tweet != null) {
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        return tweets;
    }

    // Getters
    public String getBody() {
        return body;
    }

    public String getUid_str() {
        return uid_str;
    }

    public User getUser() {
        return user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getRetweetCount() {
        return retweetCount;
    }

    public String getFavouritesCount() {
        return favouritesCount;
    }

    public String getId_str() {
        return id_str;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setRetweetCount(String retweetCount) {
        this.retweetCount = retweetCount;
    }

    public void setFavouritesCount(String favouritesCount) {
        this.favouritesCount = favouritesCount;
    }
}

