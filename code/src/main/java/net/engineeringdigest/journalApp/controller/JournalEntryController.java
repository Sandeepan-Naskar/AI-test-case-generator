//package net.engineeringdigest.journalApp.controller;
//
//
//import net.engineeringdigest.journalApp.entity.JournalEntry;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/_journal")
//public class JournalEntryController {
//
//    private Map<Long, JournalEntry> journalEntries  = new HashMap<>();
//
//    // FUNCTION for getting all records
//    @GetMapping("/record")
//    public List<JournalEntry> getJournalEntries() {
//        ArrayList<JournalEntry> entryOfJournal;
//        entryOfJournal = new ArrayList<>(journalEntries.values());
//        return entryOfJournal;
//    }
//
//    // function for getting a specific record with a particular record id
//    // we will use path variable because path me variable hai that is being used
//    @GetMapping("/record/{myId}")
//    public JournalEntry getRecord(@PathVariable long myId){
//        return journalEntries.get(myId);
//    }
//
//    // function for adding a record
//    @PostMapping("/record")
//    public void createEntry(@RequestBody JournalEntry myEntry) {
//        // basically my entry will be accessed now be server and POJO will be formed
//        journalEntries.put(myEntry.getId(), myEntry);
//    }
//
//    // NOW basically we want to remove any entry by id:
//    // delete mapping kardo same id wale method par
//
//    @DeleteMapping("/record/{myId}")
//    public boolean removeRecord(@PathVariable long myId){
//        journalEntries.remove(myId);
//        return true;
//
//    }
//
//    // update will need a put http verb
//    @PutMapping("/record/{myId}")
//    public JournalEntry UpdateEntry(@PathVariable long myId, @RequestBody JournalEntry myEntry) {
//        return journalEntries.put(myId, myEntry);
//    }
//
//}