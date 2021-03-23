package com.example.musicplayer_last;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class Player extends Fragment implements View.OnClickListener {
    private ImageView ivAlbum;
    private TextView tvPlayCount, tvArtist, tvTitle, tvCurrentTime, tvDuration;
    private SeekBar seekBar;
    private ImageButton ibPlay, ibPrevious, ibNext, ibLike;


    // 프래그먼트에서 정착된 액티비티를 가져올 수 있어.
    private MainActivity mainActivity; // 전체 액티비티는 드로워레이아웃이니 거기에 접근,저장하기 위해 메인 액티비티를 가져온 것임.

    private MediaPlayer mediaPlayer = new MediaPlayer();//노래를 등록하기 위해 선언한 객체. 노래를 재생시켜주는 기능을 함.

    private int index; //노래를 들을 위치를 지정
    private MusicData musicData = new MusicData(); // 데이터를 가져오기 위해서
    private ArrayList<MusicData> likeArrayList = new ArrayList<>(); // 좋아요 리스트 가져오기 ..? 어ㅐ 가져왓는진 몰라,,,
    // private MusicAdapter musicAdapter; // 어뎁터를 왜 가져왔지..?


    //Context : 화면과 컨트롤러 클래스의 정보를 모두 가지고 있는 변수
    @Override // 프레그먼트를 메인에 딱 붙여주는 함수
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mainActivity = (MainActivity) context;
    }

    @Override // 프레그먼트를 메인에서 떨어지게 하는 함수
    public void onDetach() {
        super.onDetach();
        this.mainActivity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.player, container, false);

        findFunc(view);


        return view;
    }

    public void findFunc(View view) {
        ivAlbum = view.findViewById(R.id.ivAlbum);
        tvPlayCount = view.findViewById(R.id.tvPlayCount);
        tvArtist = view.findViewById(R.id.tvArtist);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvCurrentTime = view.findViewById(R.id.tvCurrentTime);
        tvDuration = view.findViewById(R.id.tvDuration);
        seekBar = view.findViewById(R.id.seekBar);
        ibPlay = view.findViewById(R.id.ibPlay);
        ibPrevious = view.findViewById(R.id.ibPrevious);
        ibNext = view.findViewById(R.id.ibNext);
        ibLike = view.findViewById(R.id.ibLike);


        ibPlay.setOnClickListener(this); // this.를 준 이유: 이벤트끼리만 묶기 위해서
        ibPrevious.setOnClickListener(this);
        ibNext.setOnClickListener(this);
        ibLike.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibPlay:

                break;
            case R.id.ibPrevious:

                break;
            case R.id.ibNext:

                break;
            case R.id.ibLike:

                break;

        }
    }
}
