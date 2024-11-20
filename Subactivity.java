package com.example.smartline2;

import static com.example.smartline2.R.id.before;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Subactivity extends AppCompatActivity {

    private ListView listview;
    private SearchView stationsearchView;
    private ArrayAdapter<String> adapter;
//    private List<String> fullList;
    private List<String> fulllist;
    private List<String> filterList;
    private Button before1;
    private Button seoulfilter,gyeonggifilter,incheonfilter,chungcheongfilter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sub);

        //"이전화면으로" 버튼
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button before1=findViewById(R.id.before);

        before1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Subactivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

            // 컴포넌트 매핑 실시
//            SearchView searchView = (SearchView) findViewById(R.id.searchView);
//            ListView listView = (ListView) findViewById(R.id.listview);
//            seoulfilter=findViewById(R.id.seoulfilter);
//            gyeonggifilter=findViewById(R.id.gyeonggifilter);
//            incheonfilter=findViewById(R.id.incheonfilter);
//            chungcheongfilter=findViewById(R.id.chungcheongfilter);

        stationsearchView = findViewById(R.id.stationsearchview);
        listview = findViewById(R.id.listview);
        seoulfilter = findViewById(R.id.seoulfilter);
        gyeonggifilter = findViewById(R.id.gyeonggifilter);
        incheonfilter = findViewById(R.id.incheonfilter);
        chungcheongfilter = findViewById(R.id.chungcheongfilter);

            // 배열 선언 실시
        fulllist = Arrays.asList(
                "가능", "가산디지털단지", "간석", "개봉", "관악", "광명", "광운대", "구로", "구일", "군포",
                "금정", "금천구청", "남영", "노량진", "녹양", "녹천", "당정", "대방", "덕계", "덕정",
                "도봉", "도봉산", "도원", "도화", "독산", "동대문", "동두천", "동두천중앙", "동묘앞", "동암",
                "동인천", "두정", "망월사", "명학", "방학", "배방", "백운", "병점", "보산", "봉명",
                "부개", "부천", "부평", "서동탄", "서울역", "서정리", "석계", "석수", "성균관대", "세류",
                "세마", "소사", "소요산", "송내", "송탄", "수원", "시청", "신길", "신도림", "신설동",
                "신이문", "신창", "쌍용", "아산", "안양", "양주", "역곡", "연천", "영등포", "오류동",
                "오산대", "온수", "온양온천", "외대앞", "용산", "월계", "의왕", "의정부", "인천", "전곡",
                "제기동", "제물포", "종각", "종로3가", "종로5가", "주안", "중동", "지행", "직산", "진위",
                "창동", "천안", "청량리", "청산", "탕정", "평택", "평택지제", "화서", "회기", "회룡"
        );

        filterList=new ArrayList<>(fulllist);

            // 어댑터 선언 및 리스트 뷰에 지정
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filterList);
        listview.setAdapter(adapter);

            // 필터 버튼 클릭 리스너
            seoulfilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filterList("가산디지털단지","개봉","광운대","구로","구일","금천구청","남영","노량진","녹천","대방","도봉","도봉산","독산","동대문","동묘앞","방학","서울역","석계","시청","신길","신도림","신설동","신이문","영등포","오류동","온수","외대앞","용산","월계","제기동","종각","종로3가","종로5가","창동","청량리","회기");
                }
            });

            gyeonggifilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filterList("가능","관악","광명","군포","금정","녹양","당정","덕계","덕정","동두천","동두천중앙","망월사","명학","병점","보산","부천","서동탄","서정리","석수","성균관대","세류","세마","소사","소요산","송내","송탄","수원","안양","양주","역곡","연천","오산","오산대","의왕","의정부","전곡","중동","지행","진위","청산","평택","평택지제","화서","회룡");
                }
            });

            incheonfilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filterList("간석","도원","도화","동암","동인천","백운","부개","부평","인천","제물포","주안");
                }
            });

            chungcheongfilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filterList("두정","배방","봉명","성환","신창","쌍용","아산","온양온천","직산","천안","탕정");
                }
            });

            // searchView 이벤트 리스너
            stationsearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {

                    // [검색 진행 시 >> 단어 필터링]
                    adapter.getFilter().filter(newText);
                    return false;
                }
            });

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItem=adapter.getItem(position);

                        Intent intent = new Intent(Subactivity.this, StationDetailActivity.class);
                        intent.putExtra("stationName",selectedItem);
                        startActivity(intent);
                }
            });


//        catch (Exception e){
//            e.printStackTrace();
//        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void filterList(String...stations){
        filterList.clear();
        filterList.addAll(Arrays.asList(stations));
        adapter.notifyDataSetChanged();
    }
}
