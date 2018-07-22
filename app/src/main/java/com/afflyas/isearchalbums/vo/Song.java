package com.afflyas.isearchalbums.vo;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;

/**
 *
 * Object that contains represents general information about song provided by iTunes api
 */
public class Song {

    /**
     * get a formatted track time String
     *
     * @return String in format: mm:ss
     */
    public String getTrackTimeString() {
        return (trackTimeMillis / 1000 / 60) + ":" + String.format(Locale.ENGLISH,"%02d", (trackTimeMillis / 1000 % 60));
    }

    public Integer getTrackNumber() {
        return trackNumber;
    }

    public String getTrackName() {
        return trackName;
    }

    public Integer getTrackTimeMillis() {
        return trackTimeMillis;
    }

    @SerializedName("trackNumber")
    private Integer trackNumber;

    @SerializedName("trackName")
    private String trackName;

    @SerializedName("trackTimeMillis")
    private Integer trackTimeMillis;
}
