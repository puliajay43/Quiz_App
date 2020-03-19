package com.example.quizapp.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.quizapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class SecondActivity extends AppCompatActivity {
    TextView name;
    EditText userInput;
    String userName;
    ArrayList<ThirdActivity.Questions> questionsArrayList=new ArrayList<>();
    HashMap<String,String> responses=new HashMap<>();
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        name=findViewById(R.id.user_name);
        getDialog();
        addElements();
        Button submit=findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(responses.size()!=questionsArrayList.size())
                {
                    return;
                }
                else
                {
                    AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(SecondActivity.this).setTitle("Confirm")
                            .setMessage("Are you sure you want to submit the test?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent=new Intent(SecondActivity.this,ThirdActivity.class);
                                        intent.putExtra("responses",responses);
                                        intent.putExtra("question",questionsArrayList);

                                        startActivity(intent);
                                        finish();
                                }
                            })
                            .setNegativeButton("Cancel", null);
                    AlertDialog alertDialog=alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });

    }
    public void addElements()
    {
        questionsArrayList.add(new ThirdActivity.Questions(1,"Sun rises at? ",new String[]{"East","West","North","South"}));
        questionsArrayList.add(new ThirdActivity.Questions(2,"Capital of India? ",new String[]{"Mumbai","Hyderabad","Delhi","Banglore"}));
        questionsArrayList.add(new ThirdActivity.Questions(3,"Tajmahal located at?",new String[]{"Bhopal","Sikkim","Agra","Chennai"}));
        questionsArrayList.add(new ThirdActivity.Questions(4,"Sun rises at? ",new String[]{"East","West","North","South"}));
        questionsArrayList.add(new ThirdActivity.Questions(5,"Capital of India? ",new String[]{"Mumbai","Hyderabad","Delhi","Banglore"}));
        questionsArrayList.add(new ThirdActivity.Questions(6,"Tajmahal located at?",new String[]{"Bhopal","Sikkim","Agra","Chennai"}));

    }
    private void getDialog()
    {
        EditText input = (EditText) findViewById(R.id.editTextDialogUserInput);

        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.alert_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder.setCancelable(false);
        userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        final Button done=promptsView.findViewById(R.id.done);

        final AlertDialog alertDialog = alertDialogBuilder.create();


        userInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if(s.toString().trim().length()==0){
                    done.setEnabled(false);
                } else {
                    done.setEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!userInput.getText().toString().equals("")){
                    userName=userInput.getText().toString().trim();
                    alertDialog.dismiss();
                    name.setText("Hello "+userName+", ");
                    dynamicView();
                }
            }
        });

        alertDialog.show();
    }
    public int pxToDp(int px)
    {
        return (int) (px / getResources().getDisplayMetrics().density);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void dynamicView()
    {
        LinearLayout parentLinearLayout=findViewById(R.id.card_view);
        for(int i=0;i<questionsArrayList.size();i++)
        {
            final RadioButton[] radioButtons = new RadioButton[4];
            LinearLayout childLayout=new LinearLayout(this);
            //TextView questionId=new TextView(this);
            final TextView question=new TextView(this);
            question.setTag(1);
            RadioGroup radioGroup=new RadioGroup(this);
            radioGroup.setTag(2);
            question.setTextSize(20);
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
            LinearLayout.LayoutParams radioParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            radioParams.setMargins(10,10,10,10);
            radioGroup.setPadding(10,10,10,10);
            childLayout.setLayoutParams(layoutParams);
            childLayout.setPadding(pxToDp(10),pxToDp(10),pxToDp(10),pxToDp(10));
            childLayout.setGravity(1);
            childLayout.setOrientation(LinearLayout.VERTICAL);

            childLayout.addView(question);
            childLayout.addView(radioGroup);
            childLayout.setWeightSum(2);
            parentLinearLayout.addView(childLayout);

            parentLinearLayout.setOrientation(LinearLayout.VERTICAL);
            parentLinearLayout.setWeightSum(2);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    //TextView question=findViewById(1);
                    if (selectedId == -1) {

                    }
                    else {
                        RadioButton radioButton = (RadioButton)radioGroup.findViewById(selectedId);
                        String id=question.getText().toString().substring(0,question.getText().toString().indexOf("."));
                        System.out.println(id);
                        responses.put(id,radioButton.getText().toString());

                        //sendAnswers.sendAnswer(answers);
                    }
                }

            });

        }
    }
}
