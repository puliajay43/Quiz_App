package com.example.quizapp.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.quizapp.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class ThirdActivity extends AppCompatActivity {

    TextView score;
    HashMap<String, String> responses;
    HashMap<String, String> answers = new HashMap<>();
    ArrayList<Questions> questionsArrayList;
    Button takeTest;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Intent intent = getIntent();
        responses = (HashMap<String, String>) intent.getSerializableExtra("responses");
        addAnswers();
        questionsArrayList = (ArrayList<Questions>) intent.getSerializableExtra("question");
        score = findViewById(R.id.score);
        String finalScore = score.getText() + "<b>" + getScore() + "/" + responses.size() + "</b>";
        score.setText(Html.fromHtml(finalScore));

        getResponses();
        listeners();
    }

    private void listeners() {
        takeTest = findViewById(R.id.take_test);
        takeTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThirdActivity.this, SecondActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void getResponses() {
        LinearLayout parentLinearLayout = findViewById(R.id.inner_layout);
        Set<String> qIds = answers.keySet();
        Iterator<String> it = qIds.iterator();

        for (int i = 0; i < questionsArrayList.size(); i++) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.layout_question, null);

            TextView questionTextView = view.findViewById(R.id.question_text_view);
            ImageView responseCorrectImageView = view.findViewById(R.id.response_correct_image_view);
            ImageView responseIncorrectImageView = view.findViewById(R.id.response_incorrect_image_view);
            TextView option1TextView = view.findViewById(R.id.option_1_text_view);
            TextView option2TextView = view.findViewById(R.id.option_2_text_view);
            TextView option3TextView = view.findViewById(R.id.option_3_text_view);
            TextView option4TextView = view.findViewById(R.id.option_4_text_view);
            TextView resultTextView = view.findViewById(R.id.actual_answer_text_view);


            String qid = it.next();
            if (!answers.get(qid).equals(responses.get(qid))) {
                responseIncorrectImageView.setVisibility(View.VISIBLE);
                resultTextView.setVisibility(View.VISIBLE);
                resultTextView.setText("Your answer: " + responses.get(qid) + " | Actual answer: " + answers.get(qid));
            } else {
                responseCorrectImageView.setVisibility(View.VISIBLE);
            }

            questionTextView.setText(questionsArrayList.get(i).getId() + ". " + questionsArrayList.get(i).getText());

            String[] options = questionsArrayList.get(i).getOptions();

            option1TextView.setText("a. " + options[0]);
            option2TextView.setText("b. " + options[1]);
            option3TextView.setText("c. " + options[2]);
            option4TextView.setText("d. " + options[3]);

            parentLinearLayout.addView(view);
        }

    }

    private int getScore() {
        int score = 0;
        Set<String> qIds = answers.keySet();
        Iterator<String> it = qIds.iterator();
        for (int i = 0; i < qIds.size(); i++) {
            String qid = it.next();
            if (answers.get(qid).equals(responses.get(qid)))
                score++;
            it.hasNext();
        }
        return score;
    }

    private void addAnswers() {
        answers.put("1", "East");
        answers.put("2", "Delhi");
        answers.put("3", "Agra");
        answers.put("4", "East");
        answers.put("5", "Delhi");
        answers.put("6", "Agra");
    }

    public static class Questions implements Serializable {
        private String text;
        String[] option;
        private int id;

        public Questions(int idNum, String textTemp, String[] options) {
            id = idNum;
            text = textTemp;
            option = options;
        }

        public int getId() {
            return id;
        }

        public String getText() {
            return text;
        }

        public String[] getOptions() {
            return option;
        }
    }
}
