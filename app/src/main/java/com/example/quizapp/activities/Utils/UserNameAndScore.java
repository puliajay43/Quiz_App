package com.example.quizapp.activities.Utils;

import java.io.Serializable;

public class UserNameAndScore implements Serializable {
    String userName,scoreOfUser;
    public UserNameAndScore(String uname, String score)
    {
        userName=uname;
        scoreOfUser=score;
    }
    public String getUserName()
    {
        return userName;
    }
    public String getScoreOfUser()
    {
        return scoreOfUser;
    }
}
