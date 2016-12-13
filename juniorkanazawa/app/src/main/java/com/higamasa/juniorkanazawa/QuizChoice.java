package com.higamasa.juniorkanazawa;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.higamasa.juniorkanazawa.entity.QuizEntity;
import com.higamasa.juniorkanazawa.repository.QuizRepository;

import java.util.ArrayList;

/**
 * Created by banjousyunsuke on 2016/12/02.
 */
public class QuizChoice extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizchoice);
        ListView listView = (ListView)findViewById(R.id.quizList);
        final QuizRepository repository = new QuizRepository(this);
//        ArrayList<QuizEntity> quizList;
//        quizList = repository.getQuizList();
        repository.loadQuiz();
//        Log.d("year", String.valueOf(repository));
        ArrayList<QuizEntity> quizList;
        quizList = repository.getQuizList();
        String[] data = new String[quizList.size()];
        for (int i = 0;i<quizList.size();i++){
            data[i] = quizList.get(i).getYear();
            Log.d("year",data[i]);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,data);
        listView.setAdapter(arrayAdapter);

    }
}
