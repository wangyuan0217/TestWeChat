package com.libo.testwechat.util;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.libo.testwechat.App;

import java.io.IOException;

/**
 * 语音播放
 */
public class AudioPlayer {

    private static AudioPlayer instance;

    private MediaPlayer mediaPlayer;

    // private AudioManager mAudioManager;

    private AudioPlayer() {
        // mAudioManager = (AudioManager)
        // AppContext.getInstance().getSystemService(Context.AUDIO_SERVICE);
    }

    public static AudioPlayer getInstance() {
        if (instance == null)
            instance = new AudioPlayer();
        return instance;
    }

    public void play(AssetFileDescriptor afd) {
        try {
            if (isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.reset();
            }
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.prepare();
            mediaPlayer.start();
            // voiceVolume =
            // mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            // mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 2,
            // AudioManager.FLAG_PLAY_SOUND);

            // mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
            // @Override
            // public void onCompletion(MediaPlayer mp) {
            // mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
            // voiceVolume, AudioManager.FLAG_PLAY_SOUND);
            // }
            // });
            afd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // private int voiceVolume = 0;

    public void play(String filePath) {
        try {
            if (isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.reset();
            }
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
            mediaPlayer.prepare();
            mediaPlayer.start();
            // voiceVolume =
            // mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            // mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 2,
            // AudioManager.FLAG_PLAY_SOUND);
            //
            // mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
            // @Override
            // public void onCompletion(MediaPlayer mp) {
            // mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
            // voiceVolume, AudioManager.FLAG_PLAY_SOUND);
            // }
            // });
        } catch (Exception e) {
            e.printStackTrace();
            mediaPlayer = null;
        }
    }

    public boolean isPlaying() {
        if (mediaPlayer == null)
            return false;
        return true;
    }

    public void stop() {
        try {
            if (isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
            // mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
            // voiceVolume, AudioManager.FLAG_PLAY_SOUND);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        mediaPlayer = null;
    }

    public void playMusic() {
        try {
            AssetFileDescriptor fd = App.getInstance().getAssets().openFd("notice.mp3");
            play(fd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
