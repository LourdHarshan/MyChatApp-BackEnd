package com.chat.messenger.DocumentModels;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.BasicDBList;

@Document
public class Message {
    @Id
    private String id;
    private String message;
    private String from;
    private String to;
    private LocalDate date;
    private LocalTime time;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setDate() {
        this.date = LocalDate.now();
    }

    public void setTime() {
        this.time = LocalTime.now();
    }

    public String getMessage() {
        return this.message;
    }

    public String getId() {
        return this.id;
    }

    public String getFrom() {
        return this.from;
    }

    public String getTo() {
        return this.to;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public LocalTime getTime() {
        return this.time;
    }

}
