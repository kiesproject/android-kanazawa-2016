package com.higamasa.juniorkanazawa;

import com.higamasa.juniorkanazawa.entity.QuizEntity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizActivity extends Activity implements View.OnClickListener {
	ArrayList<QuizEntity> answerList;	//問題list

	private int correct = 0;		//正解数
	private int sNumber = 0;		//statement番号

	private String Answer;			//正解の文字列

	private String firstAnswer;
	private String secondAnswer;
	private String thirdAnswer;
	private String fourthAnswer;

	private TextView Statement;
	private TextView Title;

//	private Button[] selectButton;
	private Button firstButton;
	private Button secondButton;
	private Button thirdButton;
	private Button fourthButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_quiz);
		setQuestion(sNumber);
	}

	//クイズの挿入
	public void setQuestion(int sNumber){
		Intent ArrayIntent = getIntent();
		answerList = (ArrayList<QuizEntity>) ArrayIntent.getSerializableExtra("all");
		String.format("%d", answerList.get(sNumber).getId());

		Statement = (TextView)findViewById(R.id.statement);
		Statement.setText(answerList.get(sNumber).getStatement());

		Title = (TextView)findViewById(R.id.title);
		Title.setText(answerList.get(sNumber).getTitle());

		firstAnswer = answerList.get(sNumber).getFirst();
		secondAnswer = answerList.get(sNumber).getSecond();
		thirdAnswer = answerList.get(sNumber).getThird();
		fourthAnswer = answerList.get(sNumber).getFourth();

		int AnswerNumber = answerList.get(sNumber).getAnswer();
		Answer = AnswerSelect(AnswerNumber);

//		selectAnswer = new String[]{firstAnswer,secondAnswer,thirdAnswer,fourthAnswer};

//		selectButton = new Button[]{firstButton,secondButton,thirdButton,fourthButton};
//		Random random = new Random();
//		int select = random.nextInt(4);
//		List<Button> list = Arrays.asList(selectButton[select]);
//		Collections.shuffle(list);
//		selectButton = (Button[])list.toArray(new Button[list.size()]);

		firstButton = (Button) findViewById(R.id.button0);
		firstButton.setText(answerList.get(sNumber).getFirst());

		secondButton = (Button) findViewById(R.id.button1);
		secondButton.setText(answerList.get(sNumber).getSecond());

		thirdButton = (Button) findViewById(R.id.button2);
		thirdButton.setText(answerList.get(sNumber).getThird());

		fourthButton = (Button) findViewById(R.id.button3);
		fourthButton.setText(answerList.get(sNumber).getFourth());

		firstButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AnswerJudge(findViewById(R.id.button0));
			}
		});
		secondButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AnswerJudge(findViewById(R.id.button1));
			}
		});
		thirdButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AnswerJudge(findViewById(R.id.button2));
			}
		});
		fourthButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AnswerJudge(findViewById(R.id.button3));
			}
		});
	}

	public void selectText(Button first, Button second, Button third, Button fourth){
	}

	//正誤判定
	public void AnswerJudge(View view){
		if (((Button)view).getText().equals(Answer)){
			//正解の時
			correct++;
//			CorrectAnimation();
				next(view);
		}
		else{
			//不正解の時
			next(view);
		}
	}

	//正解の番号を文字列に変換
	public String AnswerSelect(int answerNumber){
		String answerText = null;

		switch(answerNumber){
			case 1:
//				AnswerText = answerList.get(nStatement).getFirst();
				answerText = firstAnswer;
				break;
			case 2:
//				answerText = answerList.get(nStatement).getSecond();
				answerText = secondAnswer;
				break;
			case 3:
//				answerText = answerList.get(nStatement).getThird();
				answerText = thirdAnswer;
				break;
			case 4:
//				answerText = answerList.get(nStatement).getFourth();
				answerText = fourthAnswer;
				break;
		}
		return answerText;
	}

	//次の問題へ移行
	public void next(View view){
		++sNumber;
		if(sNumber < answerList.size()){
			setQuestion(sNumber);
		}
		else{
			Intent CorrectIntent = new Intent(QuizActivity.this,QuizResult.class);
			CorrectIntent.putExtra("correct",correct);
			CorrectIntent.putExtra("sNumber",sNumber);
			startActivity(CorrectIntent);
		}
	}

	@Override
	public void onClick(View view) {
	}
}
