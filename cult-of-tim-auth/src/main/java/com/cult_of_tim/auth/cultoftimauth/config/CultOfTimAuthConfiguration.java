package com.cult_of_tim.auth.cultoftimauth.config;

import com.cult_of_tim.auth.cultoftimauth.dao.UserDao;
import com.cult_of_tim.auth.cultoftimauth.dao.mock.UserDaoMock;
import com.cult_of_tim.auth.cultoftimauth.util.UserChecker;
import com.cult_of_tim.auth.cultoftimauth.util.UserCheckerImpl;
import com.cult_of_tim.auth.cultoftimauth.util.WithoutPasswordChecker;
import com.cult_of_tim.auth.cultoftimauth.validator.TokenValidator;
import com.cult_of_tim.auth.cultoftimauth.validator.impl.TokenValidatorNoExpiration;
import com.cult_of_tim.auth.cultoftimauth.validator.impl.TokenValidatorWithExpiration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ComponentScan("com.cult_of_tim.auth")
public class CultOfTimAuthConfiguration {


    //@Bean
    //@ConditionalOnProperty(prefix = "com.cult-of-tim.auth", name = "user-checker", value = "no-password")
    //public UserChecker noPasswordChecker() {
    //    return new WithoutPasswordChecker();
    //}

    @Bean
    @ConditionalOnProperty(prefix = "cultoftim.auth", name = "checkPassword", havingValue = "false")
    public UserChecker noPasswordChecker(UserDao userDao) {
        return new WithoutPasswordChecker(userDao);
    }

    @Bean
    @ConditionalOnMissingBean(UserChecker.class)
    public UserChecker defaultChecker(UserDao userDao) {
        return new UserCheckerImpl(userDao);
    }

    @Bean
    @Primary
    public UserDao userDao() {
        return new UserDaoMock();
    }

    @Bean
@ConditionalOnExpression("${cultoftim.auth.dev} or not ${cultoftim.auth.checkTokenExpiry}")
    public TokenValidator noExpirationChecker(UserDao userDao) {
        return new TokenValidatorNoExpiration(userDao);
    }

    @Bean
    @ConditionalOnMissingBean(TokenValidator.class)
    public TokenValidator expirationChecker(UserDao userDao) {
        return new TokenValidatorWithExpiration(userDao);
    }
}
