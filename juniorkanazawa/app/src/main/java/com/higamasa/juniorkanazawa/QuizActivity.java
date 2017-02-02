
package com.higamasa.juniorkanazawa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.higamasa.juniorkanazawa.entity.QuizEntity;


import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    String[] QUIZLIST = {"100", "200", "300", "400"};
    String ANSWER = "300";
    int correct = 0;        //正解数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_quiz);
        Intent intent = getIntent();
        ArrayList<QuizEntity> answerlist = (ArrayList<QuizEntity>) intent.getSerializableExtra("all");
//		answerlist = intent.getStringArrayListExtra("all");
        Log.d("aaaaaa", String.format("%d", answerlist.get(0).getId()));

        ((Button) findViewById(R.id.button0)).setText(answerlist.get(15).getFirst());
        ((Button) findViewById(R.id.button1)).setText(answerlist.get(15).getSecond());
        ((Button) findViewById(R.id.button2)).setText(answerlist.get(15).getThird());
        ((Button) findViewById(R.id.button3)).setText(answerlist.get(15).getFourth());
        ((TextView)findViewById(R.id.statement)).setText(answerlist.get(0).getStatement());
        ((TextView)findViewById(R.id.title)).setText(answerlist.get(0).getTitle());
//	}
//
//	public void AnswerSelect(View view){
//		String text = ((Button)view).getText().toString();
//
//		for (int i=0; i < QUIZLIST.length; i++) {
//			if (text.equals(QUIZLIST[i])) {
//
//				view.setEnabled(false);
//				correct++;
//
//			}
//		}
    }
}

