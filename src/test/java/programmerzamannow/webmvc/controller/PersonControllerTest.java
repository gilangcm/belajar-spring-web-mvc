package programmerzamannow.webmvc.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createPerson() throws Exception {
        mockMvc.perform(
                post("/person")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("firstName","Gilang")
                        .param("middleName","Chandra")
                        .param("lastName","Maulana")
                        .param("email","gils@example.com")
                        .param("phone","123456789")
                        .param("address.street", "jalan belum jadi")
                        .param("address.city", "Bandung")
                        .param("address.country", "Indonesia")
                        .param("address.postalCode", "12345")
                        .param("hobbies[0]", "coding")
                        .param("hobbies[1]", "gaming")
                        .param("hobbies[2]", "coking")
                        .param("socialMedias[0].name", "facebook")
                        .param("socialMedias[0].location", "facebook.com/gils")
                        .param("socialMedias[1].name", "instagram")
                        .param("socialMedias[1].location", "instagram.com/gils")
        ).andExpectAll(
                status().isOk(),
                content().string(Matchers.containsString("Success create person Gilang Chandra Maulana " +
                        "with email gils@example.com and phone 123456789 " +
                        "with address jalan belum jadi, Bandung, Indonesia, 12345"))
        );
    }

    @Test
    void createPersonValidationError() throws Exception {
        mockMvc.perform(
                post("/person")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("middleName","Chandra")
                        .param("lastName","Maulana")
                        .param("address.street", "jalan belum jadi")
                        .param("address.city", "Bandung")
                        .param("address.country", "Indonesia")
                        .param("address.postalCode", "12345")
                        .param("hobbies[0]", "coding")
                        .param("hobbies[1]", "gaming")
                        .param("hobbies[2]", "coking")
                        .param("socialMedias[0].name", "facebook")
                        .param("socialMedias[0].location", "facebook.com/gils")
                        .param("socialMedias[1].name", "instagram")
                        .param("socialMedias[1].location", "instagram.com/gils")
        ).andExpectAll(
                status().isBadRequest(),
                content().string(Matchers.containsString("You send invalid data"))
        );
    }
}