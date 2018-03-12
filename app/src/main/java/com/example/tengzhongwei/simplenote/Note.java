package com.example.tengzhongwei.simplenote;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable {
    private String title;
    private String content;
    private Date date;
    private boolean remind;

    public Note(String title, String content, Date date, boolean remind){
        this.title = title;
        this.content = content;
        this.date = date;
        this.remind = remind;
    }

    public String getTitle(){
        return this.title;
    }

    public String getContent(){
        return this.content;
    }

    public Date getDate(){
        return this.date;
    }

    public boolean getRemind() {
        return this.remind;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public void setRemind(boolean remind) {this.remind = remind;}
}
