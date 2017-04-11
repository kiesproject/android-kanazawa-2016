package com.higamasa.juniorkanazawa;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.preference.PreferenceManager;
import android.renderscript.Sampler;
import android.support.annotation.Size;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.widget.Button;
import android.content.Intent;
import com.higamasa.juniorkanazawa.entity.QuizEntity;
import android.app.Activity;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class FigureQuiz extends Activity implements View.OnClickListener {
    private boolean nextFlag = false;
    private int correct = 0;        //正解数
    private int sNumber = 0;        //statement番号
    private int[] idButton = {R.id.button0, R.id.button1, R.id.button2, R.id.button3};
    private int[] imageIdButton = {R.id.figureButton1,R.id.figureButton2,R.id.figureButton3,R.id.figureButton4};

    ArrayList<QuizEntity> answerList;    //問題list

    private String Answer;            //正解の文字列
    private TextView title;

    private TextView statement;
    private String answerText;            //正解の文字列
    private int answerNumber;
    private String selectAnswer[];
    private String firstAnswer;
    private String secondAnswer;
    private String thirdAnswer;
    private String fourthAnswer;
    private String[] selectAnswer2;

    private ImageButton[] imageSelectButton;
    private ImageButton firstImage;
    private ImageButton secondImage;
    private ImageButton thirdImage;
    private ImageButton fourthImage;
    private Button[] selectButton;
    private Button firstButton;
    private Button secondButton;
    private Button thirdButton;
    private Button fourthButton;
    private Button breakButton;

    private ImageView correctImage;
    private ImageView IncorrectImage;
    private ImageView quizImage;
    private Animation anim_start_correct;
    private Animation anim_start_incorrect;
    private SoundPool soundPool;
    private AudioAttributes audioAttributes;
    private int correctSound;
    private int incorrectSound;
    private int AnswerNumber;
    private int position;
    private int schoolJudge;
    private int imageId;
    private int quizId;
    private String firstImageId;
    private String secondImageId;
    private String thirdImageId;
    private String fourthImageId;
    private String[] imageButtonResource;
    private String[] imageButtonResource2;
    private int[] imageButtonResource3;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//		return super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (nextFlag) {
//					CorrectAnimation();

                next();

            }
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
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
        } else {
            return false;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.figure_quiz);
        Intent intent = getIntent();
        position = intent.getIntExtra("position", position);
        schoolJudge = intent.getIntExtra("schoolJudge",schoolJudge);
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

        breakButton = (Button) findViewById(R.id.breakButton);
        breakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder breakDialog = new AlertDialog.Builder(FigureQuiz.this);
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
    answerList = (ArrayList<QuizEntity>) ArrayIntent.getSerializableExtra("figureQuiz");
        String.format("%d", answerList.get(sNumber).getId());
            quizImage = (ImageView)findViewById(R.id.figureView);
           imageId = getResources().getIdentifier(answerList.get(sNumber).getDrawable(), "drawable", getPackageName());
            quizImage.setImageResource(imageId);
            correctImage = (ImageView) findViewById(R.id.correctImage);
            correctImage.setImageResource(R.drawable.maru200);
            correctImage.setVisibility(View.INVISIBLE);
            IncorrectImage = (ImageView) findViewById(R.id.IncorrectImage);
            IncorrectImage.setImageResource(R.drawable.incorrect200);
            IncorrectImage.setVisibility(View.INVISIBLE);
            statement = (TextView) findViewById(R.id.statement);
            statement.setText(answerList.get(sNumber).getStatement());
            title = (TextView) findViewById(R.id.title);
            title.setText(answerList.get(sNumber).getTitle());
            firstAnswer = answerList.get(sNumber).getFirst();
            secondAnswer = answerList.get(sNumber).getSecond();
            thirdAnswer = answerList.get(sNumber).getThird();
            fourthAnswer = answerList.get(sNumber).getFourth();
            answerNumber = answerList.get(sNumber).getAnswer();
            answerText = AnswerSelect(answerNumber);
            selectAnswer = new String[]{firstAnswer, secondAnswer, thirdAnswer, fourthAnswer};
            List<String> list = Arrays.asList(selectAnswer);
            Collections.shuffle(list);
            selectButton = new Button[]{firstButton, secondButton, thirdButton, fourthButton};
            selectAnswer2 = list.toArray(new String[list.size()]);
            for (int i = 0; i < 4; i++) {
                final int n = i;
                selectButton[i] = (Button) findViewById(idButton[i]);
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

        if (((Button) view).getText().equals(answerText)) {
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

        }


        nextFlag = true;
    }

    //正解の番号を文字列に変換
    public String AnswerSelect(int answerNumber) {
        String answerText = null;
        switch (answerNumber) {
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
    public void next() {
        nextFlag = false;
        ++sNumber;
        if (sNumber < answerList.size()) {
            setQuestion(sNumber);
        } else {
            if (schoolJudge == 0) {
                Intent CorrectIntent = new Intent(FigureQuiz.this, QuizResult.class);
                CorrectIntent.putExtra("correct", correct);
                CorrectIntent.putExtra("sNumber", sNumber);
                startActivity(CorrectIntent);
            }else if (schoolJudge == 1){
                Intent CorrectIntent = new Intent(FigureQuiz.this, ElementalResult.class);
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
//		correctImage.setVisibility(View.INVISIBLE);
        anim_start_correct = AnimationUtils.loadAnimation(this, R.anim.anim_start);
        anim_start_correct.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
//				correctImage.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (selectAnswer2[0] == answerText) {
                    selectButton[0].setBackgroundResource(R.drawable.correct_color);
                } else if (selectAnswer2[1] == answerText) {
                    selectButton[1].setBackgroundResource(R.drawable.correct_color);
                } else if (selectAnswer2[2] == answerText) {
                    selectButton[2].setBackgroundResource(R.drawable.correct_color);
                } else if (selectAnswer2[3] == answerText) {
                    selectButton[3].setBackgroundResource(R.drawable.correct_color);
                }
                correctImage.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        correctImage.startAnimation(anim_start_correct);
//		nextFlag = true;
    }

    public void IncorrectAnimation(View view) {
        anim_start_incorrect = AnimationUtils.loadAnimation(this, R.anim.anim_start);
        anim_start_incorrect.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (selectAnswer2[0] == answerText) {
                    selectButton[0].setBackgroundResource(R.drawable.correct_color);
                } else if (selectAnswer2[1] == answerText) {
                    selectButton[1].setBackgroundResource(R.drawable.correct_color);
                } else if (selectAnswer2[2] == answerText) {
                    selectButton[2].setBackgroundResource(R.drawable.correct_color);
                } else if (selectAnswer2[3] == answerText) {
                    selectButton[3].setBackgroundResource(R.drawable.correct_color);
                }
                IncorrectImage.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        IncorrectImage.startAnimation(anim_start_incorrect);
    }



}

