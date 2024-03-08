package org.mailbox.blossom.repository;

import org.mailbox.blossom.domain.User;
import org.mailbox.blossom.dto.type.EProvider;
import org.mailbox.blossom.dto.type.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findById(UUID id);

    @Query("select u.id as id, u.role as role, u.password as password from User u where u.id = :id and u.refreshToken is not null")
    Optional<UserSecurityForm> findFormById(UUID id);

    @Query("select u.id as id, u.role as role, u.password as password from User u where u.serialId = :serialId and u.provider = :provider")
    Optional<UserSecurityForm> findFormBySerialIdAndProvider(String serialId, EProvider provider);

    @Modifying(clearAutomatically = true)
    @Query("update User u set u.refreshToken = :refreshToken where u.id = :id")
    void updateRefreshTokenAndLoginStatus(UUID id, String refreshToken);

    interface UserSecurityForm {
        UUID getId();
        ERole getRole();
        String getPassword();

        // User To UserSecurityForm
        static UserSecurityForm of(User user) {
            return new UserSecurityForm() {
                @Override
                public UUID getId() {
                    return user.getId();
                }

                @Override
                public ERole getRole() {
                    return user.getRole();
                }

                @Override
                public String getPassword() {
                    return user.getPassword();
                }
            };
        }
    }
}
