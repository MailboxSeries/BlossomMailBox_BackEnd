package org.mailbox.blossom.repository;

import org.mailbox.blossom.domain.Item;
import org.mailbox.blossom.domain.Skin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT COUNT(i) > 0 FROM Item i WHERE i.user.id = :userId AND FUNCTION('DATE', i.createdAt) = :date")
    boolean existsByUserIdAndCreatedAtDate(@Param("userId") String userId, @Param("date") LocalDate date);
    @Query("SELECT i FROM Item i JOIN FETCH i.skin WHERE i.user.id = :userId")
    List<Item> findItemsWithSkinsByUserId(@Param("userId") UUID userId);

}
