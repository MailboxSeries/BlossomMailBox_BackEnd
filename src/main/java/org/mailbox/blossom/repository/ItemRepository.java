package org.mailbox.blossom.repository;

import org.mailbox.blossom.domain.Item;
import org.mailbox.blossom.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByUser(User user);

    @Query("SELECT i FROM Item i WHERE i.skin.name LIKE CONCAT('%', :type, '%') AND i.skin.arrayId = :arrayId AND i.user.id = :userId")
    Optional<Item> findItemByTypeAndArrayIdAndUserId(@Param("type") String type, @Param("arrayId") Integer arrayId, @Param("userId") String userId);

}
