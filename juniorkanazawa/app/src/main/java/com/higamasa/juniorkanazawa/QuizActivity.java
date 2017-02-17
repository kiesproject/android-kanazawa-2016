package com.higamasa.juniorkanazawa;

import android.view.ViewGroup;
import android.widget.Button;
import android.content.Intent;

import com.higamasa.juniorkanazawa.entity.QuizEntity;

import android.app.Activity;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class QuizActivity extends Activity implements View.OnClickListener {
	private boolean nextFlag = false;

	private int correct = 0;		//正解数
	private int sNumber = 0;		//statement番号

	ArrayList<QuizEntity> answerList;    //問題list

	private TextView title;

	private TextView statement;
//	private HashMap<String,String[]> map;
	private String answerText;		//正解の文字列
	private int answerNumber;		//正解の数値
	private String[] answer = new String[4];
//	private String[] answerStr = new String[]{
//			answerList.get(sNumber).getFirst(),
//			answerList.get(sNumber).getSecond(),
//			answerList.get(sNumber).getThird(),
//			answerList.get(sNumber).getFourth()
//	};

	private Button[] selectButton = new Button[4];
//	private int[] buttonId = new int[]{R.id.button0, R.id.button1, R.id.button2, R.id.button3};
//	private Button[] idButton = new Button[]{	(Button) findViewById(R.id.button0),
//												(Button) findViewById(R.id.button2),
//												(Button) findViewById(R.id.button2),
//												(Button) findViewById(R.id.button3)
//											};

	private ImageView correctImage;
	private ImageView IncorrectImage;
	private boolean Animation = true;
	private Animation anim_start_correct;
	private Animation anim_start_incorrect;
	private Animation anim_end;
	private SoundPool soundPool;
	private AudioAttributes audioAttributes;
	private int correctSound;
	private int incorrectSound;

	private FrameLayout frameLayout;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
//		return super.onTouchEvent(event);
		if(event.getAction() == MotionEvent.ACTION_UP){
			if(nextFlag) {
//					CorrectAnimation();
				next();
			}
		}
		return true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_quiz);

		setQuestion(sNumber);

		audioAttributes = new AudioAttributes.Builder()
				.setUsage(AudioAttributes.USAGE_GAME)
				.setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
				.build();
		soundPool = new SoundPool.Builder().setAudioAttributes(audioAttributes).setMaxStreams(2).build();

		correctSound = soundPool.load(this, R.raw.correct, 1);
		incorrectSound = soundPool.load(this,R.raw.incorrect, 1);
