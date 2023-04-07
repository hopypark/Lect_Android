package com.example.multimediademo;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    // 미디어플레이어 인스턴스 생성 - 미디어 재생기를 만듦
    MediaPlayer mediaPlayer = new MediaPlayer();
    VideoView videoView = null;     // 비디오를 재생하기 위한 비디오뷰 인스턴스 생성

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView = findViewById(R.id.videoView);   // findViewById를 이용해 화면의 VideoView와 연결
    }

    public void playAudio(View view){
        // To do. 오디오 재생에 관한 코드
        if (mediaPlayer.isPlaying()){   // 현재 미디어 플레이어가 재생 중이면
            mediaPlayer.pause();        // 재생을 멈춤
        } else {                        // 현재 재생 중이 아니면, 즉, 일시 중지 상태면
            // hare.mp3을 탑재한 미디어 재생기
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.hare);
            mediaPlayer.setLooping(true);   // 무한 재생 옵션 설정
            mediaPlayer.start();            // 재생한다.
        }
    }

    public void playVideo(View view){
        // To do. 비디오 재생에 관한 코드
        if (videoView.isPlaying()){ // 만약 영상이 재생 중이면
            videoView.pause();      // 일시 중지함
        } else {
            Uri uri = Uri.parse("android.resource://"+getPackageName()+"/raw/movie");
            videoView.setVideoURI(uri); // uri 위치에 있는 영상을 VideoView에 설정
            videoView.start();          // 재생 시작
            videoView.setVisibility(View.VISIBLE);  // 뷰화면에 동영상이 보도록 설정
            // 미디어 컨트롤러 - 재생, 중지, 시크바 등 제어 버튼 추가
            MediaController mediaController = new MediaController(this);
            videoView.setMediaController(mediaController); // 비디어뷰의 컨트롤바로 등록
        }
    }

    @Override
    protected void onDestroy() {    // 앱이 종료될 때 실행됨
        super.onDestroy();
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        
        if(videoView.isPlaying()){
            videoView.pause();
            videoView = null;
        }
    }
}

