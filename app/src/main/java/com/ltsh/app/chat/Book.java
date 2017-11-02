package com.ltsh.app.chat;

import java.util.Date;

/**
 * Created by Random on 2017/9/27.
 */

public class Book {
    private int aIcon;
    private String bName;
    private String bAuthor;
    private String sendDate;
    public Book() {
    }

    public Book(int aIcon, String bName, String bAuthor, String sendDate) {
        this.aIcon = aIcon;
        this.bName = bName;
        this.bAuthor = bAuthor;
        this.sendDate = sendDate;
    }

    public String getbName() {
        return bName;
    }

    public String getbAuthor() {
        return bAuthor;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public void setbAuthor(String bAuthor) {
        this.bAuthor = bAuthor;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }
}
