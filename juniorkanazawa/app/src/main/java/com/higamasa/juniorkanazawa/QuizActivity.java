package com.higamasa.juniorkanazawa;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.text.BoringLayout;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizActivity extends Activity implements View.OnClickListener {
	private boolean nextFlag = false;
	private int correct = 0;		//正解数
	private int sNumber = 0;		//statement番号
	private int mNumber[] = new int[4];
	private int a = 0;
    private int[] idButton = {R.id.button0,R.id.button1, R.id.button2,R.id.button3};
    private String[] selectAnswer2;
	ArrayList<QuizEntity> answerList;    //問題list

	private String Answer;            //正解の文字列
	private String firstAnswer;
	private String secondAnswer;
	private String thirdAnswer;
	private String fourthAnswer;
	private String selectAnswer[];
	private TextView Statement;
	private TextView Title;


	private Button[] selectButton;
	private Button firstButton;
	private Button secondButton;
	private Button thirdButton;
	private Button fourthButton;
	private Button breakButton;

	private ImageView correctImage;
	private ImageView IncorrectImage;
	private Animation anim_start_correct;
	private Animation anim_start_incorrect;
	private SoundPool soundPool;
	private AudioAttributes audioAttributes;
	private int correctSound;
	private int incorrectSound;
	private int AnswerNumber;
	private int position;


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
        protected void onStop () {
            super.onStop();
            SharedPreferences prefer = getSharedPreferences("file", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefer.edit();
            editor.putInt("s", sNumber);
			editor.putInt("c", correct);
			editor.putInt("positon",position);
            editor.commit();
			SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
			editor = pref.edit();
			editor.clear().commit();

        }


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if(keyCode == KeyEvent.KEYCODE_BACK){
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
			alertDialog.setTitle("検定を中断しますか?");
			alertDialog.setMessage("検定結果は保存されません");
			alertDialog.setPositiveButton("はい", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					finish();
				}
			});
			alertDialog.setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
				}
			});
			alertDialog.show();
			return super.onKeyDown(keyCode, event);
		}else{
			return false;
		}
	}
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.content_quiz);
		Intent intent = getIntent();
		position = intent.getIntExtra("position",position);
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
		incorrectSound = soundPool.load(this, R.raw.incorrect, 1);
		breakButton = (Button)findViewById(R.id.breakButton);
		breakButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AlertDialog.Builder breakDialog = new AlertDialog.Builder(QuizActivity.this);
                breakDialog.setTitle("検定を中断しますか?");
                breakDialog.setMessage("検定結果は保存されません");
                breakDialog.setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                breakDialog.setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                breakDialog.show();


            }
		});
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

		selectAnswer = new String[]{firstAnswer,secondAnswer,thirdAnswer,fourthAnswer};
        List<String> list = Arrays.asList(selectAnswer);
        Collections.shuffle(list);
        selectButton = new Button[]{firstButton,secondButton,thirdButton,fourthButton};
        selectAnswer2 = list.toArray(new String[list.size()]);
        for (int i = 0;i < 4;i++){
            final int n = i;
            selectButton[i] = (Button)findViewById(idButton[i]);
            selectButton[i].setText(selectAnswer2[i]);
            selectButton[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AnswerJudge(findViewById(idButton[n]));
                }
            });
        }


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
        if (selectAnswer2[0] == Answer){
            selectButton[0].setBackgroundResource(R.drawable.correct_color);
        }else if (selectAnswer2[1] == Answer){
            selectButton[1].setBackgroundResource(R.drawable.correct_color);
        }else if (selectAnswer2[2] == Answer){
            selectButton[2].setBackgroundResource(R.drawable.correct_color);
        }else if (selectAnswer2[3] == Answer){
            selectButton[3].setBackgroundResource(R.drawable.correct_color);
        }

		nextFlag = true;
	}

	//正解の番号を文字列に変換
	public String AnswerSelect(int answerNumber){
		String answerText = null;

		switch(answerNumber){
			case 1:
				answerText = firstAnswer;
				break;
			case 2:
				answerText = secondAnswer;
				break;
			case 3:
				answerText = thirdAnswer;
				break;
			case 4:
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
