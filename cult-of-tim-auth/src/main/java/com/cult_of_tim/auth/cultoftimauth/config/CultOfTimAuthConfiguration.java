package com.cult_of_tim.auth.cultoftimauth.config;

import com.cult_of_tim.auth.cultoftimauth.service.UserService;
import com.cult_of_tim.auth.cultoftimauth.util.UserChecker;
import com.cult_of_tim.auth.cultoftimauth.util.UserCheckerImpl;
import com.cult_of_tim.auth.cultoftimauth.util.WithoutPasswordChecker;
import com.cult_of_tim.auth.cultoftimauth.validator.PasswordValidator;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ConditionalOnClass(UserChecker.class)
//@ConditionalOnProperty(name = "my.namespace.serviceA", havingValue = "true")
//@EnableAutoConfiguration
public class CultOfTimAuthConfiguration {



    //@Bean
    //@ConditionalOnProperty(prefix = "com.cult_of_tim.auth", name = "user_checker", value = "no_password")
    //public UserChecker noPasswordChecker() {
    //    return new WithoutPasswordChecker();
    //}

    @Bean
    @ConditionalOnMissingBean(UserChecker.class)
    public UserChecker normalUserChecker() {
       return new UserCheckerImpl();
    }
}
