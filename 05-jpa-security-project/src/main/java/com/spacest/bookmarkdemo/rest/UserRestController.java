package com.spacest.bookmarkdemo.rest;

import com.spacest.bookmarkdemo.entity.Bookmark;
import com.spacest.bookmarkdemo.entity.User;
import com.spacest.bookmarkdemo.service.BookmarkService;
import com.spacest.bookmarkdemo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserRestController {
    private final UserService userService;

    private final BookmarkService bookmarkService;

    public UserRestController(UserService userService, BookmarkService bookmarkService) {
        this.userService = userService;

        this.bookmarkService = bookmarkService;
    }

    //Expose an endpoint /users to get all users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    //add Mapping for GET /users/id to get a user with required id
    @GetMapping("/users/{userId}")
    public User getUserById(@PathVariable int userId) {
        User user = userService.findById(userId);
        if (user == null)
            throw new RuntimeException("User id " + userId + " not found.");
        return user;
    }

    //add Mapping for GET /users/id to get a user with required id
    @GetMapping("/users/{userEmail}")
    public User getUserByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);
        if (user == null)
            throw new RuntimeException("User with email " + email + " not found.");
        return user;
    }



    //add Mapping for POST /users to add a new user in the db
    @PostMapping("/users")
    public User addUser(@RequestBody User newUser) {

        //EntityManager merge method creates a new element if id==0
        // so we set element id to 0 in case the JSON specifies a different id
        newUser.setId(0);

        return userService.save(newUser);

    }

    //add Mapping for PUT /users to update an existing user in the db
    @PutMapping("/users")
    public User updateUser(@RequestBody User updatedUser) {
        User newUser = userService.save(updatedUser);
        if (newUser == null)
            throw new RuntimeException("User id " + updatedUser.getId() + " not found.");
        return newUser;
    }

    //add Mapping for DELETE /users/id to delete user in the db with required id
    //Consistency Check: we don't want to have bookmarks linked to deleted users.
    //option 1) all bookmarks related to the selected user are deleted
    //option 2) user can be deleted only if it has no bookmarks
    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable int userId) {
        User toBeDeleted = userService.findById(userId);

        //throw exception if user not found
        if (toBeDeleted == null)
            throw new RuntimeException("User id " + userId + " not found.");

        //delete all the bookmarks of the user
        List<Bookmark> userBookmarks = bookmarkService.findByUserId(userId);
        for (Bookmark aBookmark : userBookmarks) {
            bookmarkService.deleteById(aBookmark.getId());
        }

        //delete the element otherwise
        userService.deleteById(userId);
    }
}
