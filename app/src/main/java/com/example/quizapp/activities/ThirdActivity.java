package com.example.quizapp.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.quizapp.R;
import com.example.quizapp.activities.Models.Questions;

import java.util.ArrayList;

public class ThirdActivity extends AppCompatActivity {
    TextView name;
    ArrayList<Questions> questionsArrayList=new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Intent intent=getIntent();
        String userName= (String) intent.getSerializableExtra("name");
        name=findViewById(R.id.user_name);
        name.setText("Hello "+userName+", ");
        addElements();
        LinearLayout parentLinearLayout=findViewById(R.id.card_view);
        for(int i=0;i<questionsArrayList.size();i++)
        {
            final RadioButton[] radioButtons = new RadioButton[4];
            LinearLayout childLayout=new LinearLayout(this);
            //TextView questionId=new TextView(this);
            TextView question=new TextView(this);
            RadioGroup radioGroup=new RadioGroup(this);

            question.setTextSize(pxToDp(20));
            question.setText(questionsArrayList.get(i).getId()+". "+questionsArrayList.get(i).getText());
            String[] options=questionsArrayList.get(i).getOptions();
            for(int j=0;j<options.length;j++)
            {
                radioButtons[j]=new RadioButton(this);
                radioButtons[j].setText(options[j]);
                radioGroup.addView(radioButtons[j]);
            }
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(pxToDp(10),pxToDp(10),pxToDp(10),pxToDp(10));
            layoutParams.weight=2;
            layoutParams.setMarginStart(10);
            childLayout.setLayoutParams(layoutParams);
            childLayout.setPadding(pxToDp(10),pxToDp(10),pxToDp(10),pxToDp(10));
            childLayout.setGravity(1);
            childLayout.setOrientation(LinearLayout.VERTICAL);
            /*question.setLayoutParams(layoutParams);
            questionId.setLayoutParams(layoutParams);
            radioGroup.setLayoutParams(layoutParams);*/

            childLayout.addView(question);
            childLayout.addView(radioGroup);
            childLayout.setWeightSum(2);
            parentLinearLayout.addView(childLayout);

            parentLinearLayout.setOrientation(LinearLayout.VERTICAL);
            parentLinearLayout.setWeightSum(2);
        }




    }
    public void addElements()
    {
        questionsArrayList.add(new Questions(1,"Sun rises at? ",new String[]{"East","West","North","South"}));
        questionsArrayList.add(new Questions(2,"Capital of India? ",new String[]{"Mumbai","Hyderabad","Delhi","Banglore"}));
        questionsArrayList.add(new Questions(3,"Tajmahal located at?",new String[]{"Bhopal","Sikkim","Agra","Chennai"}));
        questionsArrayList.add(new Questions(4,"Sun rises at? ",new String[]{"East","West","North","South"}));
        questionsArrayList.add(new Questions(5,"Capital of India? ",new String[]{"Mumbai","Hyderabad","Delhi","Banglore"}));
        questionsArrayList.add(new Questions(6,"Tajmahal located at?",new String[]{"Bhopal","Sikkim","Agra","Chennai"}));

    }
    public int pxToDp(int px)
    {
        return (int) (px / getResources().getDisplayMetrics().density);
    }
}
