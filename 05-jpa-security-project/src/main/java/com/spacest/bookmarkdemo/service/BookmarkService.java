package com.spacest.bookmarkdemo.service;

import com.spacest.bookmarkdemo.entity.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookmarkService extends EntityService<Bookmark>{
    //List of bookmarks for the specified user
    List<Bookmark> findByUserId(int user_id);

    //Bookmarks whose title or url contains the specified strings
    Page<Bookmark> searchAll(String title, String url, Pageable pageable);

    //Bookmarks whose title or url contains the specified strings
    Page<Bookmark> searchByUser(String title, String url, int user_Id, Pageable pageable);

}
