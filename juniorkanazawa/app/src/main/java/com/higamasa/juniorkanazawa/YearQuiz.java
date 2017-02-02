package com.higamasa.juniorkanazawa;

import com.higamasa.juniorkanazawa.entity.QuizEntity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by banjousyunsuke on 2016/12/27.
 */
public class YearQuiz implements Serializable {
    public ArrayList<QuizEntity> quizzes = new ArrayList<>();
    private int year;
    private String year_str;

    public ArrayList<QuizEntity> getQuizzes() {
        return quizzes;
    }

    public int getYear(){return year;}
    public String getYear_str() {return year_str;}

    public void setQuizzes(ArrayList<QuizEntity> quizzes) {
        this.quizzes = quizzes;
    }

    public void setYear(int year){this.year =year;}
    public void setYear_str(String year_str) {this.year_str = year_str;}
}
