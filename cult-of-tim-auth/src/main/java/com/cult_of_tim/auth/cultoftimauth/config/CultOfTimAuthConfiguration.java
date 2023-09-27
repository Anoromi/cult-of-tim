package com.cult_of_tim.auth.cultoftimauth.config;

import com.cult_of_tim.auth.cultoftimauth.dao.UserDao;
import com.cult_of_tim.auth.cultoftimauth.dao.mock.UserDaoMock;
import com.cult_of_tim.auth.cultoftimauth.util.UserChecker;
import com.cult_of_tim.auth.cultoftimauth.util.WithoutPasswordChecker;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class CultOfTimAuthConfiguration {


    //@Bean
    //@ConditionalOnProperty(prefix = "com.cult-of-tim.auth", name = "user-checker", value = "no-password")
    //public UserChecker noPasswordChecker() {
    //    return new WithoutPasswordChecker();
    //}

    @Bean
    @ConditionalOnMissingBean(UserChecker.class)
    public UserChecker noPasswordChecker(UserDao userDao) {
        return new WithoutPasswordChecker(userDao);
    }

    @Bean
    @Primary
    public UserDao userDao() {
        return new UserDaoMock();
    }

}
