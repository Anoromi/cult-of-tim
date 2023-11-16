package com.example.cult_of_tim.cultoftim;

import com.example.cult_of_tim.cultoftim.util.InitialDatabaseSeeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
//@EnableJpaRepositories()
@EntityScan()
public class CultOfTimApplication implements CommandLineRunner {

    @Autowired(required = false)
    InitialDatabaseSeeder seeder;

    ///@Autowired(required = false)
    ///public CultOfTimApplication(InitialDatabaseSeeder seeder) {
    ///    this.seeder = seeder;
    ///}





    public static void main(String[] args) {
        SpringApplication.run(CultOfTimApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if(seeder != null)
            seeder.run();
    }
}
