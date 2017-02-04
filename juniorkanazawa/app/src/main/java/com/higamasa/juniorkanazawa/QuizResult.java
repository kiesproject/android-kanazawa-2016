package com.higamasa.juniorkanazawa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by higamasa on 2017/01/31.
 */

public class QuizResult extends AppCompatActivity{

	int correct;
	int sNumber;

	double percentage;

	private TextView cStatement;
	private TextView rStatement;

	private TextView pText;

	private TextView gradingText;

	private Button schoolButton;
	private Button ageButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resurt_quiz);

		Intent intent = getIntent();
		correct = intent.getIntExtra("correct",correct);
		sNumber = intent.getIntExtra("sNumber",sNumber);

		cStatement = (TextView)findViewById(R.id.correctView);
		cStatement.setText(String.valueOf(correct));

		rStatement = (TextView)findViewById(R.id.statementView);
		rStatement.setText(String.valueOf(sNumber));

		percentage = (double) correct/ (double) sNumber*100;
		pText = (TextView)findViewById(R.id.percentageView);
		pText.setText(String.valueOf(((int) percentage)));

		gradingText = (TextView) findViewById(R.id.gradingView);
		correctJudge(gradingText,correct, sNumber);

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

	public TextView correctJudge(TextView text,int correct, int nState){
		if (correct/nState*100 == 100){
			text.setText("ジュニア金沢検定に認定!!");
	}
		else if (correct/nState*100 < 100 && correct/nState*100 >= 90){
			text.setText("ゴールドカードに認定!!");
		}
		else if (correct/nState*100 < 90 && correct/nState*100 >= 80){
			text.setText("シルバーカードに認定!!");
		}
		else if (correct/nState*100 < 80 && correct/nState*100 >= 70){
			text.setText("ブロンズカードに認定!!");
		}
		else{
			text.setText("もう少し頑張ろう!!");
		}
		return text;
	}
}
