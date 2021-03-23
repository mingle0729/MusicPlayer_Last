package com.example.musicplayer_last;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private FrameLayout frameLayout;
    private RecyclerView recyclerView, recyclerViewLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        find();
        //현재 액티비티에 있는 프레임레이아웃에 프래그먼트 지정하기
        replaceFrag();
    }

    public void replaceFrag() {
        //프래그먼트 생성
        Player player = new Player();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.frameLayout,player);
        ft.commit();

    }

    public void find() {
        drawerLayout = findViewById(R.id.drawerLayout);
        frameLayout = findViewById(R.id.frameLayout);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerViewLike = findViewById(R.id.recyclerViewLike);
    }
}