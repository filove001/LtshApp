package com.ltsh.app.chat;

/**
 * Created by Random on 2017/9/27.
 */
public class App {
    private int aIcon;
    private String aName;

    public App() {
    }

    public App(int aIcon, String aName) {
        this.aIcon = aIcon;
        this.aName = aName;
    }

    public int getaIcon() {
        return aIcon;
    }

    public String getaName() {
        return aName;
    }

    public void setaIcon(int aIcon) {
        this.aIcon = aIcon;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }
}

