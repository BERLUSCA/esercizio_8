package com.esercizio8.demo.controller;
import com.esercizio8.demo.model.User;
import com.esercizio8.demo.repository.UserRepository;
import com.esercizio8.demo.service.CheckingAccountService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.util.UUID;
@WebMvcTest(CheckingAccountController.class)
public class CheckingAccountControllerTest {
    @MockBean
    private CheckingAccountService checkingAccountService;

    @Mock
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testDeleteCheckingAccount() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(delete("/checkingAccounts/{id}", id))
                .andExpect(status().isOk());

        verify(checkingAccountService, times(1)).deleteCheckingAccountById(id);
    }
}