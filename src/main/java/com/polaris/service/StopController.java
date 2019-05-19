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

    @PutMapping("/{id}/tags")
    public ResponseEntity<?> incrementTag(@PathVariable String id, @RequestBody DtoTags tagCounts) {
        Stop stop = verifyStop(repo.findById(id), id);
        if (stop == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(String.format("Stop with id %s not found", id));
        }

        for (String tag : tagCounts.getTags().keySet()) {
            try {
                int count = tagCounts.getTags().get(tag);
                stop.updateTagCount(tag, count);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(String.format("Tag %s not found", tag));
            }
        }

        repo.save(stop);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(stop);
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<?> addImage(@PathVariable String id, @RequestBody DtoImage imageInfo) {
        Stop stop = verifyStop(repo.findById(id), id);
        if (stop == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(String.format("Stop with id %s not found", id));
        }

        Image image = new Image(imageInfo);
        stop.addImage(image);
        repo.save(stop);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(stop);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStop(@PathVariable String id) {
        Stop stop = verifyStop(repo.findById(id), id);
        if (stop == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(String.format("Stop with id %s not found", id));
        }
        return ResponseEntity.status(HttpStatus.OK)
                             .body(stop);
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