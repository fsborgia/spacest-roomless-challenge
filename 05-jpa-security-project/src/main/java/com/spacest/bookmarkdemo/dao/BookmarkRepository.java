package com.spacest.bookmarkdemo.dao;

import com.spacest.bookmarkdemo.entity.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark,Integer>, JpaSpecificationExecutor<Bookmark> {


    @Query(value = "SELECT * FROM bookmark WHERE user_id = ?1", nativeQuery = true)
    List<Bookmark> findByUserId(Integer user_id);

    @Query(value = "SELECT * FROM bookmark WHERE title LIKE CONCAT('%',?1,'%') AND url LIKE CONCAT('%',?2,'%')", nativeQuery = true)
    Page<Bookmark> searchAll(String title, String url, Pageable pageable);

    @Query(value = "SELECT * FROM bookmark WHERE title LIKE CONCAT('%',?1,'%') AND url LIKE CONCAT('%',?2,'%') AND user_id = ?3", nativeQuery = true)
    Page<Bookmark> searchByUser(String title, String url, Integer user_Id, Pageable pageable);


}
