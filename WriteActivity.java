package com.example.smartline2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WriteActivity extends AppCompatActivity {

    private EditText titleEditText, menuEditText, contentEditText;
    private Button saveButton;
    private Button before3;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_write);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button before3=findViewById(R.id.before3);

        before3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WriteActivity.this, ComActivity.class);
                startActivity(intent);
            }
        });

        titleEditText = findViewById(R.id.titleEditText);
        menuEditText = findViewById(R.id.menuEditText);
        contentEditText = findViewById(R.id.contentEditText);
        saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString();
            String menu = menuEditText.getText().toString();
            String content = contentEditText.getText().toString();

            if (title.isEmpty() || menu.isEmpty() || content.isEmpty()) {
                Toast.makeText(this, "모든 필드를 입력해주세요!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(WriteActivity.this, ComActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("menu", menu);
                intent.putExtra("content", content);
                startActivity(intent);
                Toast.makeText(this, "글이 저장되었습니다!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
