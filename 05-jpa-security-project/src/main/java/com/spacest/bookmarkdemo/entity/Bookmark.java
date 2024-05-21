package com.spacest.bookmarkdemo.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Date;

@Entity
@Table(name="bookmark")
@EnableJpaAuditing
public class Bookmark {

    //Fields of the table
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="description")
    private String description;

    @Column(name="url", nullable = false)
    private String url;

    @Column(name="user_id", nullable = false)
    private int userId;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;

    @Transient
    private String userEmail;

    public Bookmark() {
    }

    //Constructors
    public Bookmark(String title, String description, String url, int userId) {
        this(title,url,userId);
        this.description = description;
    }

    public Bookmark(String title, String url, int userId) {
        this.title = title;
        this.url = url;
        this.userId = userId;
    }

    //Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


    @Override
    public String toString() {
        return "Bookmark{" +
                "id=" + id +
                ", title='" + title + '\'' +
                (description == null? "":(", description='" + description + '\'')) +
                ", url='" + url + '\'' +
                ", created_at='" + createdAt + '\'' +
                (updatedAt == null? "":(", updated_at='" + updatedAt + '\'')) +
                ", user=" + userId +
                '}';
    }
}
