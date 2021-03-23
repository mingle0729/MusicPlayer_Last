package com.example.musicplayer_last;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.CustomerViewHolder> {
    private Context context;
    private ArrayList<MusicData> musicList;


    // 1생성자
    public MusicAdapter(Context context, ArrayList<MusicData> musicList) {
        this.context = context;
        musicList = musicList;
    }

    public MusicAdapter(Context context) {
        this.context = context;
    }


    //2 setters값 주기
    public void setMusicList(ArrayList<MusicData> musicList) {
        this.musicList = musicList;
    }

    //3 리사이클러뷰에 들어갈 항목 뷰를 inflater한다. /항목들을 찾아서 매치시킨다
    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //화면ㅇ르 만든다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        CustomerViewHolder viewHolder = new CustomerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        //앨범 이미지를 비트맵으로 만들기
        Bitmap albumImg = getAlbumImg(context, Long.parseLong(musicList.get(position).getAlbumArt()), 200);
        if (albumImg != null) {
            holder.albumArt.setImageBitmap(albumImg);
        }

        // recyclerviewer에 보여줘야할 정보 세팅
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        holder.title.setText(musicList.get(position).getTitle());
        holder.artist.setText(musicList.get(position).getArtist());
        holder.duration.setText(simpleDateFormat.format(Integer.parseInt(musicList.get(position).getDuration())));

    }

    private Bitmap getAlbumImg(Context context, Long parseInt, int i) {
        BitmapFactory.Options options = new BitmapFactory.Options();

        /*컨텐트 프로바이더(Content Provider)는 앱 간의 데이터 공유를 위해 사용됨.
        특정 앱이 다른 앱의 데이터를 직접 접근해서 사용할 수 없기 때문에
        무조건 컨텐트 프로바이더를 통해 다른 앱의 데이터를 사용해야만 한다.
        다른 앱의 데이터를 사용하고자 하는 앱에서는 URI를 이용하여 컨텐트 리졸버(Content Resolver)를 통해
        다른 앱의 컨텐트 프로바이더에게 데이터를 요청하게 되는데
        요청받은 컨텐트 프로바이더는 URI를 확인하고 내부에서 데이터를 꺼내어 컨텐트 리졸버에게 전달한다.
        */

        ContentResolver contentResolver = context.getContentResolver();

        // 앨범아트는 uri를 제공하지 않으므로, 별도로 생성.
        Uri uri = Uri.parse("content://media/external/audio/albumart/" + parseInt);
        if (uri != null){
            ParcelFileDescriptor fd = null;
            try{
                fd = contentResolver.openFileDescriptor(uri, "r"); // 이미지를 읽고 저장을 하는 구문

                //true면 비트맵객체에 메모리를 할당하지 않아서 비트맵을 반환하지 않음.
                //다만 options fields는 값이 채워지기 때문에 Load 하려는 이미지의 크기를 포함한 정보들을 얻어올 수 있다.

                int scale = 0;
                if(options.outHeight > i || options.outWidth > i){ // 헤당이미지의 가로세로를 중ㄱㅁ
                    scale = (int)Math.pow(2,(int) Math.round(Math.log(i / (double) Math.max(options.outHeight, options.outWidth)) / Math.log(0.5)));
                }
                options.inJustDecodeBounds = false; // true면 비트맵을 만들지 않고 해당이미지의 가로, 세로, Mime type등의 정보만 가져옴
                options.inSampleSize = scale; // 이미지의 원본사이즈를 설정된 스케일로 축소

                Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fd.getFileDescriptor(), null, options);

                if(bitmap != null){
                    // 정확하게 사이즈를 맞춤
                    if(options.outWidth != i || options.outHeight != i){
                        Bitmap tmp = Bitmap.createScaledBitmap(bitmap, i, i, true);
                        bitmap.recycle();
                        bitmap = tmp;
                    }
                }
                return bitmap;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }finally {
                try {
                    if (fd != null)
                        fd.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }


    //5
    @Override
    public int getItemCount() {
        return (musicList != null) ? (musicList.size()) : (0);
    }

    //4 내부클래스 만들기
    public class CustomerViewHolder extends RecyclerView.ViewHolder {
        //임플레이터 된 데이타 항목을 찾아온다.
        ImageView albumArt;
        TextView title;
        TextView artist;
        TextView duration;//내부클래스는 굳이 private써주지 않아도 됌.

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            this.albumArt = itemView.findViewById(R.id.d_ivAlbum);
            this.title = itemView.findViewById(R.id.d_tvTitle);
            this.artist = itemView.findViewById(R.id.d_tvArtist);
            this.duration = itemView.findViewById(R.id.d_tvDuration);


        }
    }
}
