
package com.devsparkles.lyrics.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.List;

public class Song implements Serializable {

    private final static long serialVersionUID = 4973209763457096354L;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("lyrics")
    @Expose
    private List<String> lyrics = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLyrics() {
        return lyrics;
    }

    public void setLyrics(List<String> lyrics) {
        this.lyrics = lyrics;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(name).append(lyrics).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Song) == false) {
            return false;
        }
        Song rhs = ((Song) other);
        return new EqualsBuilder().append(id, rhs.id).append(name, rhs.name).append(lyrics, rhs.lyrics).isEquals();
    }

}
