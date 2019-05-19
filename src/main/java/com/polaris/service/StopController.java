package com.polaris.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path="/stops")
public class StopController {
    @Autowired
    private StopRepository repo;

    // TODO should probably remove this functionality
    @PostMapping
    public @ResponseBody String createStop(@RequestBody Stop stop) {
        repo.save(stop);
        return String.format("Added %s", stop);
    }

    @PutMapping("/{id}/increase/{tag}")
    public ResponseEntity<String> incrementTag(@PathVariable String id, @PathVariable String tag) {
        Stop stop = verifyStop(repo.findById(id), id);
        if (stop == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(String.format("Stop with id %s not found", id));
        }

        try {
            stop.incrementTag(tag);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(String.format("Tag %s not found", tag));
        }

        repo.save(stop);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(String.format("Updated stop %s successfully", id));
    }

    @PutMapping("/{id}/decrease/{tag}")
    public ResponseEntity<String> decrementTag(@PathVariable String id, @PathVariable String tag) {
        Stop stop = verifyStop(repo.findById(id), id);
        if (stop == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(String.format("Stop with id %s not found", id));
        }

        try {
            stop.decrementTag(tag);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(String.format("Tag %s not found", tag));
        }

        repo.save(stop);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(String.format("Updated stop %s successfully", id));
    }

    @PutMapping("/{id}/add/{imagUrl}")
    public ResponseEntity<String> addImage(@PathVariable String id, @PathVariable String imageUrl) {
        Stop stop = verifyStop(repo.findById(id), id);
        if (stop == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(String.format("Stop with id %s not found", id));
        }

        stop.addImage(imageUrl);
        repo.save(stop);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(String.format("Updated stop %s successfully", id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Stop>> getStop(@PathVariable String id) {
        // return Optional.ofNullable(verifyStop(repo.findById("" + id), id));
        Stop stop = verifyStop(repo.findById(id), id);
        Optional<Stop> result = Optional.ofNullable(stop);
        if (stop == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(result);  // TODO
                                 //.body(String.format("Stop with id %s not found", id));
        }
        return ResponseEntity.status(HttpStatus.OK)
                             .body(result);
    }

    // TODO should probably remove this functionality
    @DeleteMapping("/{id}")
    public @ResponseBody String deleteStop(@PathVariable String id) {
        repo.deleteById(id);
        return "Deleted " + id;
    }

    private Stop verifyStop(Optional<Stop> stopResult, String id) {
        if (stopResult.isPresent()) {
            return stopResult.get();
        } else {
            Stop stop = new Stop(id);
            /**
             * TODO
             * - should probably also set the stop name when creating the new stop
             * - should do some sort of verification before just creating this stop
                 and adding to the database -- it will likely be fine if this gets
                 called fom within OneBusAway, but since our API is public, someone
                 could try to GET a stop with a typo in the id and accidentally make
                 a new stop with a nonexistent id
                 - return null in this case
               - we can probably use OneBusAway's API for both of these:
                    http://developer.onebusaway.org/modules/onebusaway-application-modules/current/api/where/methods/stop.html
             */
            repo.save(stop);
            return stop;
        }
    }
}