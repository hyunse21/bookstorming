package edu.sungshin.bookstorming;

public class item {
    private String title;
    private String description;
    private int pubDate;
    private String profile;//이건 사진일듯??
    private String author;

    public item(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPubDate() {
        return pubDate;
    }

    public void setPubDate(int pubDate) {
        this.pubDate = pubDate;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getAuthor() { return author; }

    public void setAuthor(String author) {this.author = author;}
}
