package com.higamasa.juniorkanazawa.repository;

import android.content.Context;
import android.util.Log;
import com.higamasa.juniorkanazawa.YearQuiz;
import com.higamasa.juniorkanazawa.entity.QuizEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by banjousyunsuke on 2016/11/29.
 */
<<<<<<< HEAD

=======
>>>>>>> 613ae08f5e6c6d6ff22dd6d4e3a33ea69f5cfca9
public class QuizRepository implements Serializable {
    private Context mContext;
    //    private ArrayList<QuizEntity> mQuizList;
    private ArrayList<YearQuiz> allQuiz;
    public QuizRepository(Context context) {
//        this.mQuizList = new ArrayList<>();
        this.allQuiz = new ArrayList<>();
        this.mContext = context;
    }
    public void loadQuiz() {
        try {
//            InputStream inputStream = assetManager.open("junior1.json");
            String jsonString = getStringFromAssets("junior2.json");
            JSONObject json = new JSONObject(jsonString).getJSONObject("catalog");
//            JSONObject juniorArray = json.getJSONObject("junior");
            JSONArray yearsArray = json.getJSONArray("years");
////            for (int i = 0; i < juniorArray.length(); i++) {
            for (int j = 0; j < yearsArray.length(); j++) {
                JSONObject yearObj = yearsArray.getJSONObject(j);
                JSONArray questionsArray = yearsArray.getJSONObject(j).getJSONArray("questions");
                YearQuiz yearQuiz= new YearQuiz();
                yearQuiz.setYear(yearObj.getInt("year"));
                yearQuiz.setYear_str(yearObj.getString("year_str"));
//                    QuizEntity quizEntity2 = new QuizEntity();
//                    quizEntity2.setYear(yearObj.getInt("year"));
//                    quizEntity2.setYear_str(yearObj.getString("year_str"));
//                        mQuizList.add(quizEntity2);
//                        mQuizList.get(j).getYear();
//                        mQuizList.get(j).getYear_str();
//                    Log.d("year",(mQuizList.get().getYear_str()));
                for (int k = 0; k < questionsArray.length(); k++) {
                    JSONObject questionObj = questionsArray.getJSONObject(k);
                    QuizEntity quizEntity = new QuizEntity();
                    quizEntity.setId(questionObj.getInt("id"));
                    quizEntity.setTitle(questionObj.getString("title"));
                    quizEntity.setStatement(questionObj.getString("statement"));
                    quizEntity.setFirst(questionObj.getString("first"));
                    quizEntity.setSecond(questionObj.getString("second"));
                    quizEntity.setThird(questionObj.getString("third"));
                    quizEntity.setFourth(questionObj.getString("fourth"));
//                quizEntity.setDrawable(questionObj.getString("drawable"));
                    quizEntity.setAnswer(questionObj.getInt("answer"));
//                        quizEntity.setYear(questionObj.getInt("year"));
//                        quizEntity.setYear_str(questionObj.getString("year_str"));
                    yearQuiz.quizzes.add(quizEntity);
//                        mQuizList.add(quizEntity);
//                        mQuizList.get(k).getYear();
//                        mQuizList.get(k).getYear_str();
//                        mQuizList.get(k).getId();
//                        mQuizList.get(k).getTitle();
//                        mQuizList.get(k).getStatement();
//                        mQuizList.get(k).getFirst();
//                        mQuizList.get(k).getSecond();
//                        mQuizList.get(k).getThird();
//                        mQuizList.get(k).getFourth();
//                mQuizList.get(i).getDrawable();
//                        mQuizList.get(k).getAnswer();
                    Log.d("category", String.valueOf(quizEntity.getId()));
                    Log.d("category", (quizEntity.getTitle()));
                    Log.d("category", (quizEntity.getStatement()));
                    Log.d("category", (quizEntity.getFirst()));
                    Log.d("category", (quizEntity.getSecond()));
                    Log.d("category", (quizEntity.getThird()));
                    Log.d("category", (quizEntity.getFourth()));
//                Log.d("category",(quizEntity.getDrawable()));
                    Log.d("category", String.valueOf(quizEntity.getAnswer()));
//                Log.d("category",String.valueOf(quizEntity.getId()));
//                Log.d("category",(quizEntity.getStatement()));
//                Log.d("genre",String.valueOf(mQuizList.get(i).getId()));
//                System.out.print(quizEntity.getId());
//                System.out.print(quizEntity.getStatement());
                }
                allQuiz.add(yearQuiz);
            }
//            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
    //    public void loadShuffleQuiz() {
//        try {
////            InputStream inputStream = assetManager.open("junior1.json");
//            String jsonString = getStringFromAssets("junior2.json");
//            JSONObject json = new JSONObject(jsonString).getJSONObject("catalog");
//            JSONArray jArray = json.getJSONArray("junior");
//
//            for (int i = 0; i < jArray.length(); i++) {
//                JSONObject Jobj = jArray.getJSONObject(i);
//                QuizEntity quizEntity = new QuizEntity();
//                Random random = new Random();
//                quizEntity.setId(Jobj.getInt("id"));
//                quizEntity.setTitle(Jobj.getString("title"));
//                quizEntity.setStatement(Jobj.getString("statement"));
//                quizEntity.setFirst(Jobj.getString("first"));
//                quizEntity.setSecond(Jobj.getString("second"));
//                quizEntity.setThird(Jobj.getString("third"));
//                quizEntity.setFourth(Jobj.getString("fourth"));
////                quizEntity.setDrawable(Jobj.getString("drawable"));
//                quizEntity.setAnswer(Jobj.getInt("answer"));
//                mQuizList.add(quizEntity);
//                mQuizList.get(i).getId();
//                mQuizList.get(i).getStatement();
//                mQuizList.get(i).getTitle();
//                mQuizList.get(i).getYear();
////                Log.d("category",String.valueOf(quizEntity.getId()));
////                Log.d("category",(quizEntity.getStatement()));
////                Log.d("genre",String.valueOf(mQuizList.get(i).getId()));
////                System.out.print(quizEntity.getId());
////                System.out.print(quizEntity.getStatement());
//
//            }
//        } catch (IOException | JSONException e) {
//            e.printStackTrace();
//        }
//    }
    public ArrayList<YearQuiz> getQuizList() {
        return allQuiz;
    }
    private String getStringFromAssets(String FileName) throws IOException {
        String file = "";
        InputStream inputstream = mContext.getAssets().open(FileName);
        int size = inputstream.available();
        byte[] buffer = new byte[size];
        inputstream.read(buffer);
        inputstream.close();
        file = new String(buffer);
        return file;
    }
}
