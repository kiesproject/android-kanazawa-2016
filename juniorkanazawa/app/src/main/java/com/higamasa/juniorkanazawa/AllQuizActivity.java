package com.higamasa.juniorkanazawa;

import android.content.Intent;
import android.app.Activity;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AllQuizActivity extends Activity implements View.OnClickListener {
    private boolean nextFlag = false;

    ArrayList<YearQuiz> allList;    //問題list
    private int correct = 0;    //正解数
    private int sNumber = 0;    //statement番号
    private int yStatement = 0;
//    private int[][] ysNumber = new int[2][50];

    private boolean sJudge = false;
    private int aNumberStatement = 0;

    private String answer;            //正解の文字列

    private TextView title;
    private TextView statement;

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
    private int answerNumber;

    private GoogleApiClient client;
    private String[] selectAnswer;
    private String firstAnswer;
    private String secondAnswer;
    private String thirdAnswer;
    private String fourthAnswer;

    private Button[] selectButton;
    private Button firstButton;
    private Button secondButton;
    private Button thirdButton;
    private Button fourthButton;
    private String[] selectAnswer2;
    private int[] idButton = {R.id.button0,R.id.button1, R.id.button2,R.id.button3};

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
		incorrectSound = soundPool.load(this, R.raw.incorrect,1);
        setQuestion(sNumber, yStatement);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //クイズの挿入
    public void setQuestion(int sNumber, int yNumber) {
        Intent ArrayIntent = getIntent();
        allList = (ArrayList<YearQuiz>) ArrayIntent.getSerializableExtra("All");

        correctImage = (ImageView) findViewById(R.id.correctImage);
        correctImage.setImageResource(R.drawable.maru200);
        correctImage.setVisibility(View.INVISIBLE);
        IncorrectImage = (ImageView) findViewById(R.id.IncorrectImage);
        IncorrectImage.setImageResource(R.drawable.incorrect200);
        IncorrectImage.setVisibility(View.INVISIBLE);

        title = (TextView) findViewById(R.id.title);
        title.setText(allList.get(yNumber).getQuizzes().get(sNumber).getTitle());

        statement = (TextView) findViewById(R.id.statement);
        statement.setText(allList.get(yNumber).getQuizzes().get(sNumber).getStatement());

        firstAnswer = allList.get(yNumber).getQuizzes().get(sNumber).getFirst();
        secondAnswer = allList.get(yNumber).getQuizzes().get(sNumber).getSecond();
        thirdAnswer = allList.get(yNumber).getQuizzes().get(sNumber).getThird();
        fourthAnswer = allList.get(yNumber).getQuizzes().get(sNumber).getFourth();

        String answerText = null;
        answerNumber = allList.get(yNumber).getQuizzes().get(sNumber).getAnswer();
        switch (answerNumber) {
            case 1:
                answerText = allList.get(yNumber).getQuizzes().get(sNumber).getFirst();
//              Log.d("aa", String.valueOf(sNumber[n]));
                break;
            case 2:
                answerText = allList.get(yNumber).getQuizzes().get(sNumber).getSecond();
//              Log.d("aa", String.valueOf(sNumber[n]));
                break;
            case 3:
                answerText = allList.get(yNumber).getQuizzes().get(sNumber).getThird();
//              Log.d("aa", String.valueOf(sNumber[n]));
                break;
            case 4:
                answerText = allList.get(yNumber).getQuizzes().get(sNumber).getFourth();
//              Log.d("aa", String.valueOf(sNumber[n]));
                break;
        }
        answer = answerText;

        //項目のランダム
        selectAnswer = new String[]{firstAnswer, secondAnswer, thirdAnswer, fourthAnswer};
        List<String> list = Arrays.asList(selectAnswer);
        Collections.shuffle(list);
        selectButton = new Button[]{firstButton, secondButton, thirdButton, fourthButton};
        selectAnswer2 = list.toArray(new String[list.size()]);
        for (int k = 0; k < 4; k++) {
            final int n = k;
            selectButton[k] = (Button) findViewById(idButton[k]);
            selectButton[k].setText(selectAnswer2[k]);
            selectButton[k].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    answerJudge(findViewById(idButton[n]));
                }
            });
        }
    }

    //正誤判定
    public void answerJudge(View view) {
        if (nextFlag) {
            next();
            return;
        }

        if (((Button) view).getText().equals(answer)) {
            //正解の時
            correct++;
            soundPool.play(correctSound, 1.0f, 1.0f, 0, 0, 1);
            CorrectAnimation(view);
        } else {
            //不正解の時
            soundPool.play(incorrectSound,2.0f,2.0f,0,0,1);
            IncorrectAnimation(view);
        }

        if (selectAnswer2[0] == answer){
            selectButton[0].setBackgroundResource(R.drawable.correct_color);
        }else if (selectAnswer2[1] == answer){
            selectButton[1].setBackgroundResource(R.drawable.correct_color);
        }else if (selectAnswer2[2] == answer){
            selectButton[2].setBackgroundResource(R.drawable.correct_color);
        }else if (selectAnswer2[3] == answer){
            selectButton[3].setBackgroundResource(R.drawable.correct_color);
        }
        nextFlag = true;
    }

    //次の問題へ移行
    public void next() {
        nextFlag = false;
        if (aNumberStatement<50) {
            aNumberStatement++;
            setQuestion(sNumber,yStatement);
        } else {
            Intent CorrectIntent = new Intent(AllQuizActivity.this, QuizResult.class);
            CorrectIntent.putExtra("correct", correct);
            CorrectIntent.putExtra("aNumberStatement", aNumberStatement);
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
        anim_start_correct = AnimationUtils.loadAnimation(this, R.anim.anim_start);
        
        anim_start_correct.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
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

