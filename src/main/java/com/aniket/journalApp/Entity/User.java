package com.aniket.journalApp.Entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import com.aniket.journalApp.Entity.JournalEntry;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
public class User {

    @Id
    private ObjectId id;

    @Indexed (unique = true)// indexing of fields, because username must be unique
    @NonNull
    private String username;
    @NonNull
    private String password;
    @DBRef                   // this helps to link between user and journal entries made by that user
    private List <JournalEntry> journalEntries = new ArrayList<>();
    private List<String> roles;
}
