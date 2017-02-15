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
import java.util.List;
import java.util.Random;

public class QuizActivity extends Activity implements View.OnClickListener {
	private boolean nextFlag = false;

	private int correct = 0;		//正解数
	private int sNumber = 0;		//statement番号

	ArrayList<QuizEntity> answerList;    //問題list

	private String Answer;            //正解の文字列

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
	private int AnswerNumber;
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
		soundPool = new SoundPool.Builder()
				.setAudioAttributes(audioAttributes)
				.setMaxStreams(2)
				.build();

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
		Statement = (TextView) findViewById(R.id.statement);
		Statement.setText(answerList.get(sNumber).getStatement());

		Title = (TextView) findViewById(R.id.title);
		Title.setText(answerList.get(sNumber).getTitle());

		firstAnswer = answerList.get(sNumber).getFirst();
		secondAnswer = answerList.get(sNumber).getSecond();
		thirdAnswer = answerList.get(sNumber).getThird();
		fourthAnswer = answerList.get(sNumber).getFourth();

		AnswerNumber = answerList.get(sNumber).getAnswer();
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

	//正誤判定
	public void AnswerJudge(View view) {
		if (nextFlag) {
			next();
			return;
		}
		if (((Button) view).getText().equals(Answer)) {
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
		switch (AnswerNumber) {
			case 1:
				firstButton.setBackgroundResource(R.drawable.correct_color);
				break;
			case 2:
				secondButton.setBackgroundResource(R.drawable.correct_color);
				break;
			case 3:
				thirdButton.setBackgroundResource(R.drawable.correct_color);
				break;
			case 4:
				fourthButton.setBackgroundResource(R.drawable.correct_color);
				break;
		}
		nextFlag = true;
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
		firstButton.setBackgroundResource(R.drawable.round_button);
		secondButton.setBackgroundResource(R.drawable.round_button);
		thirdButton.setBackgroundResource(R.drawable.round_button);
		fourthButton.setBackgroundResource(R.drawable.round_button);
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
