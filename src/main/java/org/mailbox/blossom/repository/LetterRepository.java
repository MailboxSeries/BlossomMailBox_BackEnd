package org.mailbox.blossom.repository;

import org.mailbox.blossom.domain.Letter;
import org.mailbox.blossom.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LetterRepository extends JpaRepository<Letter, Long> {
    @EntityGraph(attributePaths = {"user"})
    Optional<Letter> findWithUserById(Long id);


    List<Letter> findByUserAndCreatedAt(User user, LocalDate createdAt);
}
