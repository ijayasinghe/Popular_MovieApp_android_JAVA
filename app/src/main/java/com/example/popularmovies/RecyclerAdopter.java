package com.example.popularmovies;

import android.content.Context;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RecyclerAdopter extends RecyclerView.Adapter<RecyclerAdopter.ViewHolder> {

    private ArrayList<Movie> movieList;
    private Context context;

    public RecyclerAdopter(Context context, ArrayList<Movie> movies){
        this.context= context;
        movieList= movies;
    }

    @NonNull
    @Override
    public RecyclerAdopter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //add card layout to the recycler view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdopter.ViewHolder holder, int position) {

        Movie movie = movieList.get(position);
        holder.rating.setText((movie.getRating()));
        holder.title.setText(movie.getTitle());
        holder.overview.setText((movie.getOverview()));
        Picasso.get().load(movie.getPoster()).into(holder.image);

        //create a click event listner for a cardview so when we click it, it will go to detailActivity
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);

                Bundle b = new Bundle();

                b.putString("title", movie.getTitle());
                b.putString("rating", movie.getRating());
                b.putString("overview", movie.getOverview());
                b.putString("poster", movie.getPoster());

                intent.putExtras(b);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView image;
        TextView title;
        TextView rating;
        TextView overview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image= itemView.findViewById(R.id.movie_image);
            title=itemView.findViewById(R.id.movie_title);
            rating = itemView.findViewById(R.id.movie_rating);
            overview=itemView.findViewById(R.id.overview);

            //this to use when selecting a speicfic cardview
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}
