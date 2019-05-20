package com.polaris.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Controller
@RequestMapping(path="/stops")
public class StopController {

    public static final String ONE_BUS_AWAY_STOP_API_URL = "http://api.pugetsound.onebusaway.org/api/where/stop/";

    @Autowired
    private StopRepository repo;

    @CrossOrigin
    @PutMapping("/{id}/tags")
    public ResponseEntity<?> updateTagCounts(@PathVariable String id, @RequestBody DtoTags tagCounts) {
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

    @CrossOrigin
    @PutMapping("/{id}/image")
    public ResponseEntity<?> addImage(@PathVariable String id, @RequestBody DtoAddImage imageInfo) {
        Stop stop = verifyStop(repo.findById(id), id);
        if (stop == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(String.format("Stop with id %s not found", id));
        }

        for (Image existing : stop.getImages()) {
            if (imageInfo.getUrl().equals(existing.getUrl())) {
                // image already exists in stop (update alt text and date)
                existing.setAltText(imageInfo.getAltText());
                existing.setDate(imageInfo.getDate());
                repo.save(stop);
                return ResponseEntity.status(HttpStatus.OK)
                                     .body(stop);
            }
        }

        // add image to stop
        Image image = new Image(imageInfo);
        stop.addImage(image);
        repo.save(stop);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(stop);
    }

    @CrossOrigin
    @PutMapping("/{id}/image/score")
    public ResponseEntity<?> updateImageScores(@PathVariable String id, @RequestBody DtoImageScores imageInfo) {
        Stop stop = verifyStop(repo.findById(id), id);
        if (stop == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(String.format("Stop with id %s not found", id));
        }

        for (Image existing : stop.getImages()) {
            if (imageInfo.getUrl().equals(existing.getUrl())) {
                // update score on existing image
                if (imageInfo.getUp() != null) {
                    existing.setUp(imageInfo.getUp());
                }
                if (imageInfo.getDown() != null) {
                    existing.setDown(imageInfo.getDown());
                }
                repo.save(stop);
                return ResponseEntity.status(HttpStatus.OK)
                                     .body(stop);
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(String.format("Image with url %s not found for stop id %s", imageInfo.getUrl(), id));
    }

    @CrossOrigin
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
        }

        RestTemplate rest = new RestTemplate();
        // String obaUrl = ONE_BUS_AWAY_STOP_API_URL + id + ".json"?key=TODO;
        String obaUrl = ONE_BUS_AWAY_STOP_API_URL + id + ".json?key=TEST";
        ResponseEntity<String> response = rest.getForEntity(obaUrl, String.class);
        if (!response.hasBody()) {
            return null;
        }

        Stop stop = new Stop(id);
        repo.save(stop);
        return stop;
    }
}