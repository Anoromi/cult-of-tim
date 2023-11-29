package com.example.cult_of_tim.cultoftim;

import com.example.cult_of_tim.cultoftim.util.InitialDatabaseSeeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EntityScan()
@EnableCaching
@EnableScheduling
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
