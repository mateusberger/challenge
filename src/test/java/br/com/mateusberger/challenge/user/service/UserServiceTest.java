package br.com.mateusberger.challenge.user.service;

import br.com.mateusberger.challenge.commons.exceptions.InvalidInformationException;
import br.com.mateusberger.challenge.user.domain.RoleEnum;
import br.com.mateusberger.challenge.user.domain.User;
import br.com.mateusberger.challenge.user.dto.CreateUserDTO;
import br.com.mateusberger.challenge.user.dto.EditUserDTO;
import br.com.mateusberger.challenge.user.dto.UserDTO;
import br.com.mateusberger.challenge.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("User service unity tests")
class UserServiceTest {

    private UserService userService;

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;


    @BeforeEach
    void setup() {

        userRepository = mock(UserRepository.class);

        passwordEncoder = mock(PasswordEncoder.class);

        userService = new UserService(
                userRepository,
                new ModelMapper(),
                passwordEncoder
        );
    }


    @Test
    @DisplayName("Should create new user successfully")
    void shouldCreateNewUserSuccessfully() {


        var request = new CreateUserDTO("userone", "myPass");

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocationOnMock -> {
                    var user = (User) invocationOnMock.getArgument(0);
                    user.setId(1L);
                    return user;
                });

        var response = userService.createNewUser(request);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getRole()).isEqualTo(RoleEnum.DEFAULT);
        assertThat(response.getUsername()).matches("userone");
        verify(passwordEncoder).encode(request.getPassword());
    }

    @Test
    @DisplayName("Should edit user information successfully")
    void shouldEditUserInformationSuccessfully() {

        var user = new User(1L, "userone", "myPass", RoleEnum.DEFAULT);

        var request = new EditUserDTO(RoleEnum.ACCOUNT_MANAGER, "usertwo");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        var response = userService.editUserInformation(1L, request);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getUsername()).matches("usertwo");
        assertThat(response.getRole()).isEqualTo(RoleEnum.ACCOUNT_MANAGER);
        verify(userRepository).findById(1L);
        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("Should edit user username successfully")
    void shouldEditUserUsernameSuccessfully() {

        var user = new User(1L, "userone", "myPass", RoleEnum.DEFAULT);

        var request = new EditUserDTO(null, "usertwo");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        var response = userService.editUserInformation(1L, request);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getUsername()).matches("usertwo");
        assertThat(response.getRole()).isEqualTo(RoleEnum.DEFAULT);
        verify(userRepository).findById(1L);
        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("Should edit user role successfully")
    void shouldEditUserRoleSuccessfully() {

        var user = new User(1L, "userone", "myPass", RoleEnum.DEFAULT);

        var request = new EditUserDTO(RoleEnum.ACCOUNT_MANAGER, null);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        var response = userService.editUserInformation(1L, request);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getUsername()).matches("userone");
        assertThat(response.getRole()).isEqualTo(RoleEnum.ACCOUNT_MANAGER);
        verify(userRepository).findById(1L);
        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("Should throw invalid information exception when user id not exist")
    void shouldThrowInvalidInformationExceptionWhenIdNotExist() {

        var request = new EditUserDTO(RoleEnum.ACCOUNT_MANAGER, null);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(InvalidInformationException.class, () -> userService.editUserInformation(1L, request));

        verify(userRepository).findById(1L);
    }

    @Test
    @DisplayName("Should throw invalid information exception when request has no information")
    void shouldThrowInvalidInformationExceptionWhenRequestHasNoInformation() {

        var request = new EditUserDTO(null, null);

        assertThrows(InvalidInformationException.class, () -> userService.editUserInformation(1L, request));
    }

    @Test
    @DisplayName("Should retrieve information about the user successfully")
    void shouldRetrieveInformationAboutTheUserSuccessfully(){

        var userInformation = new User(1L, "userone", "password", RoleEnum.DEFAULT);

        UserDTO response = userService.getUserInformation(userInformation);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getUsername()).matches("userone");
        assertThat(response.getRole()).isEqualTo(RoleEnum.DEFAULT);
    }

}