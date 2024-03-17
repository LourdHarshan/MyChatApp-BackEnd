package com.chat.messenger.DocumentModels;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Files {
    @Id
    private String id;
    private String file;
    private String from;
    private String to;
    private LocalDate date;
    private LocalTime time;

    public void setDate() {
        this.date = LocalDate.now();
    }

    public void setTime() {
        this.time = LocalTime.now();
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
