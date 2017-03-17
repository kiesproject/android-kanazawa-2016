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
import com.higamasa.juniorkanazawa.repository.ElementalRepository;
import com.higamasa.juniorkanazawa.repository.QuizRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by banjousyunsuke on 2016/12/02.
 */
public class ElementalChoice extends AppCompatActivity{
    private int elementalJudge = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice_quiz);
        ListView listView = (ListView)findViewById(R.id.quizList);
        final ElementalRepository repository = new ElementalRepository(this);
//        ArrayList<QuizEntity> quizList;
//        quizList = repository.getQuizList();
        repository.elementalQuiz();
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
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,data);
        listView.setAdapter(arrayAdapter);
        Button button = (Button)findViewById(R.id.total);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ElementalChoice.this,AllQuizActivity.class);
                intent.putExtra("All",yearList);
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent , View view, int position, long l) {
//                ListView listView = (ListView) parent;
                Intent intent = new Intent(ElementalChoice.this,QuizActivity.class);
                intent.putExtra("yearAll",yearList.get(position).getQuizzes());
                intent.putExtra("position",position);
                intent.putExtra("schoolJudge",elementalJudge);
//                Bundle bundle = new Bundle();
                startActivity(intent);
                //  yearList.get(position).quizzes;
            }
        });

    }
}
