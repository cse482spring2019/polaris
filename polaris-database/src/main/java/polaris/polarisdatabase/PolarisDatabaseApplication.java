package polaris.polarisdatabase;

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
        final Stop testStop = new Stop("Tyler");
        testStop.setName("Best Roomate ever");
        List<String> urls = new LinkedList<>();
        urls.add("www.testing1");
        urls.add("wwww.testing2");
        testStop.setImageUrls(urls);

        TagStore tg = new TagStore();
        tg.incrementTag(TagStore.BENCHES);

        repository.deleteAll();
        repository.save(testStop);

        final Optional<Stop> opResult = repository.findById(testStop.getId());
       Assert.isTrue(opResult.isPresent(), "Cannot find user.");

       final Stop result = opResult.get();

       System.out.println(result);
   }

}

