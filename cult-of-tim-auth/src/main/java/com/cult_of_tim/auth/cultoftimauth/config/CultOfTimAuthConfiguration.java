package com.cult_of_tim.auth.cultoftimauth.config;

import com.cult_of_tim.auth.cultoftimauth.dao.UserDao;
import com.cult_of_tim.auth.cultoftimauth.dao.mock.UserDaoMock;
import com.cult_of_tim.auth.cultoftimauth.service.UserService;
import com.cult_of_tim.auth.cultoftimauth.service.impl.UserMockService;
import com.cult_of_tim.auth.cultoftimauth.util.UserChecker;
import com.cult_of_tim.auth.cultoftimauth.util.UserCheckerImpl;
import com.cult_of_tim.auth.cultoftimauth.util.WithoutPasswordChecker;
import com.cult_of_tim.auth.cultoftimauth.validator.EmailValidator;
import com.cult_of_tim.auth.cultoftimauth.validator.PasswordValidator;
import com.cult_of_tim.auth.cultoftimauth.validator.impl.EmailValidatorImpl;
import com.cult_of_tim.auth.cultoftimauth.validator.impl.PasswordValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CultOfTimAuthConfiguration {


    //@Bean
    //@ConditionalOnProperty(prefix = "com.cult-of-tim.auth", name = "user-checker", value = "no-password")
    //public UserChecker noPasswordChecker() {
    //    return new WithoutPasswordChecker();
    //}

    @Bean
    @ConditionalOnMissingBean(UserChecker.class)
    public UserChecker normalUserChecker() {
        return new UserCheckerImpl();
    }

}
