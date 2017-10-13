package com.higamasa.juniorkanazawa;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

/**
 * Created by higamasa on 2017/03/19.
 */

public class TutoActivity extends Activity implements View.OnClickListener{

	private Button backButton;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tutorial_quiz);

		backButton = (Button)findViewById(R.id.backButton);
		backButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(TutoActivity.this,QuizChoice.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onClick(View v) {

	}
}
