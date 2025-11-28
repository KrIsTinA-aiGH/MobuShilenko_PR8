package com.example.cookbookwatchpr8;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ShowItem extends AppCompatActivity {

    private TextView timeTextView;
    private Handler timeHandler;
    private Runnable timeRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_item);

        // Инициализация TextView для времени
        timeTextView = findViewById(R.id.timeTextView);

        // Запуск обновления времени
        startTimeUpdates();

        // Стрелка назад
        ImageView backArrow = findViewById(R.id.backArrowShowItem);
        backArrow.setOnClickListener(v -> {
            finish(); // Закрываем текущую активность и возвращаемся назад
        });

        Intent intent = getIntent();
        if (intent != null) {
            String dishName = intent.getStringExtra("dish_name");

            // Устанавливаем название блюда в TextView
            TextView dishNameTextView = findViewById(R.id.textView7);
            if (dishName != null) {
                dishNameTextView.setText(dishName);
            }

            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(dishName);
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void startTimeUpdates() {
        timeHandler = new Handler();
        timeRunnable = new Runnable() {
            @Override
            public void run() {
                updateTime();
                // Обновляем время каждую секунду
                timeHandler.postDelayed(this, 1000);
            }
        };
        // Запускаем обновление времени
        timeHandler.post(timeRunnable);
    }

    private void updateTime() {
        // Форматируем текущее время
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String currentTime = timeFormat.format(new Date());

        // Устанавливаем время в TextView
        timeTextView.setText(currentTime);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Останавливаем обновление времени при уничтожении активности
        if (timeHandler != null && timeRunnable != null) {
            timeHandler.removeCallbacks(timeRunnable);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Останавливаем обновление времени когда активность не на переднем плане
        if (timeHandler != null && timeRunnable != null) {
            timeHandler.removeCallbacks(timeRunnable);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Возобновляем обновление времени когда активность снова на переднем плане
        if (timeHandler != null && timeRunnable != null) {
            timeHandler.post(timeRunnable);
        }
    }
}