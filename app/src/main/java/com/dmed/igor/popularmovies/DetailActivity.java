package com.dmed.igor.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import static com.dmed.igor.popularmovies.utilities.NetworkUtils.MOVIEDB_IMAGE_BASE_URL;

public class DetailActivity extends AppCompatActivity {

    TextView mTitleTextView;

    ImageView mPosterImageView;

    TextView mSynopsisTextView;

    TextView mRatingTextView;

    TextView mReleaseDateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mTitleTextView = (TextView) findViewById(R.id.tv_movie_title);

        mPosterImageView = (ImageView) findViewById(R.id.iv_detail_poster);

        mSynopsisTextView = (TextView) findViewById(R.id.tv_movie_synopsis);

        mRatingTextView = (TextView) findViewById(R.id.tv_movie_rating);

        mReleaseDateTextView = (TextView) findViewById(R.id.tv_movie_release_date);

        if (getIntent().hasExtra("DETAIL_JSON")) {
            try {
                JSONObject jsonObj = new JSONObject(getIntent().getStringExtra("DETAIL_JSON"));

                mTitleTextView.setText(jsonObj.getString("original_title"));

                Picasso.with(this)
                        .load(MOVIEDB_IMAGE_BASE_URL + jsonObj.getString("poster_path"))
                        .into(mPosterImageView);

                mSynopsisTextView.setText(jsonObj.getString("overview"));

                mRatingTextView.setText(jsonObj.getString("vote_average"));

                mReleaseDateTextView.setText(jsonObj.getString("release_date"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
