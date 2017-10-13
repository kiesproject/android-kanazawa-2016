package com.higamasa.juniorkanazawa;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by higamasa on 2017/01/31.
 */

public class QuizResult extends Activity{

	int correct;		//正解数
	int sNumber;		//問題数

	double percentage;	//正解率

	private TextView nCorrect;
	private TextView cStatement;
	private TextView pStatement;

	private TextView rCorrect;
	private TextView pText;
	private TextView percent;

	private RatingBar bar;			//星判定
	private TextView gradingText;	//レベル判定
	private ImageView jImage;

	private Button schoolButton;	//学校選択
	private Button ageButton;		//年代選択

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resurt_quiz);

		Intent intent = getIntent();
		correct = intent.getIntExtra("correct",correct);
		sNumber = intent.getIntExtra("sNumber",sNumber);

		nCorrect = (TextView)findViewById(R.id.nCorrectView);
		nCorrect.setText("正答数");
		nCorrect.setTypeface(Typeface.createFromAsset(getAssets(), "Meiryo.ttf"));

		cStatement = (TextView)findViewById(R.id.correctView);
		cStatement.setText(String.valueOf(correct));
		cStatement.setTypeface(Typeface.createFromAsset(getAssets(), "Meiryo.ttf"));

		pStatement = (TextView)findViewById(R.id.statementView);
		pStatement.setText(String.valueOf(sNumber));
		pStatement.setTypeface(Typeface.createFromAsset(getAssets(), "Meiryo.ttf"));

		rCorrect = (TextView)findViewById(R.id.rCorrectView);
		rCorrect.setText("正答率");
		rCorrect.setTypeface(Typeface.createFromAsset(getAssets(), "Meiryo.ttf"));

		percentage = (double) correct/ (double) sNumber*100;
		pText = (TextView)findViewById(R.id.percentageView);
		pText.setText(String.valueOf(((int) percentage)));
		pText.setTypeface(Typeface.createFromAsset(getAssets(), "Meiryo.ttf"));

		percent = (TextView)findViewById(R.id.percentView);
		percent.setText("%");
		percent.setTypeface(Typeface.createFromAsset(getAssets(), "Meiryo.ttf"));

		bar = (RatingBar)findViewById(R.id.ratingBar);
		bar.setNumStars(3);
		bar.setStepSize((float) 0.5);
		bar.setIsIndicator(true);
		barJudge(bar,correct,sNumber);

		jImage = (ImageView)findViewById(R.id.judgeImage);
		imageJudge(jImage,correct,sNumber);

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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if (keyCode == KeyEvent.KEYCODE_BACK){
			return false;
		}
		return  super.onKeyDown(keyCode, event);
	}

	//Rating Barの判定
	public RatingBar barJudge(RatingBar rBar,int correct, int nState){
		double c = (double)correct;		//double型のcorrect
		double ns = (double)nState;		//double型のnState

		if (c/ns*100 == 100){
			rBar.setRating(3);
		}
		else if ((c/ns)*100 < 100 && (c/ns)*100 >= 90){
			rBar.setRating((float)2.5);
		}
		else if ((c/ns)*100 < 90 && (c/ns)*100 >= 80){
			rBar.setRating(2);
		}
		else if ((c/ns)*100 < 80 && (c/ns)*100 >= 70){
			rBar.setRating(1);
		}
		else{
			rBar.setRating(0);
		}
		return rBar;
	}

	//テスト結果の判定
	public TextView correctJudge(TextView text,int correct, int nState){
		double c = (double)correct;		//double型のcorrect
		double ns = (double)nState;		//double型のnState

		if (c/ns*100 == 100){
			text.setText("ジュニア金沢検定認定レベルです!!");
			text.setTextColor(Color.RED);
			text.setTypeface(Typeface.createFromAsset(getAssets(), "Meiryo.ttf"));
		}
		else if ((c/ns)*100 < 100 && (c/ns)*100 >= 90){
			text.setText("ゴールドカード認定レベルです!!");
			text.setTextColor(Color.RED);
			text.setTypeface(Typeface.createFromAsset(getAssets(), "Meiryo.ttf"));
		}
		else if ((c/ns)*100 < 90 && (c/ns)*100 >= 80){
			text.setText("シルバーカード認定レベルです!!");
			text.setTextColor(Color.RED);
			text.setTypeface(Typeface.createFromAsset(getAssets(), "Meiryo.ttf"));
		}
		else if ((c/ns)*100 < 80 && (c/ns)*100 >= 70){
			text.setText("ブロンズカード認定レベルです!!");
			text.setTextColor(Color.RED);
			text.setTypeface(Typeface.createFromAsset(getAssets(), "Meiryo.ttf"));
		}
		else{
			text.setText("カード認定までがんばろう！");
			text.setTypeface(Typeface.createFromAsset(getAssets(), "Meiryo.ttf"));
		}
		return text;
	}

	public ImageView imageJudge(ImageView iView,int correct, int nState){
		double c = (double)correct;		//double型のcorrect
		double ns = (double)nState;		//double型のnState


		if (c/ns*100 <= 60) {
			iView.setImageResource(R.drawable.no);
		}
		else{
			iView.setImageResource(R.drawable.yes);
		}
		return iView;
	}
}
