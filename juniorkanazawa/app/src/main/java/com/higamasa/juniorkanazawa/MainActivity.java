package com.higamasa.juniorkanazawa;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.higamasa.juniorkanazawa.repository.QuizRepository;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final QuizRepository repo = new QuizRepository(this);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//				repo.loadQuiz();
//				System.out.print("onClick");
                Intent intent = new Intent(MainActivity.this, QuizChoice.class);
                startActivity(intent);
            }
        });
    }
}