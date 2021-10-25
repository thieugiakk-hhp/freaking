package com.unknown.myapplication;

import static android.widget.Toast.LENGTH_LONG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Context context = this;
    private TextView tvCalculation;
    private TextView tvScore;
    private Button btnTrue;
    private Button btnFalse;

    private ProgressBar progressBar;

    private boolean exactly = false;

    private boolean result;

    private CountDownTimer timer;

    private TextView tvTap;

    private ConstraintLayout playLayout;
    private ConstraintLayout endGameLayout;

    private int score;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("MiniGame", MODE_PRIVATE);

        tvCalculation = findViewById(R.id.tvCalculation);
        tvScore = findViewById(R.id.tvScore);
        
        progressBar = findViewById(R.id.progressBar);

        btnTrue = findViewById(R.id.btnTrue);
        btnFalse = findViewById(R.id.btnFalse);

        score = 0;

        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (result) {
                    Log.e("res", String.valueOf(result));
                    score += 10;
                    tvScore.setText(String.valueOf(score));
                    initFreakingMath();
                } else {
                    if (score > sharedPreferences.getInt("highScore", 0)) {
                        sharedPreferences.edit().putInt("highScore", score);
                    }
                    playLayout.setVisibility(View.INVISIBLE);
                    endGameLayout = findViewById(R.id.endGameLayout);
                    endGameLayout.setVisibility(View.VISIBLE);
                    setEndGameLayout();
                    Log.e("res", String.valueOf(result));
                    Toast.makeText(MainActivity.this, "You Lose", LENGTH_LONG);
                }
            }
        });

        btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!result) {
                    Log.e("res", String.valueOf(result));
                    score += 10;
                    tvScore.setText(String.valueOf(score));
                    initFreakingMath();
                } else {
                    if (score > sharedPreferences.getInt("highScore", 0)) {
                        sharedPreferences.edit().putInt("highScore", score);
                    }
                    playLayout.setVisibility(View.INVISIBLE);
                    endGameLayout = findViewById(R.id.endGameLayout);
                    endGameLayout.setVisibility(View.VISIBLE);
                    setEndGameLayout();
                    Log.e("res", String.valueOf(result));
                    Toast.makeText(MainActivity.this, "You Lose", LENGTH_LONG);
                }
            }
        });

        initFreakingMath();

        tvTap = findViewById(R.id.tvTap);

        tvTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTap.setVisibility(View.INVISIBLE);
                playLayout = findViewById(R.id.playLayout);
                playLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initFreakingMath() {
        FreakingMath freakingMath = FreakingMath.randomFreakingMath();
        result = freakingMath.isResult();
        tvCalculation.setText(freakingMath.getN1() + freakingMath.getSign() + freakingMath.getN2());

        CountDown();
    }

    private void CountDown() {
        timer = new CountDownTimer(7000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long s = millisUntilFinished / 1000;
                progressBar.setProgress((int) s);
            }

            @Override
            public void onFinish() {
                timer.cancel();
            }
        }.start();
    }

    private void setEndGameLayout() {
        TextView tvYourScore = findViewById(R.id.tvYourScore);
        TextView tvHighScore = findViewById(R.id.tvHighScore);

        sharedPreferences = getSharedPreferences("MiniGame", MODE_PRIVATE);

        tvYourScore.setText(String.valueOf(score));
        tvHighScore.setText(String.valueOf(sharedPreferences.getInt("highScore", score)));

        Button btnContinue = findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
