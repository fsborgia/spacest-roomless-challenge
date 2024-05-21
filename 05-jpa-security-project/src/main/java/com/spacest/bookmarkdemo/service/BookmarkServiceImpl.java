package com.spacest.bookmarkdemo.service;

import com.spacest.bookmarkdemo.dao.BookmarkRepository;
import com.spacest.bookmarkdemo.entity.Bookmark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class BookmarkServiceImpl implements BookmarkService{

    private final BookmarkRepository bookmarkRepo;

    @Autowired
    BookmarkServiceImpl(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepo = bookmarkRepository;
    }

    @Override
    public List<Bookmark> findAll() {
        return bookmarkRepo.findAll();
    }

    @Override
    public Bookmark findById(int id) {
        Optional<Bookmark> res =  bookmarkRepo.findById(id);
        if (res.isPresent()) {
            return res.get();
        }
        else {
            throw new RuntimeException("Bookmark id" + id + "not found");
        }
    }

    @Override
    public Bookmark save(Bookmark element) {
        return bookmarkRepo.save(element);
    }

    @Override
    public void deleteById(int id) {
        bookmarkRepo.deleteById(id);
    }

    @Override
    public List<Bookmark> findByUserId(int user_id) {
        return bookmarkRepo.findByUserId(user_id);
    }

    @Override
    public Page<Bookmark> searchAll(String title, String url, Pageable pageable) {
        return bookmarkRepo.searchAll(title, url, pageable);
    }

    @Override
    public Page<Bookmark> searchByUser(String title, String url, int user_Id, Pageable pageable) {
        return bookmarkRepo.searchByUser(title, url, user_Id, pageable);
    }
}
