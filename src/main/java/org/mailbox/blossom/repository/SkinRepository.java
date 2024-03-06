package org.mailbox.blossom.repository;

import org.mailbox.blossom.domain.Skin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkinRepository extends JpaRepository<Skin, Long> {
}
