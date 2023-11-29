package com.cult_of_tim.auth.cultoftimauth.repositories;

import com.cult_of_tim.auth.cultoftimauth.model.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, UUID> {

    Optional<UserToken> findByTokenId(UUID tokenId);

    List<UserToken> findByUserUserId(UUID userId);


    void deleteAllByExpiresAtAfter(Date now);
}
