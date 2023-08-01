package sg.edu.rp.c346.id22001095.moviesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Movie> {
    Context context;
    ArrayList<Movie>al;
    int resource;

    public CustomAdapter(Context context, int resource, ArrayList<Movie> al) {
        super(context, resource, al);
        this.context = context;
        this.resource = resource;
        this.al = al;
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(resource, parent, false);

        TextView tvName = rowView.findViewById(R.id.tvTitle);
        TextView tvYear = rowView.findViewById(R.id.tvYear);
        TextView tvGenre = rowView.findViewById(R.id.tvGenre);
        ImageView ivRating = rowView.findViewById(R.id.imageView);

        Movie currentMovie = al.get(position);
        String rating = currentMovie.getRating();

        tvName.setText(currentMovie.getTitle());
        tvYear.setText(String.valueOf(currentMovie.getYear()));
        tvGenre.setText(currentMovie.getGenre());



        if ("G".equals(rating)) {
            ivRating.setImageResource(R.drawable.rating_g);
        } else if ("PG".equals(rating)) {
            String imageUrl = "https://images.immediate.co.uk/production/volatile/sites/28/2019/02/16278-28797ce.jpg?quality=90&webp=true&fit=584,471";
            Picasso.with(context).load(imageUrl).into(ivRating);
            //ik this is abit scuffed but almost 2359 so i no time
        } else if ("PG13".equals(rating)) {
            ivRating.setImageResource(R.drawable.rating_pg13);
        } else if ("NC16".equals(rating)) {
            ivRating.setImageResource(R.drawable.rating_nc16);
        } else if ("M18".equals(rating)) {
            String imageUrl = "https://images.immediate.co.uk/production/volatile/sites/28/2019/02/16282-05127b2.jpg?quality=90&webp=true&fit=300,300";
            Picasso.with(context).load(imageUrl).into(ivRating);
        } else if ("R21".equals(rating)) {
            ivRating.setImageResource(R.drawable.rating_r21);
        }


        return rowView;
    }
}
