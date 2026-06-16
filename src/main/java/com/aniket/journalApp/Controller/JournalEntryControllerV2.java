package com.aniket.journalApp.Controller;

import com.aniket.journalApp.Entity.JournalEntry;
import com.aniket.journalApp.Entity.User;
import com.aniket.journalApp.Service.JournalEntryService;
import com.aniket.journalApp.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

        @Autowired
        private JournalEntryService journalEntryService;

        @Autowired
        private UserService userService;


        //get method to fetch the all entries by a specific user with journal entry list
        @GetMapping
        public ResponseEntity<?> getAllJournalEntriesByUser() {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            User user = userService.findByUserName(username);
            List<JournalEntry> all = user.getJournalEntries();

            if(all !=null && !all.isEmpty()){
                return new ResponseEntity<>(all, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        //post method to create a new entry by a specific user
        @PostMapping
        public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry postEntry) {

                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String username = authentication.getName();

                postEntry.setDate(LocalDateTime.now());

                journalEntryService.saveEntry(postEntry, username);

                return new ResponseEntity<>(postEntry,HttpStatus.CREATED);

        }


        //get method to fetch the entry by using "id" field of
        @GetMapping("id/{myId}")
        public ResponseEntity<JournalEntry> getEntryById(@PathVariable ObjectId myId) {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            User user = userService.findByUserName(username);

            List<JournalEntry> collect = user.getJournalEntries().stream()
                    .filter(x -> x.getId().equals(myId))
                    .collect(Collectors.toList());

            if (!collect.isEmpty()){
                Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
                if (journalEntry.isPresent()){
                    return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

        //put method to update the existing entry
        @PutMapping("id/{myId}")
        public ResponseEntity<?> updateEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry postEntry) {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            User user = userService.findByUserName(username);

            List<JournalEntry> collect = user.getJournalEntries().stream()
                    .filter(x -> x.getId().equals(myId))
                    .collect(Collectors.toList());

            if (!collect.isEmpty()){
                Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
                if (journalEntry.isPresent()){
                    JournalEntry old = journalEntry.get();
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
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //delete the entry by Id
        @DeleteMapping("id/{myId}")
        public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId) {

             Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
             String username = authentication.getName();

             journalEntryService.deleteById(myId, username);
             return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }
