package com.example.tengzhongwei.simplenote;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tengzhongwei on 3/10/18.
 */

public class Note implements Serializable {
    private String title;
    private String content;
    private Date date;

    public Note(String title, String content, Date date){
        this.title = title;
        this.content = content;
        this.date = date;
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

    public void setTitle(String title){
        this.title = title;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setDate(Date date){
        this.date = date;
    }
}
