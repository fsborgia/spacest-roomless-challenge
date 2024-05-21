package com.spacest.bookmarkdemo.rest;

import com.spacest.bookmarkdemo.entity.Bookmark;
import com.spacest.bookmarkdemo.entity.User;
import com.spacest.bookmarkdemo.security.CustomPrincipal;
import com.spacest.bookmarkdemo.service.BookmarkService;
import com.spacest.bookmarkdemo.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;

@RestController
public class BookmarkRestController {
    //Service layer to manipulate Bookmarks
    private final BookmarkService bookmarkService;

    //Service layer to check Users
    private final UserService userService;

    //UserDetailsService to find the role of the logged user
    private final UserDetailsService userDetailsService;

    public BookmarkRestController(BookmarkService bookmarkService, UserService userService, UserDetailsService userDetailsService) {
        this.bookmarkService = bookmarkService;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }


    //Expose an endpoint /bookmarks to get all bookmarks
    //If user has Admin role returns all bookmarks, otherwise it returns only user's bookmarks
    @GetMapping("/bookmarks")
    public List<Bookmark> getBookmarkByUser(Principal principal) {
        System.out.println(principal);
        CustomPrincipal thePrincipal = new CustomPrincipal(principal, userService, userDetailsService);
        if (thePrincipal.isAdmin()) {
            return this.bookmarkService.findAll();
        }
        else {
            return this.bookmarkService.findByUserId(thePrincipal.getId());
        }
    }

    //add Mapping for GET /bookmarks/id to get a bookmark with required id
    //if user is not admin he can not access other users bookmark informations
    @GetMapping("/bookmarks/{bookmarkId}")
    public Bookmark getBookmarkById(@PathVariable int bookmarkId, Principal principal) {
        Bookmark bookmark = bookmarkService.findById(bookmarkId);
        if (bookmark == null)
            throw new BookmarkNotFoundException("Bookmark id " + bookmarkId + " not found.");
        CustomPrincipal thePrincipal = new CustomPrincipal(principal, userService, userDetailsService);
        if (thePrincipal.isAdmin() || thePrincipal.getId() == bookmark.getUserId()) {
            return bookmark;
        }
        else {
            throw new RuntimeException("User " + principal.getName() + " unauthorized to access required data");
        }
    }

    //add Mapping for GET /bookmarks/search?title="name",url="link"
    //returns all matching bookmarks. If is not admin the result is filtered to show only personal bookmarks
    @GetMapping("/bookmarks/search")
    public Page<Bookmark> searchBookmarks(@RequestParam(defaultValue = "") String title, @RequestParam(defaultValue = "") String url, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, Principal principal) {
        Pageable firstPage = PageRequest.of(page,size);
        CustomPrincipal thePrincipal = new CustomPrincipal(principal, userService, userDetailsService);
        if (thePrincipal.isAdmin()) {
            return bookmarkService.searchAll(title, url, firstPage);
        }
        else {
            return bookmarkService.searchByUser(title,url, thePrincipal.getId(), firstPage);
        }
    }




    //add Mapping for POST /bookmarks to add a new bookmark in the db
    //non-admin users can only save bookmarks for themselves
   @PostMapping("/bookmarks")
    public Bookmark addBookmark(@RequestBody Bookmark newBookmark, Principal principal) {

        //EntityManager merge method creates a new element if id==0
       // so we set element id to 0 in case the JSON specifies a different id
       newBookmark.setId(0);

       User user = userService.findById(newBookmark.getUserId());

       //throw new exception if user id not found
       if (user == null)
           throw new UserNotFoundException("User id " + newBookmark.getUserId() + " not found. Constraint violated.");

       CustomPrincipal thePrincipal = new CustomPrincipal(principal, userService, userDetailsService);

       if (thePrincipal.isAdmin() || thePrincipal.getId() == user.getId())
           return bookmarkService.save(newBookmark);
       else
           throw new RuntimeException("User " + thePrincipal.getName() + " unauthorized to insert this data.");

   }

    //add Mapping for PUT /bookmarks to update an existing bookmark in the db
    @PutMapping("/bookmarks")
    public Bookmark updateBookmark(@RequestBody Bookmark updatedBookmark, Principal principal) {
        Bookmark oldBookmark = bookmarkService.findById(updatedBookmark.getId());

        User newUser = userService.findById(updatedBookmark.getUserId());

        User oldUser = userService.findById(oldBookmark.getUserId());

        //throw new exception if user id not found
        if (newUser == null)
            throw new UserNotFoundException("User id " + oldUser.getId() + " not found. Constraint violated.");


        CustomPrincipal thePrincipal = new CustomPrincipal(principal, userService, userDetailsService);

        //A non-admin user can't change informations of a bookmark associated to another user
        // and can not assign a bookmark to a different user
        if (thePrincipal.isAdmin() || (thePrincipal.getId() == newUser.getId() && newUser.getId() == oldUser.getId())) {
            Bookmark newBookmark = bookmarkService.save(updatedBookmark);

            //throw exception if the element you want to update is not found
            if (newBookmark == null)
                throw new RuntimeException("Bookmark id " + newBookmark.getId() + " not found.");
            return newBookmark;
        }
        else
            throw new RuntimeException("User " + thePrincipal.getName() + " unauthorized to update this data.");
   }

    //add Mapping for DELETE /bookmarks/id to delete bookmark in the db with required id
    @DeleteMapping("/bookmarks/{bookmarkId}")
    public void deleteBookmark(@PathVariable int bookmarkId, Principal principal) {
        Bookmark toBeDeleted = bookmarkService.findById(bookmarkId);

        //throw exception if bookmark not found
        if (toBeDeleted == null)
            throw new RuntimeException("Bookmark id " + bookmarkId + " not found.");

        CustomPrincipal thePrincipal = new CustomPrincipal(principal, userService, userDetailsService);

        if (thePrincipal.isAdmin() || (thePrincipal.getId() == toBeDeleted.getUserId())) {
            //delete the element if user is authorized
            bookmarkService.deleteById(bookmarkId);
        }
        else
            throw new RuntimeException("User " + thePrincipal.getName() + " unauthorized to delete this data.");

   }
}
