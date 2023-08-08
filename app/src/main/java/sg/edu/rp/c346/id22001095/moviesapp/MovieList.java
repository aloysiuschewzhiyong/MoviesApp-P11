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
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class MovieList extends AppCompatActivity {
    ListView lvMovies;
    Button btnBack, btnFilter;
    CustomAdapter ca;
    ArrayList<Movie> movies;
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

        movies = db.getMovies();

        db.close();

        ca = new CustomAdapter(this, R.layout.row, movies);

        lvMovies.setAdapter(ca);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                DBHelper db = new DBHelper(MovieList.this);

                ArrayList <Movie> alTmp = db.getMovies();
                movies.clear();
                for (int i = 0; i < alTmp.size(); i++) {
                    Movie movie = alTmp.get(i);
                    if (movie.getRating().equals(selectedRating)) {
                        movies.add(movie);
                    }

                    //caFiltered = new CustomAdapter(MovieList.this, R.layout.row, filteredList);
                    //lvMovies.setAdapter(caFiltered);
                    ca.notifyDataSetChanged();
                }

            }
        });



    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == this.requestCode && resultCode == RESULT_OK){
            DBHelper db = new DBHelper(MovieList.this);

            movies.clear();
            movies.addAll(db.getMovies());
            db.close();
            //ca = new CustomAdapter(this, R.layout.row, movies);
            ca.notifyDataSetChanged();

        }
        super.onActivityResult(requestCode, resultCode,data);

    }
    protected void onResume() {
        super.onResume();

        //Toast.makeText(this, "ABC", Toast.LENGTH_SHORT).show();

        DBHelper db = new DBHelper(MovieList.this);

        movies.clear();
        movies.addAll(db.getMovies());
        db.close();
        //ca = new CustomAdapter(this, R.layout.row, movies);
        //lvMovies.setAdapter(ca);
        ca.notifyDataSetChanged();


    }
}