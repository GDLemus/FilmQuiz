package com.example.filmquiz;

import com.google.firebase.database.Query;

public class Users {
    String userId, name,profile, puntos;

    public String getPuntos() {
        return puntos;
    }

    public void setPuntos(String puntos) {
        this.puntos = puntos;
    }

    public Users() {
    }

    public Users(String userId, String name, String profile, String puntos) {
        this.userId = userId;
        this.name = name;
        this.profile = profile;
        this.puntos = puntos;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }



}
