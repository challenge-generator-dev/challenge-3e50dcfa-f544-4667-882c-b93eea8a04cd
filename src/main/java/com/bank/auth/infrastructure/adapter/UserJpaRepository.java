package com.bank.auth.infrastructure.adapter;

import com.bank.auth.domain.model.User;
import com.bank.auth.domain.port.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserJpaRepository extends JpaRepository<User, UUID> implements UserRepository {
    
    @Override
    public Optional<User> findById(UUID id) {
        return super.findById(id);
    }
    
    @Override
    public Optional<User> findByUsername(String username) {
        return findAll().stream()
            .filter(u -> u.getUsername().equals(username))
            .findFirst();
    }
    
    @Override
    public Optional<User> findByEmail(String email) {
        return findAll().stream()
            .filter(u -> u.getEmail().equals(email))
            .findFirst();
    }
    
    @Override
    public User save(User user) {
        return super.save(user);
    }
    
    @Override
    public void deleteById(UUID id) {
        super.deleteById(id);
    }
}