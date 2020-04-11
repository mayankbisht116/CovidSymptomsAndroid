package com.covid.ui.history;

public class HistoryItemModel {

    private String date;
    private String time;

    public HistoryItemModel(String date, String time) {
        this.date = date;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
