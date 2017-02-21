package com.higamasa.juniorkanazawa;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.higamasa.juniorkanazawa.repository.QuizRepository;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
	private int correct = -1;
	private int sNumber = -1;
	private int position = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final QuizRepository repo = new QuizRepository(this);
		repo.loadQuiz();
		final ArrayList<YearQuiz> breakList = repo.getQuizList();

		ImageButton eButton = (ImageButton) findViewById(R.id.elementbutton);
		ImageButton juButton = (ImageButton) findViewById(R.id.juniorbutton);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int height = dm.heightPixels;
		int width = dm.widthPixels;
		float xdp = dm.xdpi;
		float ydp = dm.ydpi;

		eButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
//				repo.loadQuiz();
//				System.out.print("onClick");
				Intent intent = new Intent(MainActivity.this, QuizChoice.class);
				startActivity(intent);
			}
		});
		juButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, QuizChoice.class);
				startActivity(intent);
			}
		});
		SharedPreferences preferences = getSharedPreferences("file", MODE_PRIVATE);
		 correct = preferences.getInt("c", -1);
		 sNumber = preferences.getInt("s", -1);
		position = preferences.getInt("position",0);
		if (sNumber == breakList.get(position).getQuizzes().size())
			sNumber = -1;
			correct = -1;
		Log.d("ssss",String.valueOf(sNumber));
		if (correct > 0 || sNumber > 0) {
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
			alertDialog.setTitle("前回の戻る？");
			alertDialog.setPositiveButton("はい", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					Intent intent = new Intent(MainActivity.this, BreakQuizActivity.class);
					intent.putExtra("break",breakList.get(position).getQuizzes());
					intent.putExtra("i", sNumber);
					intent.putExtra("c", correct);
					startActivity(intent);
				}
			});
			alertDialog.setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					sNumber = -1;
					correct = -1;
				}
			});
			alertDialog.show();

		}
	}
}




