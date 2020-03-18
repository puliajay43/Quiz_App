package com.example.quizapp.activities.Models;



public class Questions {
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

