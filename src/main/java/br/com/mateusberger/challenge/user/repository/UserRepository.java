package br.com.mateusberger.challenge.user.repository;

import br.com.mateusberger.challenge.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    User save(User user);

    Optional<User> findByUsername(String username);

    @Override
    Optional<User> findById(Long id);
}
