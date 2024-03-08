package org.mailbox.blossom.repository;

import org.mailbox.blossom.domain.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserStatusRepository extends JpaRepository<UserStatus, UUID> {
}
