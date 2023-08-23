package org.example.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User getById(Long id);
    User save(User user);
    void deleteById(Long id);

}
