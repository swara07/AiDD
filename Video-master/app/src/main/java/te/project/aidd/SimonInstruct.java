package te.project.aidd;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SimonInstruct extends AppCompatActivity {
    Button flipinstrut,tutorial;
    private static final String SET_OF_GAMES="set_of_games_simon";
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String OLD_DATE3 ="04/04/2020";
    int decision=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simon_instuct);
        flipinstrut=(Button) findViewById(R.id.flipin);
        tutorial=(Button) findViewById(R.id.tutorial_simon);


        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        final String formattedDate = df.format(c);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String dd=sharedPreferences.getString(OLD_DATE3," ");

        final int diff=getDateDiffFromNow(sharedPreferences.getString(OLD_DATE3," "));
        System.out.println(diff);
        System.out.println("no of games played"+ sharedPreferences.getInt(SET_OF_GAMES,-1));
        if(diff>0 && sharedPreferences.getInt(SET_OF_GAMES,0)==2){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(SET_OF_GAMES,0);
            editor.apply();

        }

        flipinstrut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                if(diff>0 && sharedPreferences.getInt(SET_OF_GAMES,0)==0){

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(OLD_DATE3 ,formattedDate );
                    editor.putInt(SET_OF_GAMES,1);
                    editor.apply();
                    //Toast.makeText(SimonInstruct.this, "Date saved", Toast.LENGTH_SHORT).show();
                    Intent cmin=new Intent(SimonInstruct.this, SimonGame.class);
                    cmin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(cmin);


                    finish();

                }
                else if(diff>0 && sharedPreferences.getInt(SET_OF_GAMES,0)==1){

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(OLD_DATE3 ,formattedDate );
                    editor.putInt(SET_OF_GAMES,2);
                    editor.apply();
                    //Toast.makeText(SimonInstruct.this, "Date saved", Toast.LENGTH_SHORT).show();
                    Intent cmin=new Intent(SimonInstruct.this, SimonGame.class);
                    cmin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(cmin);


                    finish();

                }
                else if(diff==0 && sharedPreferences.getInt(SET_OF_GAMES,0)==2){
                    System.out.println("nooo cant playyy");
                    System.out.println("cant ");

                    AlertDialog.Builder builder=new AlertDialog.Builder(SimonInstruct.this);
                    builder.setMessage("You have completed your exercises for the day");
                    builder.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.setCancelable(false);
                    AlertDialog alertDialog=builder.create();
                    alertDialog.show();
                }


                else  if(diff==0 && sharedPreferences.getInt(SET_OF_GAMES,0)==0){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(OLD_DATE3 ,formattedDate );
                    editor.putInt(SET_OF_GAMES,1);
                    editor.apply();
                    //Toast.makeText(SimonInstruct.this, "Date saved", Toast.LENGTH_SHORT).show();
                    Intent cmin=new Intent(SimonInstruct.this, SimonGame.class);
                    cmin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(cmin);


                    finish();

                }
                else  if(diff==0 && sharedPreferences.getInt(SET_OF_GAMES,0)==1){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(OLD_DATE3 ,formattedDate );
                    editor.putInt(SET_OF_GAMES,2);
                    editor.apply();
                    //Toast.makeText(SimonInstruct.this, "Date saved", Toast.LENGTH_SHORT).show();
                    Intent cmin=new Intent(SimonInstruct.this, SimonGame.class);
                    cmin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(cmin);


                    finish();

                }
                else{

                }


            }
        });
        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tut=new Intent(SimonInstruct.this, YoutubeVideo3.class);
                startActivity(tut);
                finish();
            }
        });

    }
    public int getDateDiffFromNow(String date){
        int days = 0;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            long diff = new Date().getTime() - sdf.parse(date).getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            days = ((int) (long) hours / 24);
            Log.i("hello", "Date "+date+" Difference From Now :"+ days + " days");
        }catch (Exception e){
            e.printStackTrace();
        }
        return days;
    }
}