package com.higamasa.juniorkanazawa;

<<<<<<< HEAD
import com.higamasa.juniorkanazawa.entity.QuizEntity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
=======
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.higamasa.juniorkanazawa.entity.QuizEntity;

>>>>>>> 613ae08f5e6c6d6ff22dd6d4e3a33ea69f5cfca9
import java.util.ArrayList;

public class QuizActivity extends Activity implements View.OnClickListener {
	ArrayList<QuizEntity> answerList;	//問題list

	private int correct = 0;		//正解数
	private int nStatement = 0;		//statement番号

	private String Answer;			//正解の文字列

<<<<<<< HEAD
	private TextView Statement;
	private TextView Title;

	private Button FirstButton;
	private Button SecondButton;
	private Button ThirdButton;
	private Button FourthButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_quiz);

		setQuestion(nStatement);
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

		FirstButton = (Button) findViewById(R.id.button0);
		FirstButton.setText(answerList.get(sNumber).getFirst());
		SecondButton = (Button) findViewById(R.id.button1);
		SecondButton.setText(answerList.get(sNumber).getSecond());
		ThirdButton = (Button) findViewById(R.id.button2);
		ThirdButton.setText(answerList.get(sNumber).getThird());
		FourthButton = (Button) findViewById(R.id.button3);
		FourthButton.setText(answerList.get(sNumber).getFourth());

		int AnswerNumber = answerList.get(sNumber).getAnswer();
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
	public void AnswerJudge(View view){
		if (((Button)view).getText().equals(Answer)){
			//正解の時
			correct++;
			next(view);
		}
		else{
			//不正解の時
			next(view);
		}
	}

	//正解の番号を文字列に変換
	public String AnswerSelect(int aNumber){
		String AnswerText = null;

		switch(aNumber){
			case 1:
				AnswerText =  answerList.get(nStatement).getFirst();
				break;
			case 2:
				AnswerText =  answerList.get(nStatement).getSecond();
				break;
			case 3:
				AnswerText =  answerList.get(nStatement).getThird();
				break;
			case 4:
				AnswerText =  answerList.get(nStatement).getFourth();
				break;
		}
		return AnswerText;
	}

	//次の問題へ移行
	public void next(View view){
		++nStatement;
		if(nStatement < answerList.size()){
			setQuestion(nStatement);
		}
		else{
			Intent CorrectIntent = new Intent(QuizActivity.this,QuizResult.class);
			CorrectIntent.putExtra("correct",correct);
			CorrectIntent.putExtra("nStatement",nStatement);
			startActivity(CorrectIntent);
		}
	}
=======
    String[] QUIZLIST = {"100", "200", "300", "400"};
    String ANSWER = "300";
    int correct = 0;        //正解数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_quiz);
        Intent intent = getIntent();
        ArrayList<QuizEntity> answerlist = (ArrayList<QuizEntity>) intent.getSerializableExtra("all");
//		answerlist = intent.getStringArrayListExtra("all");
        Log.d("aaaaaa", String.format("%d", answerlist.get(0).getId()));

        ((Button) findViewById(R.id.button0)).setText(answerlist.get(15).getFirst());
        ((Button) findViewById(R.id.button1)).setText(answerlist.get(15).getSecond());
        ((Button) findViewById(R.id.button2)).setText(answerlist.get(15).getThird());
        ((Button) findViewById(R.id.button3)).setText(answerlist.get(15).getFourth());
        ((TextView)findViewById(R.id.statement)).setText(answerlist.get(0).getStatement());
        ((TextView)findViewById(R.id.title)).setText(answerlist.get(0).getTitle());
//	}
//
//	public void AnswerSelect(View view){
//		String text = ((Button)view).getText().toString();
//
//		for (int i=0; i < QUIZLIST.length; i++) {
//			if (text.equals(QUIZLIST[i])) {
//
//				view.setEnabled(false);
//				correct++;
//
//			}
//		}
    }
}
>>>>>>> 613ae08f5e6c6d6ff22dd6d4e3a33ea69f5cfca9

	@Override
	public void onClick(View view) {
	}
}
