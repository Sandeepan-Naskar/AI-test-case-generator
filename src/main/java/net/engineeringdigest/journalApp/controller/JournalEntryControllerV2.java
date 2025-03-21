package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    // FUNCTION for getting all records
    @GetMapping("/record")
    public List<JournalEntry> getJournalEntries() {
        return journalEntryService.getAll();
    }

    // function for adding a record
    @PostMapping("/record")
    public boolean createEntry(@RequestBody JournalEntry myEntry) {
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);
        return true;
    }

    @GetMapping("/record/{myId}")
    public JournalEntry getRecord(@PathVariable ObjectId myId) {
        return null;
    }


    @DeleteMapping("/record/{myId}")
    public boolean removeRecord(@PathVariable ObjectId myId) {
        return false;
    }

    // update will need a put http verb
    @PutMapping("/record/{myId}")
    public JournalEntry UpdateEntry(@PathVariable ObjectId myId, @RequestBody JournalEntry myEntry) {
        return null;
    }

}