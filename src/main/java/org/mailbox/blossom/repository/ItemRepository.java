package org.mailbox.blossom.repository;

import org.mailbox.blossom.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT COUNT(i) > 0 FROM Item i WHERE i.user.id = :userId AND FUNCTION('DATE', i.createdAt) = :date")
    boolean existsByUserIdAndCreatedAtDate(@Param("userId") String userId, @Param("date") LocalDate date);
}
