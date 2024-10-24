package br.com.mateusberger.challenge.user.repository;

import br.com.mateusberger.challenge.user.domain.RoleEnum;
import br.com.mateusberger.challenge.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@DisplayName("User repository integration test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should create a user")
    void shouldCreateUserIntoTheRepository(){

        var user = new User(null, "fulano", "fulpass", RoleEnum.DEFAULT);

        User savedUser = userRepository.save(user);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
    }

    @Test
    @DisplayName("Should alter a user")
    void shouldAlterUserIntoTheRepository(){

        var user = new User(null, "fulano", "fulpass", RoleEnum.DEFAULT);

        User savedUser = userRepository.save(user);

        Optional<User> userToBeAltered = userRepository.findById(savedUser.getId());

        userToBeAltered.get().setUsername("fulano_novo");
        userToBeAltered.get().setRole(RoleEnum.ACCOUNT_MANAGER);

        User userAfterBeAltered = userRepository.save(userToBeAltered.get());

        assertThat(userAfterBeAltered).isNotNull();
        assertThat(userAfterBeAltered.getId()).isEqualTo(savedUser.getId());
        assertThat(userAfterBeAltered.getRole()).isEqualTo(RoleEnum.ACCOUNT_MANAGER);
        assertThat(userAfterBeAltered.getUsername()).isEqualTo("fulano_novo");
    }

    @Test
    @DisplayName("Should read a user by username")
    void shouldReadUserByUsernameIntoTheRepository(){

        var user = userRepository.save(new User(null, "fulano", "fulpass", RoleEnum.DEFAULT));

        Optional<User> foundUser = userRepository.findByUsername("fulano");

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("Should read a user by id")
    void shouldReadUserByIdIntoTheRepository(){

        var user = userRepository.save(new User(null, "fulano", "fulpass", RoleEnum.DEFAULT));

        Optional<User> foundUser = userRepository.findById(user.getId());

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getId()).isEqualTo(user.getId());
    }
}