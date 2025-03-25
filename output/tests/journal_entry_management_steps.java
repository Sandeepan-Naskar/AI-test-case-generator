package net.engineeringdigest.journalApp.steps;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ContextConfiguration(classes = JournalEntryControllerV2.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class JournalEntrySteps {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    private static final String BASE_URL = "/api/journal";

    @BeforeEach
    void setup() {
        journalEntryRepository.deleteAll();
    }

    @Given("there exists a journal entry with:")
    public void createJournalEntry(String json) throws Exception {
        JournalEntry entry = objectMapper.readValue(json, JournalEntry.class);
        journalEntryRepository.save(entry);
    }

    @When("I send a POST request to \"/api/journal\" with the following JSON:")
    public void postRequest(String json) throws Exception {
        mockMvc.perform(post(BASE_URL)
                .contentType("application/json")
                .content(json))
                .andExpect(status().isCreated());
    }

    @When("I send a GET request to \"/api/journal\"")
    public void getAllRequest() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk());
    }

    @Then("the response should be an array of journal entries")
    public void verifyResponseIsArray() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @When("I send a PUT request to \"/api/journal/{id}\" with the following JSON:")
    public void putRequest(String id, String json) throws Exception {
        mockMvc.perform(put(BASE_URL + "/" + id)
                .contentType("application/json")
                .content(json))
                .andExpect(status().isOk());
    }

    @When("I send a DELETE request to \"/api/journal/{id}\"")
    public void deleteRequest(String id) throws Exception {
        mockMvc.perform(delete(BASE_URL + "/" + id))
                .andExpect(status().isNoContent());
    }

    @Then("the response should contain {string}")
    public void verifyResponseContent(String content) throws Exception {
        mockMvcPerform().andExpect(content().string(containsString(content)));
    }

    private String createJournalEntryId() {
        JournalEntry entry = new JournalEntry(
                "Test Entry",
                "Test Content",
                LocalDateTime.now());
        return journalEntryRepository.save(entry).getId().toHexString();
    }
}