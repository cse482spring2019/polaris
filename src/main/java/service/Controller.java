import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class Controller {

    // won't actually need this map once we have database set up
    private Map<Long, BusStop> database;

    public Controller() {
        this.database = new HashMap<>();
    }

    /**
     * Returns the bus stop with the given id
     *
     * @param id id of the bus stop
     * @return the bus stop with that id
     */
    @GetMapping("/stops/{id}")
    public BusStop getBusStop(@PathVariable long id) {
        return database.get(id);
    }

    @PostMapping("/stops/{id}")
    public ResponseEntity<?> postBusStop(@RequestBody BusStop busStop) throws URISyntaxException {
        database.put(busStop.getId(), busStop);

        Resource resource = assembler

        return ResponseEntity
                .created(new URI(busStop.toString()))
                .body(busStop);
    }
}
