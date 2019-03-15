package com.example.cse.exoplayer;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectorResult;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class MainActivity extends AppCompatActivity {
    SimpleExoPlayerView simpleExoPlayerView;
    SimpleExoPlayer simpleExoPlayer;
    long currentpos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        simpleExoPlayerView=findViewById(R.id.exoview);

        simpleExoPlayerView.setPlayer(simpleExoPlayer);
    }
    public void stopPlayer(){
        if(simpleExoPlayer!=null){
            currentpos=simpleExoPlayer.getContentPosition();
            simpleExoPlayer.release();
            simpleExoPlayer.stop();
            simpleExoPlayer=null;


        }
    }
    public void starPlayer(){
        RenderersFactory renderersFactory=new DefaultRenderersFactory(this);
        TrackSelector trackSelector=new DefaultTrackSelector();
        LoadControl loadControl=new DefaultLoadControl();
        Uri uri=Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
        String s=Util.getUserAgent(this,"ExoplayerDemo2");
        MediaSource mediaSource=new ExtractorMediaSource(uri,new DefaultDataSourceFactory(this,s),new DefaultExtractorsFactory(),null,null);

        simpleExoPlayer=ExoPlayerFactory.newSimpleInstance(renderersFactory,trackSelector,loadControl);
        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.seekTo(currentpos);
        simpleExoPlayerView.setPlayer(simpleExoPlayer);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(Util.SDK_INT>23){
            starPlayer();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(Util.SDK_INT<=23){
            stopPlayer();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        starPlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopPlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(Util.SDK_INT>23){
            stopPlayer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Util.SDK_INT<=23||currentpos!=0){
            starPlayer();
        }
    }
}
