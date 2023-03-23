package my.edu.utar.individual;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    public TextView sec;
    public Button startbtn;
    public CountDownTimer c = null;
    public TextView[] arraytv;
    public TextView currenttv;
    public int score = 0;
    public int time = 5000;
    public Random random = new Random();
    public ArrayList<TextView> grayedOutTextViews = new ArrayList<>();
    public TextView scoreText;

    int level;






    // TIMER
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);


        SharedPreferences.Editor editor = prefs.edit();


        int scr = 0;
        editor.putInt("score",scr);

        editor.apply();


        sec = findViewById(R.id.second);
        startbtn = findViewById(R.id.startbtn);
        Button exitbtn = findViewById(R.id.exitbtn);
        Button nextLevelBtn = findViewById(R.id.nextbtn);

        arraytv = new TextView[4];
        arraytv[0] = findViewById(R.id.box1);
        arraytv[1] = findViewById(R.id.box2);
        arraytv[2] = findViewById(R.id.box3);
        arraytv[3] = findViewById(R.id.box4);

        scoreText= findViewById(R.id.score);

        for (TextView textView : arraytv) {
            textView.setClickable(false);
        }
        startbtn.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                if (c != null) {
                    c.cancel();
                }


                currenttv = arraytv[random.nextInt(4)];
                currenttv.setBackgroundColor(Color.YELLOW);

                for (TextView textView : arraytv) {
                    textView.setClickable(true);
                }

                c = new CountDownTimer(time, 1000) {
                    public void onTick(long l) {
                        sec.setText(String.valueOf(l / 1000));

                    }

                    public void onFinish() {
                        sec.setText(String.valueOf(time / 1000));
                        for (TextView textView : arraytv) {
                            textView.setClickable(false);
                            textView.setBackgroundColor(Color.WHITE);
                        }
                        grayedOutTextViews.clear();
                        score = 0;

                        if(c!=null){
                            c.cancel();
                        }
                        Toast.makeText(MainActivity.this, "U Lose", Toast.LENGTH_SHORT).show();
                    }
                };
                sec.setText("5");
                c.start();


            }
        });

        exitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Enter your name and score");

                LinearLayout layout = new LinearLayout(MainActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText nameInput;
                nameInput = new EditText(MainActivity.this);
                nameInput.setHint("Name");
                layout.addView(nameInput);

                final EditText scoreInput = new EditText(MainActivity.this);
                scoreInput.setHint("Score");
                scoreInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                layout.addView(scoreInput);

                builder.setView(layout);


                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = nameInput.getText().toString();
                        int score = Integer.parseInt(scoreInput.getText().toString());

                        Intent intent = new Intent(MainActivity.this, ScoreboardActivity.class);
                        intent.putExtra("name", name);
                        intent.putExtra("score", score);
                        startActivity(intent);
                        finish();
                    }
                });

                builder.setNegativeButton("Cancel", null);

                builder.show();
            }
        });
        nextLevelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Level2.class);
                intent.putExtra("score", score+level);
                startActivity(intent);
                finish();
            }
        });

        for (TextView textView : arraytv) {
            textView.setOnClickListener(new View.OnClickListener() {
                Random random = new Random();

                @Override
                public void onClick(View v) {
                    if (v == currenttv) {
                        v.setBackgroundColor(Color.GRAY);
                        v.setClickable(false);
                        grayedOutTextViews.add(currenttv);
                        score++;

                        scoreText.setText("Score:"+score);
                        if (score == 4) {
                            for (TextView textView : arraytv) {
                                textView.setClickable(false);
                            }
                            boolean allClicked = true;
                            for (TextView textView : arraytv) {
                                if (textView.isClickable()) {
                                    allClicked = false;
                                    break;
                                }
                            }
                            if (allClicked) {
                                if (grayedOutTextViews.size() == arraytv.length) {
                                    Toast.makeText(MainActivity.this, "You Won!", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(MainActivity.this, Level2.class);
                                    i.putExtra("score",score);
                                    startActivity(i);
                                    finish();
                                }
                                if (c != null) {
                                    c.cancel();


                                }
                            }
                        }

                        currenttv = getNextHighlightTextView();
                        if (currenttv != null) {
                            currenttv.setBackgroundColor(Color.YELLOW);
                        }
                    }
                }
            });
        }
    }

    private TextView getNextHighlightTextView() {
        List<TextView> nonGrayedOutTextViews = new ArrayList<>();
        for (TextView textView : arraytv) {
            if (!grayedOutTextViews.contains(textView)) {
                nonGrayedOutTextViews.add(textView);
            }
        }
            nonGrayedOutTextViews.remove(currenttv);

        if (nonGrayedOutTextViews.isEmpty()) {
            return null;
        }
        Collections.shuffle(nonGrayedOutTextViews);
        return nonGrayedOutTextViews.get(0);
    }

}

