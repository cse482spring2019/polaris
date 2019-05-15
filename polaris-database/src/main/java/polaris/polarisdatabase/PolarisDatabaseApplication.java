package polaris.polarisdatabase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
// These imports are required for the application.
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
        final Stop testStop = new Stop("" + 143);

        repository.deleteAll();
        repository.save(testStop);

        final Optional<Stop> opResult = repository.findById(testStop.getId());
       Assert.isTrue(opResult.isPresent(), "Cannot find user.");

       final Stop result = opResult.get();

       System.out.println(result);
   }

}

