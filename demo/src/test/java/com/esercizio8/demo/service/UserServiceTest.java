package com.esercizio8.demo.service;
import com.esercizio8.demo.dto.requests.user.UserUpdateRequestDto;
import com.esercizio8.demo.dto.responses.user.UserUpdateResponseDto;
import com.esercizio8.demo.helper.UserHelper;
import com.esercizio8.demo.model.User;
import com.esercizio8.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserHelper userHelper;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private UserService userService;

    @Test
    void updateUser() throws Exception   {
        // utente
        UUID userId = UUID.fromString("e0c16996-80df-4c44-a6e8-3cafae33a4c3");
        User user = new User();
        user.setId(userId);
        user.setEmail("test11@test.com");
        user.setFirstName("giorgio");
        user.setLastName("gambino");

        // request aggiornato
        UserUpdateRequestDto updateUser = new UserUpdateRequestDto();
        updateUser.setEmail("alessandro@test.com");
        updateUser.setFirstname("alessandro");
        updateUser.setLastname("catalano");

        // response utente
        UserUpdateResponseDto responseDto = new UserUpdateResponseDto();
        responseDto.setEmail(updateUser.getEmail());
        responseDto.setFirstname(updateUser.getFirstname());
        responseDto.setLastname(updateUser.getLastname());

        // Configurazione mock
        when(userHelper.getUser(eq(userId))).thenReturn(user);
        doNothing().when(userHelper).checkEmailAlreadyExists(updateUser.getEmail());
        when(userRepository.save(eq(user))).thenReturn(user);
        when(modelMapper.map(eq(user), eq(UserUpdateResponseDto.class))).thenReturn(responseDto);

        user.setFirstName(updateUser.getFirstname());
        user.setLastName(updateUser.getLastname());

        // Chiamata del metodo da testare
        UserUpdateResponseDto response = userService.updateUser(userId, updateUser);

        // Verifica degli output attesi
        assertEquals(responseDto.getId(), response.getId());
        assertEquals(responseDto.getEmail(), response.getEmail());
        assertEquals(responseDto.getFirstname(), response.getFirstname());
        assertEquals(responseDto.getLastname(), response.getLastname());
    }
}