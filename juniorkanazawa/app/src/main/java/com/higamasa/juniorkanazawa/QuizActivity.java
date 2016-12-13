<<<<<<< HEAD
package com.higamasa.juniorkanazawa;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

	String[] QUIZLIST = {"100", "200","300","400"};
	String ANSWER = "300";
	int correct = 0;		//正解数

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);

		ArrayList<String> Answerlist = new ArrayList<String>();
		((Button)findViewById(R.id.button0)).setText(Answerlist.get(0));
		((Button)findViewById(R.id.button1)).setText(Answerlist.get(1));
		((Button)findViewById(R.id.button2)).setText(Answerlist.get(2));
		((Button)findViewById(R.id.button3)).setText(Answerlist.get(3));

	}

	public void AnswerSelect(View view){
		String text = ((Button)view).getText().toString();

		for (int i=0; i < QUIZLIST.length; i++) {
			if (text.equals(QUIZLIST[i])) {

				view.setEnabled(false);
				correct++;

			}
		}
	}
}
=======
//package com.higamasa.juniorkanazawa;
//
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.Button;
//
//public class QuizActivity extends AppCompatActivity {
//
//	String[] Quiz = {1,2,3,4};
//	int correct = 0;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//
//
//	}
//
//	public void onButton(View view){
//		String text = ((Button)view).getText().toString();
//
//
//	}
//
//}
>>>>>>> origin/develop
