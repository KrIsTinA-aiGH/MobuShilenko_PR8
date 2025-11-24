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
                handler.postDelayed(this, 1000); // Обновлять каждую секунду
            }
        };

        // Запускаем обновление времени
        handler.post(timeUpdater);

        // Находим ImageView и добавляем обработчик нажатия
        ImageView backArrow = findViewById(R.id.imageView2);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Переход на MainActivity
                Intent intent = new Intent(Table.this, MainActivity.class);
                startActivity(intent);
                finish(); // Закрываем текущую активность
            }
        });

        // Обработчик для борща
        View borschtLayout = findViewById(R.id.imageView3).getParent();
        borschtLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Переход на ShowItem для борща
                Intent intent = new Intent(Table.this, ShowItem.class);
                intent.putExtra("dish_name", "Борщ");
                intent.putExtra("dish_id", 1);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
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
        // Останавливаем обновление при закрытии активности
        if (handler != null && timeUpdater != null) {
            handler.removeCallbacks(timeUpdater);
        }
    }
}