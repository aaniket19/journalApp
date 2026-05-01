package com.aniket.journalApp.Controller;

import com.aniket.journalApp.Entity.JournalEntry;
import com.aniket.journalApp.Entity.User;
import com.aniket.journalApp.Service.JournalEntryService;
import com.aniket.journalApp.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

        @Autowired
        private JournalEntryService journalEntryService;

        @Autowired
        private UserService userService;


        //get method to fetch the all entries by a specific user with journal entry list
        @GetMapping("/{userName}")
        public ResponseEntity<?> getAllJournalEntriesByUser(@PathVariable String userName) {
            User user = userService.findByUserName(userName);
            List<JournalEntry> all = user.getJournalEntries();
            if(all !=null && !all.isEmpty()){
                return new ResponseEntity<>(all, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


//        @GetMapping
//        public ResponseEntity<?> getAll() {
//            List<JournalEntry> all = journalEntryService.getAll();
//            if (all != null && !all.isEmpty()){
//                return new  ResponseEntity<>(all,HttpStatus.OK);
//            }
//            return new  ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }

        //post method to create a new entry by a specific user
        @PostMapping("{userName}")
        public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry postEntry, @PathVariable String userName) {
                postEntry.setDate(LocalDateTime.now());
                journalEntryService.saveEntry(postEntry, userName);
                return new ResponseEntity<>(postEntry,HttpStatus.CREATED);


        }

        //get method to fetch the entry by using "id" field of Plain Old Java Object class
        @GetMapping("id/{myId}")
        public ResponseEntity<JournalEntry> getEntryById(@PathVariable ObjectId myId) {
             Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
             if (journalEntry.isPresent()){
                 return new ResponseEntity<>(journalEntry.get(),HttpStatus.OK);
             }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //put method to update the existing entry
        @PutMapping("id/{userName}/{myId}")
        public ResponseEntity<?> updateEntryById(
                @PathVariable ObjectId myId,
                @RequestBody JournalEntry postEntry,
                @PathVariable String userName) {
            JournalEntry old = journalEntryService.findById(myId).orElse(null);
            if (old != null){
//                old.setTitle(postEntry.getTitle() != null) && !postEntry.getTitle().equals("") ? postEntry.getTitle() : old.getTitle());
//                old.setContent(postEntry.getContent() != null) && !postEntry.getContent().equals("") ? postEntry.getContent() : old.getContent());
                old.setTitle(
                        (postEntry.getTitle() != null && !postEntry.getTitle().isEmpty())
                                ? postEntry.getTitle()
                                : old.getTitle()
                );

                old.setContent(
                        (postEntry.getContent() != null && !postEntry.getContent().isEmpty())
                                ? postEntry.getContent()
                                : old.getContent()
                );
                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old,HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //delete the entry by Id
        @DeleteMapping("id/{userName}/{myId}")
        public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId, @PathVariable String userName) {
             journalEntryService.deleteById(myId, userName);
             return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }
