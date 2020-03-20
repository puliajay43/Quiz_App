package com.example.quizapp.activities;

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
import java.util.regex.Pattern;

public class SecondActivity extends AppCompatActivity {
    TextView name;
    EditText userInput;
    String userName;
    ArrayList<ThirdActivity.Questions> questionsArrayList=new ArrayList<>();
    HashMap<String,String> responses=new HashMap<>();
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        name=findViewById(R.id.user_name);
        submit=findViewById(R.id.submit);
        submit.setEnabled(false);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        getDialog();
        addElements();
        listeners();
    }
    private void listeners()
    {

        //submit.setEnabled(false);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        });
    }
    public void addElements()
    {
        questionsArrayList.add(new ThirdActivity.Questions(1,"Sun rises at? ",new String[]{"East","West","North","South"}));
        questionsArrayList.add(new ThirdActivity.Questions(2,"Capital of India? ",new String[]{"Mumbai","Hyderabad","Delhi","Banglore"}));
        questionsArrayList.add(new ThirdActivity.Questions(3,"Tajmahal located at?",new String[]{"Bhopal","Sikkim","Agra","Chennai"}));
        questionsArrayList.add(new ThirdActivity.Questions(4,"Sun rises at? ",new String[]{"East","West","North","South"}));
        questionsArrayList.add(new ThirdActivity.Questions(5,"Capital of India? ",new String[]{"Mumbai","Hyderabad","Delhi","Banglore"}));
        questionsArrayList.add(new ThirdActivity.Questions(6,"Taj mahal located at?",new String[]{"Bhopal","Sikkim","Agra","Chennai"}));

    }
    private void getDialog()
    {

        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.alert_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder.setCancelable(false);
        userInput = promptsView.findViewById(R.id.editTextDialogUserInput);

        final Button done=promptsView.findViewById(R.id.done);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        done.setEnabled(false);
        editTextChanges(done);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!userInput.getText().toString().equals("")){
                    userName=userInput.getText().toString().trim();
                    userName=userName.replaceAll("\\s+"," ");
                    alertDialog.dismiss();
                    name.setText("Hello "+userName+", ");
                    dynamicView();
                }
            }
        });

        alertDialog.show();
    }
    private void editTextChanges(final Button done)
    {
        userInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if(s.toString().trim().length()!=0) {
                    if (!Pattern.matches("[a-zA-Z]+", s.toString().substring(0, 1))) {
                        return;
                    }
                }
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
    }
     private void dynamicView()
    {
        LinearLayout parentLinearLayout=findViewById(R.id.card_view);
        for(int i=0;i<questionsArrayList.size();i++)
        {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View questionsView = inflater.inflate(R.layout.layout_question_paper, null);
            String[] options=questionsArrayList.get(i).getOptions();
            TextView questionTextView=questionsView.findViewById(R.id.question);
            RadioGroup radioGroup=questionsView.findViewById(R.id.radio_group);
            RadioButton option1=questionsView.findViewById(R.id.option_1);
            RadioButton option2=questionsView.findViewById(R.id.option_2);
            RadioButton option3=questionsView.findViewById(R.id.option_3);
            RadioButton option4=questionsView.findViewById(R.id.option_4);
            questionTextView.setText(questionsArrayList.get(i).getId()+". "+questionsArrayList.get(i).getText());
            option1.setText(options[0]);
            option2.setText(options[1]);
            option3.setText(options[2]);
            option4.setText(options[3]);

            parentLinearLayout.addView(questionsView);

            parentLinearLayout.setOrientation(LinearLayout.VERTICAL);
            parentLinearLayout.setWeightSum(2);

            radioResponse(radioGroup,questionTextView);

        }
    }
    private void radioResponse(RadioGroup radioGroup, final TextView question)
    {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId != -1){
                    RadioButton radioButton = (RadioButton)radioGroup.findViewById(selectedId);
                    String id=question.getText().toString().substring(0,question.getText().toString().indexOf("."));
                    System.out.println(id);
                    responses.put(id,radioButton.getText().toString());

                }
                if(responses.size()==questionsArrayList.size())
                {
                    submit.setEnabled(true);
                }
                else
                    submit.setEnabled(false);
            }

        });
    }
}
