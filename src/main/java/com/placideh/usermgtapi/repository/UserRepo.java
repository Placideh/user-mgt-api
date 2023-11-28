package com.placideh.usermgtapi.repository;

import com.placideh.usermgtapi.Dto.UserDto;
import com.placideh.usermgtapi.entity.Status;
import com.placideh.usermgtapi.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
    Integer countByStatus(Status status);


    @Modifying
    @Transactional
    @Query(
            value="update users set first_name=?1,last_name=?2 where email=?3",
            nativeQuery=true
    )
    void updateUser(String firstName,String lastName,String email);

}
