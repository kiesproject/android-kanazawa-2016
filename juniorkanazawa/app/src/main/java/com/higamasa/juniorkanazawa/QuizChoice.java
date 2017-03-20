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
    private int juniorJudge = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice_quiz);
        ListView listView = (ListView)findViewById(R.id.quizList);
        final QuizRepository repository = new QuizRepository(this);
        final QuizRepository figureRepository = new QuizRepository(this);
//        ArrayList<QuizEntity> quizList;
//        quizList = repository.getQuizList();
        repository.loadQuiz();
        figureRepository.loadFigureQuiz();
//        Log.d("year", String.valueOf(repository));
        final ArrayList<YearQuiz> yearList = repository.getQuizList();
        final ArrayList<YearQuiz> figureList = figureRepository.getQuizList();
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
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,data);
        listView.setAdapter(arrayAdapter);
        Button button = (Button)findViewById(R.id.total);
        button.setOnClickListener(new View.OnClickListener() {
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
                intent.putExtra("position",position);
                intent.putExtra("schoolJudge",juniorJudge);
//                Bundle bundle = new Bundle();
                startActivity(intent);
                //  yearList.get(position).quizzes;
            }
        });
        Button figureButton = (Button)findViewById(R.id.figureChoice);
        figureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent figureIntent = new Intent(QuizChoice.this,FigureQuiz.class);
                figureIntent.putExtra("figureQuiz",figureList.get(0).getQuizzes());
                startActivity(figureIntent);
            }
        });

    }
}
