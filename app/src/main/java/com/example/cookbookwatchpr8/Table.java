package com.example.cookbookwatchpr8;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
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

public class Table extends AppCompatActivity {
    private TextView timeTextViewTable;
    private Handler handler;
    private Runnable timeUpdater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_table);

        // Инициализация времени
        timeTextViewTable = findViewById(R.id.timeTextViewTable);
        handler = new Handler();

        // Создаем Runnable для обновления времени
        timeUpdater = new Runnable() {
            @Override
            public void run() {
                updateTime();
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(timeUpdater);

        // Обработчик для стрелки назад
        ImageView backArrow = findViewById(R.id.imageView2);
        backArrow.setOnClickListener(v -> finish());

        setupDishClickListeners();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupDishClickListeners() {
        // Борщ
        View borschtLayout = findViewById(R.id.borschtContainer);
        borschtLayout.setOnClickListener(v -> {
            Intent intent = new Intent(Table.this, ShowItem.class);
            intent.putExtra("dish_name", "Борщ");
            intent.putExtra("dish_id", 1);
            startActivity(intent);
        });
    }

    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String currentTime = sdf.format(new Date());
        timeTextViewTable.setText(currentTime);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null && timeUpdater != null) {
            handler.removeCallbacks(timeUpdater);
        }
    }
}