package com.example.zatrzymaj_na_7;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvHits, tvCounter, tvMessage;
    private Button btnStart, btnStop;

    private int currentDigit = 0;
    private int hitsCount = 0;
    private long delayMillis = 500;

    private Handler handler;
    private Runnable counterRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvHits = findViewById(R.id.tvHits);
        tvCounter = findViewById(R.id.tvCounter);
        tvMessage = findViewById(R.id.tvMessage);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);

        handler = new Handler(Looper.getMainLooper());

        counterRunnable = new Runnable() {
            @Override
            public void run() {
                currentDigit = (currentDigit + 1) % 10;
                tvCounter.setText(String.valueOf(currentDigit));
                handler.postDelayed(this, delayMillis);
            }
        };

        btnStart.setOnClickListener(v -> startGame());
        btnStop.setOnClickListener(v -> stopGame());
    }

    private void startGame() {
        currentDigit = 0;
        tvCounter.setText("0");
        tvMessage.setText("");

        btnStart.setEnabled(false);
        btnStop.setEnabled(true);

        handler.postDelayed(counterRunnable, delayMillis);
    }

    private void stopGame() {
        handler.removeCallbacks(counterRunnable);

        btnStart.setEnabled(true);
        btnStop.setEnabled(false);

        if (currentDigit == 7) {
            hitsCount++;
            tvHits.setText("Trafienia: " + hitsCount);
            tvMessage.setText("Trafienie!");

            if (delayMillis > 200) {
                delayMillis -= 50;
            }
        } else {
            tvMessage.setText("Pudło! Zatrzymano na " + currentDigit);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(counterRunnable);
    }
}
