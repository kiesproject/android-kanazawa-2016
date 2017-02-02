package com.higamasa.juniorkanazawa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by higamasa on 2017/01/31.
 */

public class QuizResult extends AppCompatActivity{

	int correct;
	int nStatement;

	private TextView cStatement;
	private TextView rStatement;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resurt_quiz);

		Intent intent = getIntent();
		correct = intent.getIntExtra("correct",correct);
		nStatement = intent.getIntExtra("nStatement",nStatement);
		Log.d("correctN",String.valueOf(correct));

		cStatement = (TextView)findViewById(R.id.correctView);
		cStatement.setText(correct);

		rStatement = (TextView)findViewById(R.id.statementView);
		rStatement.setText(nStatement);
	}

}
