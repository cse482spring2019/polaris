package polaris;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.util.Assert;

@SpringBootApplication
public class PolarisDatabaseApplication implements CommandLineRunner {

    @Autowired
    private StopRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(PolarisDatabaseApplication.class, args);
    }

    public void run(String... var1) throws Exception {
        final Stop testStop = new Stop();
        repository.save(testStop);
   }

}
