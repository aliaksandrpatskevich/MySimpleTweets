package com.codepath.apps.mysimpletweets;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.models.User;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class ComposeDialog extends DialogFragment
        implements Button.OnClickListener {

    private TextView tvLeft;
    private EditText tvBody;
    private Button btnOk;

    public interface ComposeDialogListener {
        void onFinishComposeDialogListener(String tweet);
    }

    public ComposeDialog() {
        // Empty constructor required for DialogFragment
    }

    public static ComposeDialog newInstance(User user) {
        ComposeDialog frag = new ComposeDialog();
        Bundle args = new Bundle();
        args.putString("name", user.getName());
        args.putString("screenName", user.getScreenName());
        args.putString("profileImageUrl", user.getProfileImageUrl());
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compose, container);

        ImageView ivProfileImage = (ImageView) view.findViewById(R.id.ivProfileImage);
        TextView tvUserName = (TextView) view.findViewById(R.id.tvUserName);
        TextView tvScreenName = (TextView) view.findViewById(R.id.tvScreenName);
        tvLeft = (TextView) view.findViewById(R.id.tvLeft);
        tvBody = (EditText) view.findViewById(R.id.tvBody);
        tvUserName.setText(getArguments().getString("name", ""));
        tvScreenName.setText(" @" + getArguments().getString("screenName", ""));
        tvLeft.setText("140 characters left");

        btnOk = (Button) view.findViewById(R.id.btnOk);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);


        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(10)
                .oval(false)
                .build();
        Picasso.with(getContext()).load(getArguments().getString("profileImageUrl", ""))
                .transform(transformation)
                .into(ivProfileImage);

        tvBody.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int left = 140 - s.length();
                if (left > 0) {
                    tvLeft.setText(left + " characters left");
                    btnOk.setEnabled(true);
                } else {
                    tvLeft.setText("Message is too long");
                    btnOk.setEnabled(false);
                }
            }
        });

        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOk:
                ComposeDialogListener listener = (ComposeDialogListener) getActivity();
                listener.onFinishComposeDialogListener(tvBody.getText().toString());

            case R.id.btnCancel:
                dismiss();
                break;
        }
    }
}
