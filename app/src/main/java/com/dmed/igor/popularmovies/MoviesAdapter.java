package com.dmed.igor.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import static com.dmed.igor.popularmovies.utilities.NetworkUtils.MOVIEDB_IMAGE_BASE_URL;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder> {

    private String[] mMoviesData;

    private final GridItemClickListener mOnClickListener;

    public interface GridItemClickListener {
        void onGridItemClick(int clickedItemIndex);
    }

    public MoviesAdapter (GridItemClickListener listener) {
        mOnClickListener = listener;
    }

    @Override
    public MoviesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForGridItem = R.layout.movie_grid_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForGridItem, parent, shouldAttachToParentImmediately);
        return new MoviesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesAdapterViewHolder holder, int position) {
        String moviePosterId = mMoviesData[position];

        Picasso.with(holder.mMovieImageView.getContext())
                .load(MOVIEDB_IMAGE_BASE_URL + moviePosterId)
                .into(holder.mMovieImageView);
    }

    @Override
    public int getItemCount() {
        if (null == mMoviesData) return 0;
        return mMoviesData.length;
    }

    public class MoviesAdapterViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener {
        ImageView mMovieImageView;

        public MoviesAdapterViewHolder(View itemView) {
            super(itemView);
            mMovieImageView = itemView.findViewById(R.id.iv_movie_data);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onGridItemClick(clickedPosition);
        }
    }

    public void setMoviesData(String[] moviesData) {
        mMoviesData = moviesData;
        notifyDataSetChanged();
    }
}
