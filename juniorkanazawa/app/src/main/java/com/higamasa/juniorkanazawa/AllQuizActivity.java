package com.higamasa.juniorkanazawa;

import android.content.DialogInterface;
import android.content.Intent;
import android.app.Activity;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
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

    private String Answer;            //正解の文字列

    private TextView Title;
    private TextView Statement;

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
    private int schoolJudge;

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
    private Button breakButton;
    private int[] idButton = {R.id.button0, R.id.button1, R.id.button2, R.id.button3};
    private int[][] repeat = new int[11][50];

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//		return super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (nextFlag) {
                next();
            }
        }
        return true;
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
        audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build();
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                .setMaxStreams(2)
                .build();
        Intent intent = getIntent();
        schoolJudge = intent.getIntExtra("schoolJudge",schoolJudge);
        correctSound = soundPool.load(this, R.raw.correct, 1);
        incorrectSound = soundPool.load(this, R.raw.incorrect, 1);
        setQuestion(sNumber, yStatement);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        breakButton = (Button) findViewById(R.id.breakButton);
        breakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder breakDialog = new AlertDialog.Builder(AllQuizActivity.this);
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
        yNumber = randomYear.nextInt(11);
        sNumber = randomQuestion.nextInt(allList.get(yNumber).getQuizzes().size());
        Log.e("a", String.valueOf(yNumber));
        Log.e("b", String.valueOf(sNumber));

        for (int n = 0; n<11;n++){
            for (int s = 0; s<allList.get(yNumber).getQuizzes().size();s++){
                if (repeat[n][s] == allList.get(yNumber).getQuizzes().get(sNumber).getId()){
                    Random random = new Random();
                    Random random1 = new Random();
                    int m = yNumber;
                    yNumber = random.nextInt(11);
                    sNumber = random1.nextInt(allList.get(yNumber).getQuizzes().size());
                    Log.e("de", String.valueOf(yNumber));
                    Log.e("ba", String.valueOf(sNumber));
                    if (yNumber <= m){
                        n = 0;
                        Log.e("w", String.valueOf(yNumber));
                        Log.e("ww", String.valueOf(sNumber));
                    }
                }
            }
        }
        for (int i = 0 ; i < 11; i++){
            for (int j = 0 ; j < allList.get(yNumber).getQuizzes().size() ; j++){
                if (i == yNumber && j == sNumber){
                    repeat[i][j] = allList.get(yNumber).getQuizzes().get(sNumber).getId();
                }
            }
        }
        Log.e("aa", String.valueOf(yNumber));
        Log.e("bb", String.valueOf(sNumber));

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

        if (((Button) view).getText().equals(Answer)) {
            //正解の時
            correct++;
            soundPool.play(correctSound, 1.0f, 1.0f, 0, 0, 1);
            CorrectAnimation(view);
            final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
            scrollView.post(new Runnable() {
                public void run() {
                    scrollView.fullScroll(ScrollView.FOCUS_UP);
                }
            });
        } else {
            //不正解の時
            soundPool.play(incorrectSound, 2.0f, 2.0f, 0, 0, 1);
            IncorrectAnimation(view);
            final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
            scrollView.post(new Runnable() {
                public void run() {
                    scrollView.fullScroll(ScrollView.FOCUS_UP);
                }
            });
        }

        if (selectAnswer2[0] == Answer) {
            selectButton[0].setBackgroundResource(R.drawable.correct_color);
        } else if (selectAnswer2[1] == Answer) {
            selectButton[1].setBackgroundResource(R.drawable.correct_color);
        } else if (selectAnswer2[2] == Answer) {
            selectButton[2].setBackgroundResource(R.drawable.correct_color);
        } else if (selectAnswer2[3] == Answer) {
            selectButton[3].setBackgroundResource(R.drawable.correct_color);
        }
        nextFlag = true;
    }

    //次の問題へ移行
    public void next() {
        nextFlag = false;
        ++sNumber;
        if (sNumber < 50) {
            setQuestion(sNumber, yStatement);

        } else {
            if (schoolJudge == 0) {
                Intent CorrectIntent = new Intent(AllQuizActivity.this, QuizResult.class);
                CorrectIntent.putExtra("correct", correct);
                CorrectIntent.putExtra("sNumber", sNumber);
                startActivity(CorrectIntent);
            }else if (schoolJudge == 1){
                Intent CorrectIntent = new Intent(AllQuizActivity.this, ElementalResult.class);
                CorrectIntent.putExtra("correct", correct);
                CorrectIntent.putExtra("sNumber", sNumber);
                startActivity(CorrectIntent);
            }
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


    public void noRepeatRandom(int sNumber, int yNumber ) {

    }



}

