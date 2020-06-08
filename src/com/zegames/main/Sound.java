package com.zegames.main;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {

    private AudioClip clip;

    public static final Sound hurtEffect = new Sound("/smb_stomp.wav");
    public static final Sound coinEffect = new Sound("/smb_coin.wav");
    public static final Sound dieEffect = new Sound("/smb_mariodie.wav");
    public static final Sound jumpEffect = new Sound("/smb_jump-small.wav");
    public static final Sound music = new Sound("/SuperMarioBros (online-audio-converter.com).wav");

    private Sound(String name) {
        try {
            clip = Applet.newAudioClip(Sound.class.getResource(name));
        }catch(Throwable e) {}
    }

    public void play() {
        try {
            new Thread() {
                public void run() {
                    clip.play();
                }
            }.start();
        }catch(Throwable e) {}
    }

    public void loop() {
        try {
            new Thread() {
                public void run() {
                    clip.loop();
                }
            }.start();
        }catch(Throwable e) {}
    }
}
