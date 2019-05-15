package polaris.polarisdatabase;

import com.microsoft.azure.spring.data.documentdb.repository.DocumentDbRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StopRepository extends DocumentDbRepository<Stop, String> { 
    public Stop findById(long id);

    public Iterable<Stop> findAll();

    // This is not just save because the save method is defined in 
    // DocumentDbRepository, and has a wack return type.
    public int saveStop(Stop stop);

    public void deleteById(long id);
}