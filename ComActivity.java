package com.example.smartline2;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class ComActivity extends AppCompatActivity {

    private Button before2; // 클래스 필드로 선언
    private Button write;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com);

        // 이전으로 버튼
        before2 = findViewById(R.id.before2);

        before2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //글쓰기 버튼
        write = findViewById(R.id.write);

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComActivity.this, WriteActivity.class);
                startActivity(intent);
            }
        });

        // WindowInsets 설정
        View mainView = findViewById(R.id.main); // 레이아웃에 정의된 ID 확인 필요
        if (mainView != null) { // NullPointerException 방지
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }
    }
}
