package my.edu.utar.individual;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreboardActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private SharedPreferences preferences;
    private Button exitbtn;
    private static final int MAX_SCORES = 25;
    private HashMap<String, Integer> scoreMap = new HashMap<>(MAX_SCORES);
    private ScoreboardDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int score = intent.getIntExtra("score", 0);
        TextView nameText = findViewById(R.id.nameText);
        TextView scoreText = findViewById(R.id.scoreText);
        nameText.setText("Name: " + name);
        scoreText.setText("Score: " + score);
        exitbtn = findViewById(R.id.quit);

        listView = findViewById(R.id.listView);
        dbHelper = new ScoreboardDatabaseHelper(this);

        //insertScore(name, score);

        listView = findViewById(R.id.listView);
        //adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,


        preferences = getSharedPreferences("scoreboard", MODE_PRIVATE);

        if (scoreMap.size() >= MAX_SCORES) {
            int minScore = Integer.MAX_VALUE;
            String minName = null;
            for (Map.Entry<String, Integer> entry : scoreMap.entrySet()) {
                if (entry.getValue() < minScore) {
                    minScore = entry.getValue();
                    minName = entry.getKey();
                }
            }
            scoreMap.remove(minName);
        }
        scoreMap.put(name, score);

        scoreMap.put(name, score);

        // 保存HashMap中的分数
        saveScores();

        // 初始化适配器并设置listView的适配器
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        listView.setAdapter(adapter);

        // 加载分数并更新适配器
        loadScoresAndUpdateAdapter();

        // 退出按钮的点击事件
        exitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreboardActivity.this, Mainpage.class);
                startActivity(intent);
            }
        });
    }


    private void saveScores() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        for (Map.Entry<String, Integer> entry : scoreMap.entrySet()) {
            if (!preferences.contains(entry.getKey())) {
                editor.putInt(entry.getKey(), entry.getValue());
            }
        }
        editor.apply();
    }


    private void loadScores() {
        Map<String, ?> scoreMapFromPreferences = preferences.getAll();
        for (Map.Entry<String, ?> entry : scoreMapFromPreferences.entrySet()) {
            scoreMap.put(entry.getKey(), (Integer) entry.getValue());
        }
        if (scoreMap.size() > 25) {
            List<Map.Entry<String, Integer>> scoreList = new ArrayList<>(scoreMap.entrySet());

            // 按照得分进行排序
            Collections.sort(scoreList, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return o1.getValue() - o2.getValue();


                    }

            });

            // 删除最低分数的元素
            for (int i = 0; i < scoreMap.size() - 25; i++) {
                scoreMap.remove(scoreList.get(i).getKey());
            }
        }
}


    // 从HashMap中加载分数并更新适配器
    private void loadScoresAndUpdateAdapter() {
        List<Map.Entry<String, Integer>> scoreList = new ArrayList<>(scoreMap.entrySet());

        // 按照得分进行排序
        Collections.sort(scoreList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue() - o1.getValue();

            }
        });

    }
}