package com.higamasa.juniorkanazawa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import com.higamasa.juniorkanazawa.entity.QuizEntity;

public class QuizActivity extends AppCompatActivity {
	//int correct = 0;		//正解数

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_quiz);
		Intent intent = getIntent();
		ArrayList<QuizEntity> answerlist = (ArrayList<QuizEntity>) intent.getSerializableExtra("all");
//		answerlist = intent.getStringArrayListExtra("all");
		Log.d("aaaaaa", String.format("%d", answerlist.get(0).getId()));
		((TextView) findViewById(R.id.statement)).setText(answerlist.get(0).getStatement());
		((TextView) findViewById(R.id.title)).setText(answerlist.get(0).getTitle());
		((Button) findViewById(R.id.button0)).setText(answerlist.get(0).getFirst());
		((Button) findViewById(R.id.button1)).setText(answerlist.get(0).getSecond());
		((Button) findViewById(R.id.button2)).setText(answerlist.get(0).getThird());
		((Button) findViewById(R.id.button3)).setText(answerlist.get(0).getFourth());
	}

}
