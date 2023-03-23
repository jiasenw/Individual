package my.edu.utar.individual;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Level5 extends AppCompatActivity {

    public TextView sec;
    public Button startbtn;
    public CountDownTimer c = null;
    public TextView[] arraytv;
    public TextView currenttv;
    int score = 0;
    int time = 5000;
    Random random = new Random();
    ArrayList<TextView> grayedOutTextViews = new ArrayList<>();
    int level ;
    public TextView scoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level5);

        level = getIntent().getIntExtra("Level",5);

        sec = findViewById(R.id.second);
        startbtn = findViewById(R.id.startbtn);
        Button exitbtn = findViewById(R.id.exitbtn);


        arraytv = new TextView[36];
        arraytv[0] = findViewById(R.id.box1);
        arraytv[1] = findViewById(R.id.box2);
        arraytv[2] = findViewById(R.id.box3);
        arraytv[3] = findViewById(R.id.box4);
        arraytv[4] = findViewById(R.id.box5);
        arraytv[5] = findViewById(R.id.box6);
        arraytv[6] = findViewById(R.id.box7);
        arraytv[7] = findViewById(R.id.box8);
        arraytv[8] = findViewById(R.id.box9);
        arraytv[9] = findViewById(R.id.box10);
        arraytv[10] = findViewById(R.id.box11);
        arraytv[11] = findViewById(R.id.box12);
        arraytv[12] = findViewById(R.id.box13);
        arraytv[13] = findViewById(R.id.box14);
        arraytv[14] = findViewById(R.id.box15);
        arraytv[15] = findViewById(R.id.box16);
        arraytv[16] = findViewById(R.id.box17);
        arraytv[17] = findViewById(R.id.box18);
        arraytv[18] = findViewById(R.id.box19);
        arraytv[19] = findViewById(R.id.box20);
        arraytv[20] = findViewById(R.id.box21);
        arraytv[21] = findViewById(R.id.box22);
        arraytv[22] = findViewById(R.id.box23);
        arraytv[23] = findViewById(R.id.box24);
        arraytv[24] = findViewById(R.id.box25);
        arraytv[25] = findViewById(R.id.box26);
        arraytv[26] = findViewById(R.id.box27);
        arraytv[27] = findViewById(R.id.box28);
        arraytv[28] = findViewById(R.id.box29);
        arraytv[29] = findViewById(R.id.box30);
        arraytv[30] = findViewById(R.id.box31);
        arraytv[31] = findViewById(R.id.box32);
        arraytv[32] = findViewById(R.id.box33);
        arraytv[33] = findViewById(R.id.box34);
        arraytv[34] = findViewById(R.id.box35);
        arraytv[35] = findViewById(R.id.box36);
        scoreText= findViewById(R.id.score);



        for (TextView textView : arraytv) {
            textView.setClickable(false);
        }
        startbtn.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                if (c != null) {
                    c.cancel();
                }

                currenttv = arraytv[random.nextInt(25)];
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
                        Toast.makeText(Level5.this, "U Lose", Toast.LENGTH_SHORT).show();
                    }
                };
                sec.setText("5");
                c.start();


            }
        });
        exitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Level5.this);
                builder.setTitle("Enter your name and score");

                LinearLayout layout = new LinearLayout(Level5.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText nameInput = new EditText(Level5.this);
                nameInput.setHint("Name");
                layout.addView(nameInput);

                final EditText scoreInput = new EditText(Level5.this);
                scoreInput.setHint("Score");
                scoreInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                layout.addView(scoreInput);

                builder.setView(layout);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = nameInput.getText().toString();
                        int score = Integer.parseInt(scoreInput.getText().toString());

                        Intent intent = new Intent(Level5.this, ScoreboardActivity.class);
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
                        if (score == 36) {
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
                                    Toast.makeText(Level5.this, "You Won!", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(Level5.this, Level4.class);
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


