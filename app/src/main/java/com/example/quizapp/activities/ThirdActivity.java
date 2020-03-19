package com.example.quizapp.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
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
    HashMap<String,String> responses;
    HashMap<String,String> answers=new HashMap<>();
    ArrayList<Questions> questionsArrayList;
    Button takeTest;
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Intent intent=getIntent();
        responses= (HashMap<String, String>) intent.getSerializableExtra("responses");
        addAnswers();
        questionsArrayList= (ArrayList<Questions>) intent.getSerializableExtra("question");
        score=findViewById(R.id.score);
        String finalScore=score.getText()+"<b>"+getScore()+"/"+responses.size()+"</b>";
        score.setText(Html.fromHtml(finalScore));

        getResponses();
        listeners();
    }
    private void listeners()
    {
        takeTest=findViewById(R.id.take_test);
        takeTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ThirdActivity.this,SecondActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.P)
    private void getResponses()
    {
        LinearLayout parentLinearLayout=findViewById(R.id.inner_layout);
        Set<String> qIds=answers.keySet();
        Iterator<String> it=qIds.iterator();
        for(int i=0;i<questionsArrayList.size();i++)
        {
            final TextView[] textViews = new TextView[4];
            LinearLayout childLayout=new LinearLayout(this);
            final TextView question=new TextView(this);
            //question.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            ImageView isCorrect=new ImageView(this);
            //isCorrect.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            String qid=it.next();
            if(!answers.get(qid).equals(responses.get(qid))) {
                isCorrect.setImageResource(R.drawable.outline_clear_black_18);
                isCorrect.setColorFilter(R.color.red);
            }
            else {
                isCorrect.setImageResource(R.drawable.outline_done_black_18);

            }
            LinearLayout questionCorrect=new LinearLayout(this);
            questionCorrect.setOrientation(LinearLayout.HORIZONTAL);
            question.setTextSize(20);
            question.setText(questionsArrayList.get(i).getId()+". "+questionsArrayList.get(i).getText());
            questionCorrect.addView(question);
            questionCorrect.addView(isCorrect);

           // childLayout.addView(questionCorrect);
            LinearLayout optionsLinearLayout=new LinearLayout(this);
            optionsLinearLayout.addView(questionCorrect);
            String[] options=questionsArrayList.get(i).getOptions();
            optionsLinearLayout.setOrientation(LinearLayout.VERTICAL);

            for(int j=0;j<options.length;j++)
            {
                textViews[j]=new TextView(this);
                textViews[j].setText((j+1)+". "+options[j]);
                textViews[j].setTextSize(20);
                optionsLinearLayout.addView(textViews[j]);
            }
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(pxToDp(10),pxToDp(10),pxToDp(10),pxToDp(10));
            layoutParams.weight=2;
            layoutParams.setMarginStart(10);
            optionsLinearLayout.setLayoutParams(layoutParams);
            questionCorrect.setLayoutParams(layoutParams);
            childLayout.addView(optionsLinearLayout);
            TextView answer=new TextView(this);
            answer.setText("Your answer: "+responses.get(qid)+" | Actual answer: "+answers.get(qid));
            answer.setTextSize(16);
            childLayout.addView(answer);
            if(i==questionsArrayList.size()-1)
            {
                childLayout.addView(new TextView(this));
                childLayout.addView(new TextView(this));
                childLayout.addView(new TextView(this));
            }
            childLayout.setLayoutParams(layoutParams);
            childLayout.setPadding(pxToDp(10),pxToDp(10),pxToDp(10),pxToDp(10));
            childLayout.setGravity(1);
            childLayout.setOrientation(LinearLayout.VERTICAL);


            //childLayout.addView(radioGroup);
            childLayout.setWeightSum(2);
            parentLinearLayout.addView(childLayout);

            parentLinearLayout.setOrientation(LinearLayout.VERTICAL);
            parentLinearLayout.setWeightSum(2);
        }
    }
    private int getScore()
    {
        int score=0;
        Set<String> qIds=answers.keySet();
        Iterator<String> it=qIds.iterator();
        for(int i=0;i<qIds.size();i++)
        {
            String qid=it.next();
            if(answers.get(qid).equals(responses.get(qid)))
                score++;
            it.hasNext();
        }
        return score;
    }
    private void addAnswers()
    {
        answers.put("1","East");
        answers.put("2","Delhi");
        answers.put("3","Agra");
        answers.put("4","East");
        answers.put("5","Delhi");
        answers.put("6","Agra");
    }
    public int pxToDp(int px)
    {
        return (int) (px / getResources().getDisplayMetrics().density);
    }

    public static class Questions implements Serializable {
        private String text;
        String[] option;
        private int id;
        public Questions(int idNum,String textTemp,String[] options)
        {
            id=idNum;
            text=textTemp;
            option=options;
        }
        public int getId()
        {
            return id;
        }
        public String getText()
        {
            return text;
        }
        public String[] getOptions()
        {
            return option;
        }
    }
}
