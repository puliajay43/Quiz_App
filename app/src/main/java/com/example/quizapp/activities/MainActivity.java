package com.example.quizapp.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quizapp.R;

public class MainActivity extends AppCompatActivity {
    EditText input,userInput;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView=findViewById(R.id.main_logo);
        imageView.setImageResource(R.drawable.logo);
        Button takeTest=findViewById(R.id.take_test);
        takeTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent=new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
                finish();*/
                getDialog();
            }
        });
    }
    private void getDialog()
    {
        input = (EditText) findViewById(R.id.editTextDialogUserInput);

        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.alert_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder.setCancelable(false);
        userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);
           /* alertDialogBuilder.setPositiveButton("Done",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                        if(userInput.getText().toString().equals(""))
                                        {
                                            Intent intent=getIntent();
                                            finish();
                                            startActivity(intent);

                                        }
                                        else {
                                            Intent intent=new Intent(SecondActivity.this,ThirdActivity.class);
                                            intent.putExtra("name",userInput.getText().toString());
                                            startActivity(intent);
                                            finish();
                                        }
                                }
                            });*/
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
                    Intent intent=new Intent(MainActivity.this, SecondActivity.class);
                    intent.putExtra("name",userInput.getText().toString());
                    startActivity(intent);
                    finish();
                }
            }
        });
        WindowManager.LayoutParams lp = alertDialog.getWindow().getAttributes();
        lp.dimAmount=0.0f;
        alertDialog.getWindow().setAttributes(lp);
        alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        alertDialog.show();
    }
}
