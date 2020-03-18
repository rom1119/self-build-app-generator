package com.SelfBuildApp.infrastructure.User.Repository;

import com.SelfBuildApp.infrastructure.User.Model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);

}
