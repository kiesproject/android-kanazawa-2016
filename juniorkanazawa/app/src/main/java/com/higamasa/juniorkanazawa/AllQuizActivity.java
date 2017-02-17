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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AllQuizActivity extends Activity implements View.OnClickListener {
    private boolean nextFlag = false;

    ArrayList<YearQuiz> allList;    //問題list
    private int n = 0;
    private int correct = 0;        //正解数
    private int sNumber = 0;//statement番号
    private int yStatement = 0;
    private int nNumber[] = {};

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
    private boolean num[] = new boolean[50];
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
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
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //クイズの挿入
    public void setQuestion(int sNumber, int yNumber) {
        Intent ArrayIntent = getIntent();
        allList = (ArrayList<YearQuiz>) ArrayIntent.getSerializableExtra("All");
//        String.format("%d", allList.get(sNumber).getId());
        Random randomQuestion = new Random();
        Random randomYear = new Random();
//        sNumber = noRepeatRandom(sNumber);
//        for (int i = 0; i < 50; i++) {
//            n = randomQuestion.nextInt(50);
//            sNumber[i] = n;
//            int a = sNumber[i];
//            for (i = 0; i < 50; i++)
//                if (sNumber[i] == a)
//                    break;
//        }
        sNumber = randomQuestion.nextInt(50);
        yNumber = randomYear.nextInt(1);
//        for (int i = 0; i < 50; i++) {
//            num[i] = false;
//        Arrays.fill(num,false);

//        for (int i = 0;i<50;i++){
//            int p = randomQuestion.nextInt(50);
//            if (num[p] == false){
//                num[p] = true;
//            }
//        }

//        for (int i = 0;i<50;i++){
//            int n = randomQuestion.nextInt(50);
//            int temp = sNumber[i];
//            sNumber[i] = sNumber[n];
//            sNumber[n] = temp;
//        }
//        for (int i = 0;i<50;i++) {
//            if (!num[sNumber]) {


//        }

                correctImage = (ImageView) findViewById(R.id.correctImage);
                correctImage.setImageResource(R.drawable.maru200);
                correctImage.setVisibility(View.INVISIBLE);
                IncorrectImage = (ImageView) findViewById(R.id.IncorrectImage);
                IncorrectImage.setImageResource(R.drawable.incorrect200);
                IncorrectImage.setVisibility(View.INVISIBLE);

                Statement = (TextView) findViewById(R.id.statement);
                Statement.setText(allList.get(yNumber).getQuizzes().get(sNumber).getStatement());

                Title = (TextView) findViewById(R.id.title);
                Title.setText(allList.get(yNumber).getQuizzes().get(sNumber).getTitle());
        firstAnswer = allList.get(yNumber).getQuizzes().get(sNumber).getFirst();
        secondAnswer = allList.get(yNumber).getQuizzes().get(sNumber).getSecond();
        thirdAnswer = allList.get(yNumber).getQuizzes().get(sNumber).getThird();
        fourthAnswer = allList.get(yNumber).getQuizzes().get(sNumber).getFourth();


        String AnswerText = null;
        AnswerNumber = allList.get(yNumber).getQuizzes().get(sNumber).getAnswer();
                switch (AnswerNumber) {
                    case 1:
                        AnswerText = allList.get(yNumber).getQuizzes().get(sNumber).getFirst();
//                        Log.d("aa", String.valueOf(sNumber[n]));
                        break;
                    case 2:
                        AnswerText = allList.get(yNumber).getQuizzes().get(sNumber).getSecond();
//                        Log.d("aa", String.valueOf(sNumber[n]));
                        break;
                    case 3:
                        AnswerText = allList.get(yNumber).getQuizzes().get(sNumber).getThird();
//                        Log.d("aa", String.valueOf(sNumber[n]));
                        break;
                    case 4:
                        AnswerText = allList.get(yNumber).getQuizzes().get(sNumber).getFourth();
//                        Log.d("aa", String.valueOf(sNumber[n]));
                        break;
                }
                Answer = AnswerText;
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
//                FirstButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        AnswerJudge(findViewById(R.id.button0));
//
//                    }
//                });
//                SecondButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        AnswerJudge(findViewById(R.id.button1));
//                    }
//                });
//                ThirdButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        AnswerJudge(findViewById(R.id.button2));
//                    }
//                });
//                FourthButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        AnswerJudge(findViewById(R.id.button3));
//                    }
//                });

            }
//        num[sNumber] = true;
//        }



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


    //次の問題へ移行
    public void next() {
        nextFlag = false;
        if (sNumber < allList.get(yStatement).getQuizzes().size()) {
            setQuestion(sNumber,yStatement);
        } else {
            Intent CorrectIntent = new Intent(AllQuizActivity.this, QuizResult.class);
            CorrectIntent.putExtra("correct", correct);
            CorrectIntent.putExtra("sNumber", sNumber);
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
//        anim_end = AnimationUtils.loadAnimation(this, R.anim.anim_end);
////		if (Animation) {
////			Animation = false;
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
//
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
    public int[] noRepeatRandom(int sNumber[]) {
        int number[] = new int[50];
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            sNumber[i] = random.nextInt(50);
            int a = sNumber[i];
            for (i = 0; i < 50; i++)
                if (sNumber[i] == a)
                    break;
        }
        return sNumber;
//        for (int j = 0;j<50;j++) {
//            return sNumber[j] = number[j];
//        }
    }


}

