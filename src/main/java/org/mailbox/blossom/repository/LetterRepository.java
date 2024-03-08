package org.mailbox.blossom.repository;

import org.mailbox.blossom.domain.Letter;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LetterRepository extends JpaRepository<Letter, Long> {
    @EntityGraph(attributePaths = {"user"})
    Optional<Letter> findWithUserById(Long id);


}
