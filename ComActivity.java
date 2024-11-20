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
package com.example.smartline2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class ComActivity extends AppCompatActivity {

    private Button before2;
    private Button write;
    private Spinner menuFilterSpinner;
    private LinearLayout postContainer;
    private ArrayList<String> postList = new ArrayList<>();
    private ArrayList<String> filteredList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com);

        before2 = findViewById(R.id.before2);
        write = findViewById(R.id.write);
        menuFilterSpinner = findViewById(R.id.menuFilterSpinner);
        postContainer = findViewById(R.id.postContainer);

        // Spinner adapter
        ArrayAdapter<CharSequence> filterAdapter = ArrayAdapter.createFromResource(this,
                R.array.menu_options, android.R.layout.simple_spinner_item);
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        menuFilterSpinner.setAdapter(filterAdapter);

        // Back button
        before2.setOnClickListener(v -> {
            Intent intent = new Intent(ComActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Write button
        write.setOnClickListener(v -> {
            Intent intent = new Intent(ComActivity.this, WriteActivity.class);
            startActivity(intent);
        });

        // Check for new post data from Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("title")) {
            String title = intent.getStringExtra("title");
            String menu = intent.getStringExtra("menu");
            String content = intent.getStringExtra("content");
            String post = "제목: " + title + "\n메뉴: " + menu + "\n내용: " + content;
            postList.add(post);
            filteredList.add(post);
            Collections.reverse(postList); // Ensure oldest post is at the bottom
            updatePostDisplay();
        }

        // Spinner selection listener for filtering
        menuFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedMenu = parent.getItemAtPosition(position).toString();
                filterPosts(selectedMenu);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void updatePostDisplay() {
        postContainer.removeAllViews(); // Clear existing views
        for (String post : filteredList) {
            TextView postView = new TextView(this);
            postView.setText(post);
            postView.setTextSize(16);
            postView.setPadding(16, 16, 16, 16);
            postView.setBackgroundResource(android.R.color.white);
            postContainer.addView(postView);
        }
    }

    private void filterPosts(String selectedMenu) {
        filteredList.clear();
        if (selectedMenu.equals("전체")) {
            filteredList.addAll(postList);
        } else {
            filteredList.addAll(postList.stream()
                    .filter(post -> post.contains("메뉴: " + selectedMenu))
                    .collect(Collectors.toList()));
        }
        updatePostDisplay();
    }
}

