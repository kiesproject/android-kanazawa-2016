package com.higamasa.juniorkanazawa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by higamasa on 2017/01/31.
 */

public class QuizResult extends AppCompatActivity{

	int correct;
	int nStatement;

	private TextView cStatement;
	private TextView rStatement;

	private Button schoolButton;
	private Button ageButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resurt_quiz);

		Intent intent = getIntent();
		correct = intent.getIntExtra("correct",correct);
		nStatement = intent.getIntExtra("nStatement",nStatement);
//		Log.d("correctN",String.valueOf(correct));

//	public void setResult(int correct, int state){
		cStatement = (TextView)findViewById(R.id.correctView);
		cStatement.setText(String.valueOf(correct));

		rStatement = (TextView)findViewById(R.id.statementView);
		rStatement.setText(String.valueOf(nStatement));

		schoolButton = (Button)findViewById(R.id.schoolButton);
		ageButton = (Button)findViewById(R.id.ageButton);

		schoolButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent backButton = new Intent(QuizResult.this,MainActivity.class);
				startActivity(backButton);
			}
		});
		ageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent backButton = new Intent(QuizResult.this,QuizChoice.class);
				startActivity(backButton);
			}
		});
	}
}
