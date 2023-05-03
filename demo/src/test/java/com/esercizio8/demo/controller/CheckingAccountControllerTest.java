package com.esercizio8.demo.controller;
import com.esercizio8.demo.service.CheckingAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.UUID;
import static org.mockito.Mockito.*;

@WebMvcTest(CheckingAccountController.class)
public class CheckingAccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CheckingAccountService checkingAccountService;

    @Test
    public void testDeleteCheckingAccount() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(checkingAccountService).deleteCheckingAccountById(id);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/checkingAccount/"+ id.toString()))
                .andExpect(status().isOk());

    }
}