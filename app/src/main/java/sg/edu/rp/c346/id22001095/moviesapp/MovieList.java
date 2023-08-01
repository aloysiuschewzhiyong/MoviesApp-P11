package sg.edu.rp.c346.id22001095.moviesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class MovieList extends AppCompatActivity {
    ListView lvMovies;
    Button btnBack, btnFilter;
    CustomAdapter ca, caFiltered;
    Spinner spinner;
    int requestCode = 9;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        lvMovies = findViewById(R.id.lvMovies);
        btnFilter = findViewById(R.id.buttonFilter);
        btnBack = findViewById(R.id.buttonBack);
        spinner= findViewById(R.id.spinner);

        DBHelper db = new DBHelper(MovieList.this);

        ArrayList<Movie> movies = db.getMovies();

        db.close();

        ca = new CustomAdapter(this, R.layout.row, movies);

        lvMovies.setAdapter(ca);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MovieList.this, MainActivity.class);
                startActivity(intent);
            }
        });

        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long identity) {
                //Movie movie = movies.get(position);
                Intent intent = new Intent(MovieList.this, MainActivityUpdate.class);
                intent.putExtra("movie", movies.get(i));
                startActivityForResult(intent, requestCode);

            }
        });

        this.btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String selectedRating = spinner.getSelectedItem().toString();

                ArrayList<Movie> filteredList = new ArrayList<>();
                for (int i = 0; i < movies.size(); i++) {
                    Movie movie = movies.get(i);
                    if (movie.getRating().equals(selectedRating)) {
                        filteredList.add(movie);
                    }

                    caFiltered = new CustomAdapter(MovieList.this, R.layout.row, filteredList);
                    lvMovies.setAdapter(caFiltered);
                }

            }
        });



    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == this.requestCode && resultCode == RESULT_OK){
            DBHelper db = new DBHelper(MovieList.this);

            ArrayList<Movie> movies = db.getMovies();

            movies.clear();
            movies.addAll(db.getMovies());
            db.close();
            ca = new CustomAdapter(this, R.layout.row, movies);
            ca.notifyDataSetChanged();

        }
        super.onActivityResult(requestCode, resultCode,data);

    }
    protected void onResume() {
        super.onResume();

        DBHelper db = new DBHelper(MovieList.this);

        ArrayList<Movie> movies = db.getMovies();

        movies.clear();
        movies.addAll(db.getMovies());
        db.close();
        ca = new CustomAdapter(this, R.layout.row, movies);
        lvMovies.setAdapter(ca);
        ca.notifyDataSetChanged();


    }
}