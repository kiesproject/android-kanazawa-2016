package com.higamasa.juniorkanazawa;

import android.content.Intent;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.higamasa.juniorkanazawa.entity.QuizEntity;
import android.app.Activity;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;


import com.higamasa.juniorkanazawa.entity.QuizEntity;


import java.util.ArrayList;

public class QuizActivity extends Activity implements View.OnClickListener {
	private boolean nextFlag = false;

	ArrayList<QuizEntity> answerList;    //問題list

	private int correct = 0;        //正解数
	private int nStatement = 0;        //statement番号

	private String Answer;            //正解の文字列

	private TextView Statement;
	private TextView Title;

	private Button FirstButton;
	private Button SecondButton;
	private Button ThirdButton;
	private Button FourthButton;

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
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	private GoogleApiClient client;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
//		return super.onTouchEvent(event);
		if(event.getAction() == MotionEvent.ACTION_UP){
				if(nextFlag) {
					next();
				}
		}
		return true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_quiz);
		audioAttributes = new AudioAttributes.Builder()
				.setUsage(AudioAttributes.USAGE_GAME)
				.setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
				.build();
		soundPool = new SoundPool.Builder()
				.setAudioAttributes(audioAttributes)
				.setMaxStreams(2)
				.build();

		correctSound = soundPool.load(this, R.raw.correct, 1);
//		incorrectSound = soundPool.load(this, R.raw.incorrect,1)

		setQuestion(nStatement);
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
	}

	//クイズの挿入
	public void setQuestion(int sNumber) {
		Intent ArrayIntent = getIntent();
		answerList = (ArrayList<QuizEntity>) ArrayIntent.getSerializableExtra("yearAll");
		String.format("%d", answerList.get(sNumber).getId());

		Statement = (TextView) findViewById(R.id.statement);
		Statement.setText(answerList.get(sNumber).getStatement());

		Title = (TextView) findViewById(R.id.title);
		Title.setText(answerList.get(sNumber).getTitle());

		FirstButton = (Button) findViewById(R.id.button0);
		FirstButton.setText(answerList.get(sNumber).getFirst());
		SecondButton = (Button) findViewById(R.id.button1);
		SecondButton.setText(answerList.get(sNumber).getSecond());
		ThirdButton = (Button) findViewById(R.id.button2);
		ThirdButton.setText(answerList.get(sNumber).getThird());
		FourthButton = (Button) findViewById(R.id.button3);
		FourthButton.setText(answerList.get(sNumber).getFourth());

		AnswerNumber = answerList.get(sNumber).getAnswer();
		Answer = AnswerSelect(AnswerNumber);
		FirstButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AnswerJudge(findViewById(R.id.button0));

			}
		});
		SecondButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AnswerJudge(findViewById(R.id.button1));
			}
		});
		ThirdButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AnswerJudge(findViewById(R.id.button2));
			}
		});
		FourthButton.setOnClickListener(new View.OnClickListener() {
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
			soundPool.play(correctSound, 1.0f, 1.0f, 0, 0, 1);
			CorrectAnimation(view);


		} else {
			//不正解の時
			soundPool.play(incorrectSound,1.0f,1.0f,0,0,1);
			IncorrectAnimation(view);
		}
		switch (AnswerNumber) {
			case 1:
				FirstButton.setBackgroundResource(R.drawable.correct_color);
				break;
			case 2:
				SecondButton.setBackgroundResource(R.drawable.correct_color);
				break;
			case 3:
				ThirdButton.setBackgroundResource(R.drawable.correct_color);
				break;
			case 4:
				FourthButton.setBackgroundResource(R.drawable.correct_color);
				break;
		}
		nextFlag = true;
	}

	//正解の番号を文字列に変換
	public String AnswerSelect(int aNumber) {
		String AnswerText = null;

		switch (aNumber) {
			case 1:
				AnswerText = answerList.get(nStatement).getFirst();
				break;
			case 2:
				AnswerText = answerList.get(nStatement).getSecond();
				break;
			case 3:
				AnswerText = answerList.get(nStatement).getThird();
				break;
			case 4:
				AnswerText = answerList.get(nStatement).getFourth();
				break;
		}
		return AnswerText;
	}

	//次の問題へ移行
	public void next() {
		nextFlag = false;
		++nStatement;
		if (nStatement < answerList.size()) {
			setQuestion(nStatement);
		} else {
			Intent CorrectIntent = new Intent(QuizActivity.this, QuizResult.class);
			CorrectIntent.putExtra("correct", correct);
			CorrectIntent.putExtra("nStatement", nStatement);
			startActivity(CorrectIntent);
		}
		FirstButton.setBackgroundResource(R.drawable.round_button);
		SecondButton.setBackgroundResource(R.drawable.round_button);
		ThirdButton.setBackgroundResource(R.drawable.round_button);
		FourthButton.setBackgroundResource(R.drawable.round_button);
	}

	@Override
	public void onClick(View view) {
	}

	public void CorrectAnimation(View view) {
		correctImage = (ImageView) findViewById(R.id.correctImage);
		correctImage.setImageResource(R.drawable.maru1);
//		correctImage.setVisibility(View.INVISIBLE);
		anim_start_correct = AnimationUtils.loadAnimation(this, R.anim.anim_start);
		anim_end = AnimationUtils.loadAnimation(this, R.anim.anim_end);
//		if (Animation) {
//			Animation = false;
			correctImage.startAnimation(anim_start_correct);
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
		IncorrectImage = (ImageView)findViewById(R.id.IncorrectImage);
		IncorrectImage.setImageResource(R.drawable.incorrect);
		anim_start_incorrect = AnimationUtils.loadAnimation(this, R.anim.anim_start);
		IncorrectImage.startAnimation(anim_start_incorrect);

	}






}
