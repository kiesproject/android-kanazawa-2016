package com.higamasa.juniorkanazawa;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.higamasa.juniorkanazawa.entity.QuizEntity;
import com.higamasa.juniorkanazawa.repository.QuizRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by banjousyunsuke on 2016/12/02.
 */

public class QuizChoice extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice_quiz);
        ListView listView = (ListView)findViewById(R.id.quizList);
        final QuizRepository repository = new QuizRepository(this);
//        ArrayList<QuizEntity> quizList;
//        quizList = repository.getQuizList();
        repository.loadQuiz();
//        Log.d("year", String.valueOf(repository));
        final ArrayList<YearQuiz> yearList = repository.getQuizList();
//        if(yearList.isEmpty()){
//            Log.d("QuizList","isEmpty");
//        } else {
//            Log.d("QuizList", ""+yearList.size());
//        }
//        Log.d("QuizList", ""+yearList.size());
        String[] data = new String[yearList.size()];
//        String[] data = (String[]) quizList.toArray(new String[0]);
//        for (String str : data) {
//            Log.d("year",str);
//        }
        for (int i = 0;i<yearList.size();i++){
            data[i] = yearList.get(i).getYear_str();
            Log.d("year",data[i]);
        }

        Button tutoButton = (Button)findViewById(R.id.tutorial);
        tutoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizChoice.this, TutoActivity.class);
                startActivity(intent);
            }
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,data);
        listView.setAdapter(arrayAdapter);

        Button allQuizButton = (Button)findViewById(R.id.total);
        allQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizChoice.this,AllQuizActivity.class);
                intent.putExtra("All",yearList);
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent , View view, int position, long l) {
//                ListView listView = (ListView) parent;
                Intent intent = new Intent(QuizChoice.this,QuizActivity.class);
                intent.putExtra("yearAll",yearList.get(position).getQuizzes());
//                Bundle bundle = new Bundle();
                startActivity(intent);
                //  yearList.get(position).quizzes;
            }
        });

    }
}
