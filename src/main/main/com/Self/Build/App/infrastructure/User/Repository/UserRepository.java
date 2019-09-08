package com.Self.Build.App.infrastructure.User.Repository;

import com.Self.Build.App.infrastructure.User.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findById(String id);

    @Query(
            "SELECT DISTINCT u FROM User u " +
            "LEFT JOIN u.roles r " +
            "LEFT JOIN u.userDetails ud " +
            "WHERE " +
                " (" +
                "LOWER(u.username) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
                "LOWER(r.name) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
                "LOWER(ud.firstName) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
                "LOWER(ud.lastName) LIKE LOWER(CONCAT('%',:searchTerm, '%')) " +
                " ) "
    )
    Page<User> findAll(@Param("searchTerm") String searchTerm, Pageable pageable);

    public User findByUsername(String username);

    @Query(
            "SELECT u FROM User u " +
            "JOIN u.userDetails ud " +
                    "WHERE ud.score IS NOT NULL " +
                    "ORDER BY ud.score DESC"

    )
    List<User> findAllOrderByScoreDesc();



}
