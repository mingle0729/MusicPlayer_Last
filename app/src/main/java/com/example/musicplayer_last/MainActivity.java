package com.example.musicplayer_last;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private FrameLayout frameLayout;
    private RecyclerView recyclerView, recyclerViewLike;

    ///////////////////////////////////////////////
    private ArrayList<MusicData> musicDataArrayList;
    ///////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //1
        find();
        //2 현재 액티비티에 있는 프레임레이아웃에 프래그먼트 지정하기
        replaceFrag();
        //3 권한 설정
        requestPermissionFunc();
        //4 어뎁터생성
        MusicAdapter musicAdapter = new MusicAdapter(getApplicationContext());
        //5 리사이틀러뷰에서
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        //6 리사이클러뷰에다 리니어레이아웃메니저 적용
        recyclerView.setAdapter(musicAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        //7 arrayList<musicData>를 가져와서 musicAdapter에 적용시켜야 된다.
        musicDataArrayList = findMusic(getApplicationContext());
        musicAdapter.setMusicList(musicDataArrayList);
        musicAdapter.notifyDataSetChanged();

        //8

    }

    private ArrayList<MusicData> findMusic(Context context) {
        ArrayList<MusicData> sdCardList = new ArrayList<>();

        String[] data = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.DURATION};

        // 특정 폴더에서 음악 가져오기
//        String selection = MediaStore.Audio.Media.DATA + " like ? ";
//        String selectionArqs = new String[]{"%MusicList%"}

        // 전체 영역에서 음악 가져오기
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                data, null, null, data[2] + " ASC");

        if (cursor != null) {
            while (cursor.moveToNext()) {

                // 음악 데이터 가져오기
                String id = cursor.getString(cursor.getColumnIndex(data[0]));
                String artist = cursor.getString(cursor.getColumnIndex(data[1]));
                String title = cursor.getString(cursor.getColumnIndex(data[2]));
                String albumArt = cursor.getString(cursor.getColumnIndex(data[3]));
                String duration = cursor.getString(cursor.getColumnIndex(data[4]));

                MusicData mData = new MusicData(id, artist, title, albumArt, duration, 0, 0);

                sdCardList.add(mData);
            }
        }

        return sdCardList;

    }

    //외부파일을 접근하려고 할 때 허용하쉴?
    public void requestPermissionFunc() {
        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                MODE_PRIVATE);
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