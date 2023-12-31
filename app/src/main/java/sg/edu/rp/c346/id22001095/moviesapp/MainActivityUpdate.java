package sg.edu.rp.c346.id22001095.moviesapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amrdeveloper.lottiedialog.LottieDialog;

public class MainActivityUpdate extends AppCompatActivity {

    EditText etID, etTitle, etGenre, etYear;
    Spinner spinner;
    Button btnUpdate, btnDelete, btnCancel;
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_update);

        etID = findViewById(R.id.editTextId);
        etTitle = findViewById(R.id.editTextTitle);
        etGenre = findViewById(R.id.editTextGenre);
        etYear = findViewById(R.id.editTextYear);
        spinner= findViewById(R.id.spinner);
        btnUpdate = findViewById(R.id.buttonUpdate);
        btnDelete = findViewById(R.id.buttonDelete);
        btnCancel = findViewById(R.id.buttonCancel);

        Intent i = getIntent();
        movie = (Movie) i.getSerializableExtra("movie");
        etID.setText(String.valueOf(movie.getId()));
        etTitle.setText(movie.getTitle());
        etGenre.setText(movie.getGenre());
        etYear.setText(String.valueOf(movie.getYear()));

        String rating = movie.getRating();

        if ("G".equals(rating)) {
            spinner.setSelection(0);
        } else if ("PG".equals(rating)) {
            spinner.setSelection(1);
        } else if ("PG13".equals(rating)) {
            spinner.setSelection(2);
        } else if ("NC16".equals(rating)) {
            spinner.setSelection(3);
        } else if ("M18".equals(rating)) {
            spinner.setSelection(4);
        } else if ("R21".equals(rating)) {
            spinner.setSelection(5);
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DBHelper dbh = new DBHelper(MainActivityUpdate.this);

                movie.setId(Integer.parseInt(etID.getText().toString()));
                movie.setTitle(etTitle.getText().toString());
                movie.setGenre(etGenre.getText().toString());
                movie.setYear(Integer.parseInt(etYear.getText().toString()));
                movie.setRating(spinner.getSelectedItem().toString());

                if(dbh.updateMovie(movie) >0){
                    Toast.makeText(MainActivityUpdate.this, "Updated", Toast.LENGTH_LONG).show();
                    setResult(RESULT_OK);
                }else{
                    Toast.makeText(MainActivityUpdate.this,"Failed", Toast.LENGTH_LONG).show();
                }
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLottieDialog();


            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showLottieDialog2();



            }
        });

    }



    private void showLottieDialog() {
        Button okButton = new Button(this);
        okButton.setText("Delete");
        okButton.setOnClickListener(view ->{DBHelper dbh = new DBHelper(MainActivityUpdate.this);
                    dbh.deleteMovie(movie.getId());
                    finish();});



        LottieDialog dialog = new LottieDialog(this)
                .setAnimation(R.raw.animation2)
                .setAnimationRepeatCount(LottieDialog.INFINITE)
                .setAutoPlayAnimation(true)
                .setTitle("Danger")
                .setTitleColor(Color.BLACK)
                .setMessage("Do you want to discard changes?")
                .setMessageColor(Color.BLACK)
                .setDialogBackground(Color.WHITE)
                .setCancelable(true)
                .addActionButton(okButton)
                .setOnShowListener(dialogInterface -> {})
                .setOnDismissListener(dialogInterface -> {})
                .setOnCancelListener(dialogInterface -> {});
        Button cancelButton = new Button(this);
        cancelButton.setText("Cancel");
        cancelButton.setOnClickListener(view -> dialog.dismiss());
        dialog.addActionButton(cancelButton);
        dialog.show();
    }
    private void showLottieDialog2() {
        Button okButton = new Button(this);
        okButton.setText("Discard");
        okButton.setOnClickListener(view -> finish());



        LottieDialog dialog = new LottieDialog(this)
                .setAnimation(R.raw.animation)
                .setAnimationRepeatCount(LottieDialog.INFINITE)
                .setAutoPlayAnimation(true)
                .setTitle("Danger")
                .setTitleColor(Color.BLACK)
                .setMessage("Do you want to discard changes?")
                .setMessageColor(Color.BLACK)
                .setDialogBackground(Color.WHITE)
                .setCancelable(true)
                .addActionButton(okButton)
                .setOnShowListener(dialogInterface -> {})
                .setOnDismissListener(dialogInterface -> {})
                .setOnCancelListener(dialogInterface -> {});

        Button cancelButton = new Button(this);
        cancelButton.setText("Cancel");
        cancelButton.setOnClickListener(view -> dialog.dismiss());
        dialog.addActionButton(cancelButton);

        dialog.show();
    }
}