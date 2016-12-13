package com.higamasa.juniorkanazawa.repository;

import android.content.Context;
import android.util.Log;

import com.higamasa.juniorkanazawa.entity.QuizEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by banjousyunsuke on 2016/11/29.
 */
public class QuizRepository {
    private Context mContext;
    private ArrayList<QuizEntity> mQuizList;

    public QuizRepository(Context context) {
        this.mQuizList = new ArrayList<>();
        this.mContext = context;
    }
    public void loadQuiz(){
        try{
//            InputStream inputStream = assetManager.open("junior1.json");
            String jsonString = getStringFromAssets("junior1.json");
            JSONObject json = new JSONObject(jsonString).getJSONObject("catalog");
            JSONArray jArray = json.getJSONArray("junior");

            for (int i = 0; i<jArray.length(); i++) {
                JSONObject Jobj = jArray.getJSONObject(i);
                QuizEntity quizEntity = new QuizEntity();
                quizEntity.setId(Jobj.getInt("id"));
                quizEntity.setTitle(Jobj.getString("title"));
                quizEntity.setYear(Jobj.getString("year"));
                quizEntity.setStatement(Jobj.getString("statement"));
//                quizEntity.setFirst(Jobj.getString("first"));
//                quizEntity.setSecond(Jobj.getString("second"));
//                quizEntity.setThird(Jobj.getString("third"));
//                quizEntity.setFourth(Jobj.getString("fourth"));
//                quizEntity.setDrawable(Jobj.getString("drawable"));
//                quizEntity.setAnswer(Jobj.getInt("answer"));
                mQuizList.add(quizEntity);
                mQuizList.get(i).getId();
                mQuizList.get(i).getStatement();
                mQuizList.get(i).getTitle();
                mQuizList.get(i).getYear();
//                Log.d("category",String.valueOf(quizEntity.getId()));
//                Log.d("category",(quizEntity.getStatement()));
//                Log.d("genre",String.valueOf(mQuizList.get(i).getId()));
//                System.out.print(quizEntity.getId());
//                System.out.print(quizEntity.getStatement());
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<QuizEntity> getQuizList(){
        return mQuizList;
    }
    private String getStringFromAssets(String FileName) throws IOException {
        String file = "";
        InputStream inputstream = mContext.getAssets().open(FileName);
        int size = inputstream.available();
        byte[]buffer = new byte[size];
        inputstream.read(buffer);
        inputstream.close();
        file = new String(buffer);
        return file;
    }
}
