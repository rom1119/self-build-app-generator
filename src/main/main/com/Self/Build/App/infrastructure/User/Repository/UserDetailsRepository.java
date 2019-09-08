package com.Self.Build.App.infrastructure.User.Repository;

import com.Self.Build.App.infrastructure.User.Model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

}
