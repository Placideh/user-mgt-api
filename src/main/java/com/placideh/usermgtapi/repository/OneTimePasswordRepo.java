package com.placideh.usermgtapi.repository;


import com.placideh.usermgtapi.entity.OneTimePassword;
import com.placideh.usermgtapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface OneTimePasswordRepo extends JpaRepository<OneTimePassword,String> {


    Optional<OneTimePassword> findByOtp(String otp);
    Optional<OneTimePassword> findByUser(User user);

    @Transactional
    Long deleteByOtp(String otp);


}
