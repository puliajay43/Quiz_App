package com.example.quizapp.activities;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.quizapp.R;
import com.example.quizapp.activities.Persistance.AppPref;
import com.example.quizapp.activities.Utils.UserNameAndScore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class ScoresActivity extends AppCompatActivity {

    AppPref namesAndScores;
    ArrayList<UserNameAndScore> userNameAndScoreArrayList;
    LinearLayout parentLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        Intent intent=getIntent();
        userNameAndScoreArrayList = (ArrayList<UserNameAndScore>) intent.getSerializableExtra("namesAndScoresArrayList");
        namesAndScores=new AppPref(this);
        dynamicView();
    }
    private void dynamicView() {
        parentLayout=findViewById(R.id.parent_layout);
        //namesAndScores.print();
        ArrayList<UserNameAndScore> temp=namesAndScores.getData();
        for (int i = 0; i < temp.size(); i++)
        {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.scores_layout, null);
            TextView nameTextView=view.findViewById(R.id.name_text_view);
            TextView scoreTextView= view.findViewById(R.id.score_text_view);

            nameTextView.setText(temp.get(i).getUserName());
            scoreTextView.setText(temp.get(i).getScoreOfUser());
            parentLayout.addView(view);
        }
    }
    public ArrayList<String> getArrayList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
