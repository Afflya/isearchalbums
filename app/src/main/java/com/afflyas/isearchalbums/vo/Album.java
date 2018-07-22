package com.afflyas.isearchalbums.vo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;

/**
 *
 * Object that contains represents general information about album provided by iTunes api
 *
 * {@link #collectionId} - id of the album
 * {@link #collectionName} - title of the album
 * {@link #artworkUrl} - Url string of artwork image
 * {@link #artistName} - name of the artist
 * {@link #releaseDate} - release date
 * {@link #primaryGenreName} - primary genre of the album
 *
 *
 * Implemented {@link Parcelable} to be able to put Album objects into Bundle and pass it
 * as argument when navigating to {@link com.afflyas.isearchalbums.ui.album.AlbumFragment}
 *
 * Implemented {@link Comparable} to be able to sort {@link java.util.List<Album>} by {@link #collectionName}
 *
 */
public class Album implements Parcelable, Comparable<Album> {

    @SerializedName("collectionId")
    private Long collectionId;

    @SerializedName("collectionName")
    private String collectionName;

    @SerializedName("artworkUrl100")
    private String artworkUrl;

    @SerializedName("artistName")
    private String artistName;

    @SerializedName("releaseDate")
    private String releaseDate;

    @SerializedName("primaryGenreName")
    private String primaryGenreName;

    public Long getCollectionId() {
        return collectionId;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public String getArtworkUrl() {
        return artworkUrl;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPrimaryGenreName() {
        return primaryGenreName;
    }


    private Album(Parcel in) {
        if (in.readByte() == 0) {
            collectionId = null;
        } else {
            collectionId = in.readLong();
        }
        collectionName = in.readString();
        artworkUrl = in.readString();
        artistName = in.readString();
        releaseDate = in.readString();
        primaryGenreName = in.readString();
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (collectionId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(collectionId);
        }
        parcel.writeString(collectionName);
        parcel.writeString(artworkUrl);
        parcel.writeString(artistName);
        parcel.writeString(releaseDate);
        parcel.writeString(primaryGenreName);
    }

    /**
     * Used to sort list {@link java.util.List<Album>} by {@link #collectionName}
     */
    @Override
    public int compareTo(@NonNull Album album) {
        return collectionName.compareTo(album.collectionName);
    }
}