//		incorrectSound = soundPool.load(this, R.raw.incorrect,1)
	}

	//クイズの挿入
	public void setQuestion(int sNumber) {
		Intent ArrayIntent = getIntent();
		answerList = (ArrayList<QuizEntity>) ArrayIntent.getSerializableExtra("yearAll");
		String.format("%d", answerList.get(sNumber).getId());

		correctImage = (ImageView) findViewById(R.id.correctImage);
		correctImage.setImageResource(R.drawable.maru200);
		correctImage.setVisibility(View.INVISIBLE);
		IncorrectImage = (ImageView)findViewById(R.id.IncorrectImage);
		IncorrectImage.setImageResource(R.drawable.incorrect200);
		IncorrectImage.setVisibility(View.INVISIBLE);

		title = (TextView) findViewById(R.id.title);
		title.setText(answerList.get(sNumber).getTitle());

		statement = (TextView) findViewById(R.id.statement);
		statement.setText(answerList.get(sNumber).getStatement());

		answer[0] = answerList.get(sNumber).getFirst();
		answer[1] = answerList.get(sNumber).getSecond();
		answer[2] = answerList.get(sNumber).getThird();
		answer[3] = answerList.get(sNumber).getFourth();

		answerNumber = answerList.get(sNumber).getAnswer();
		answerText = answerSelect(answerNumber);



		selectButton[0] = (Button) findViewById(R.id.button0);
		selectButton[1] = (Button) findViewById(R.id.button1);
		selectButton[2] = (Button) findViewById(R.id.button2);
		selectButton[3] = (Button) findViewById(R.id.button3);


		selectButton[0].setText(answerList.get(sNumber).getFirst());
		selectButton[0].setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				answerJudge(findViewById(R.id.button0));
			}
		});
		selectButton[1].setText(answerList.get(sNumber).getSecond());
		selectButton[1].setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				answerJudge(findViewById(R.id.button1));
			}
		});
		selectButton[2].setText(answerList.get(sNumber).getThird());
		selectButton[2].setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				answerJudge(findViewById(R.id.button2));
			}
		});
		selectButton[3].setText(answerList.get(sNumber).getFourth());
		selectButton[3].setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				answerJudge(findViewById(R.id.button3));
			}
		});
	}

	//正誤判定
	public void answerJudge(View view) {
		if (nextFlag) {
			next();
			return;
		}
		if (((Button) view).getText().equals(answerText)) {
			//正解の時
			correct++;
			soundPool.play(correctSound,1.0f,1.0f,0,0,1);
			CorrectAnimation(view);
		}
		else {
			//不正解の時
			soundPool.play(incorrectSound,2.0f,2.0f,0,0,1);
			IncorrectAnimation(view);
		}
		switch (answerNumber) {
			case 1:
				selectButton[0].setBackgroundResource(R.drawable.correct_color);
				break;
			case 2:
				selectButton[1].setBackgroundResource(R.drawable.correct_color);
				break;
			case 3:
				selectButton[2].setBackgroundResource(R.drawable.correct_color);
				break;
			case 4:
				selectButton[3].setBackgroundResource(R.drawable.correct_color);
				break;
		}
		nextFlag = true;
	}

	//正解の番号を文字列に変換
	public String answerSelect(int answerNumber){
		String answerText = null;

		switch(answerNumber){
			case 1:
				answerText = answer[0];
				break;
			case 2:
				answerText = answer[1];
				break;
			case 3:
				answerText = answer[2];
				break;
			case 4:
				answerText = answer[3];
				break;
		}
		return answerText;
	}

	//次の問題へ移行
	public void next(){
		nextFlag = false;
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
		selectButton[0].setBackgroundResource(R.drawable.round_button);
		selectButton[1].setBackgroundResource(R.drawable.round_button);
		selectButton[2].setBackgroundResource(R.drawable.round_button);
		selectButton[3].setBackgroundResource(R.drawable.round_button);
	}

	@Override
	public void onClick(View view) {
	}

	public void CorrectAnimation(View view) {
//		correctImage.setVisibility(View.INVISIBLE);

		anim_start_correct = AnimationUtils.loadAnimation(this, R.anim.anim_start);
		anim_start_correct.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
//				correctImage.setVisibility(View.INVISIBLE);
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				correctImage.setVisibility(View.GONE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
		correctImage.startAnimation(anim_start_correct);

//		nextFlag = true;

// anim_end = AnimationUtils.loadAnimation(this, R.anim.anim_end);
//		if (Animation) {
//			Animation = false;

//			correctImage.setVisibility(View.VISIBLE);
//		} else {
//			Animation = true;
//			correctImage.startAnimation(anim_end);
//			correctImage.setVisibility(View.GONE);
//		}
//

// .setListener(new ViewPropertyAnimatorListenerAdapter()) {
//				correctImage.setVisibility(View.INVISIBLE);
//				.start();

//		AlphaAnimation alpha = new AlphaAnimation(0,1);
//		alpha.setDuration(3000);
//		alpha.setFillBefore(true);
//		correctImage.startAnimation(alpha);

	}

	public void IncorrectAnimation(View view){

		anim_start_incorrect = AnimationUtils.loadAnimation(this, R.anim.anim_start);
		anim_start_incorrect.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				IncorrectImage.setVisibility(View.GONE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
		IncorrectImage.startAnimation(anim_start_incorrect);
	}
}
