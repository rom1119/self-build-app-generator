package com.SelfBuildApp.infrastructure.User.Repository;

import com.SelfBuildApp.infrastructure.User.Model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

}
