package com.example.librarymanagement;

public class Books {
    String title, author, genre, status, rating;

    public Books() {
    }

    public Books(String title, String author, String genre, String status, String rating) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.rating = rating;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
