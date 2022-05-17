package com.example.kidsbrain.model;


public class Video {
    private String _id;
    private String titre;
    private String description;
    private String url;

    public Video(String _id, String titre, String description, String url) {
        this._id = _id;
        this.titre = titre;
        this.description = description;
        this.url = url;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
