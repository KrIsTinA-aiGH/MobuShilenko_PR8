package com.example.cookbookwatchpr8;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ShowItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_item);

        // Стрелка назад
        ImageView backArrow = findViewById(R.id.backArrowShowItem);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Закрываем текущую активность и возвращаемся назад
            }
        });

        // Получаем данные о блюде
        Intent intent = getIntent();
        if (intent != null) {
            String dishName = intent.getStringExtra("dish_name");
            int dishId = intent.getIntExtra("dish_id", 0);

            // Здесь можно отобразить информацию о блюде
            // Например, установить заголовок
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
}