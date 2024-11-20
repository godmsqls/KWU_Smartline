package com.example.smartline2;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StationDetailActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://openapi.sk.com/puzzle/congestion/subway/stations";
    private static final String API_KEY = "y082FNY0A326QSjo0ovp262NtYAVb6bm1zhtyOXo"; // Replace with your API key

    private TextView subwayDataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_station_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        subwayDataTextView = findViewById(R.id.subway_data);

        // Get stationName and line from Intent
        String stationName = getIntent().getStringExtra("stationName");
        String line = getIntent().getStringExtra("line"); // Optional, can default to a line like "1호선"

        if (stationName != null) {
            fetchSubwayCongestionData(stationName, line != null ? line : "1호선");
        } else {
            subwayDataTextView.setText("역 이름이 전달되지 않았습니다.");
        }
    }

    private void fetchSubwayCongestionData(String stationName, String line) {
        OkHttpClient client = new OkHttpClient();
        String url = BASE_URL + "?stationName=" + stationName + "&line=" + line;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("appKey", API_KEY) // Add API key in the header
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("StationDetailActivity", "API call failed", e);
                runOnUiThread(() -> subwayDataTextView.setText("API 호출 실패: " + e.getMessage()));
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    Log.d("StationDetailActivity", "API Response: " + responseData);

                    String parsedData = parseResponse(responseData);
                    runOnUiThread(() -> subwayDataTextView.setText(parsedData));
                } else {
                    Log.e("StationDetailActivity", "API call unsuccessful: " + response.code());
                    runOnUiThread(() -> subwayDataTextView.setText("API 호출 실패: " + response.code()));
                }
            }
        });
    }

    private String parseResponse(String responseData) {
        try {
            org.json.JSONObject jsonResponse = new org.json.JSONObject(responseData);
            org.json.JSONObject data = jsonResponse.getJSONObject("data");

            String stationName = data.getString("stationName");
            String line = data.getString("line");
            String congestion = data.getString("congestion");

            return "역 이름: " + stationName + "\n"
                    + "노선: " + line + "\n"
                    + "혼잡도: " + congestion;
        } catch (Exception e) {
            Log.e("StationDetailActivity", "JSON Parsing Error", e);
            return "데이터 처리 중 오류 발생";
        }
    }
}


