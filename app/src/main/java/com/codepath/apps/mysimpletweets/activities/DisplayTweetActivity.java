package com.codepath.apps.mysimpletweets.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class DisplayTweetActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivProfileImage;
    private TextView tvUserName;
    private TextView tvScreenName;
    private TextView tvBody;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_tweet);

        Tweet result = (Tweet) getIntent().getSerializableExtra("result");
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvScreenName = (TextView) findViewById(R.id.tvScreenName);

        tvBody = (TextView) findViewById(R.id.tvBody);
        tvBody.setText(result.getBody());
        tvUserName.setText(result.getUser().getName());
        tvScreenName.setText(" @" + result.getUser().getScreenName());


        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(10)
                .oval(false)
                .build();
        Picasso.with(this).load(result.getUser().getProfileImageUrl())
                .transform(transformation)
                .into(ivProfileImage);

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.btnOk:
////                 // Prepare data intent
//                Intent data = new Intent();
//                // Pass relevant data back as a result
//                data.putExtra("name", etName.getText().toString());
//                data.putExtra("code", 200); // ints work too
//                // Activity finished ok, return the data
//                setResult(RESULT_OK, data); // set result code and bundle data for response
//                finish(); // closes the activity, pass data to parent

            case R.id.btnCancel:
                this.finish();
        }
    }
}
