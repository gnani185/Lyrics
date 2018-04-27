
package com.devsparkles.lyrics.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.List;

public class Songs implements Serializable {

    private final static long serialVersionUID = -1762395026109297792L;
    @SerializedName("songs")
    @Expose
    private List<Song> songs = null;

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(songs).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Songs) == false) {
            return false;
        }
        Songs rhs = ((Songs) other);
        return new EqualsBuilder().append(songs, rhs.songs).isEquals();
    }

}
