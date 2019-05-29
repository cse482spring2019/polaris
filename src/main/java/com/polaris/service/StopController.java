package com.polaris.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping(path="/stops")
public class StopController {

    public static final String ONE_BUS_AWAY_STOP_API_URL = "http://api.pugetsound.onebusaway.org/api/where/stop/";

    @Autowired
    private StopRepository repo;

    @CrossOrigin
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

    @CrossOrigin
    @PutMapping("/{id}/yes/{count}")
    public ResponseEntity<?> updateYesRating(@PathVariable String id, @PathVariable int count) {
        Stop stop = verifyStop(repo.findById(id), id);
        if (stop == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(String.format("Stop with id %s not found", id));
        }

        stop.setYesAccessible(count);
        repo.save(stop);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(stop);
    }

    @CrossOrigin
    @PutMapping("/{id}/no/{count}")
    public ResponseEntity<?> updateNoating(@PathVariable String id, @PathVariable int count) {
        Stop stop = verifyStop(repo.findById(id), id);
        if (stop == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(String.format("Stop with id %s not found", id));
        }

        stop.setNoAccessible(count);
        repo.save(stop);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(stop);
    }

    @CrossOrigin
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

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<?> getStop(@PathVariable String id) {
        Stop stop = verifyStop(repo.findById(id), id);
        if (stop == null) {
            String res;
            if (id.equals("false")) {
                res = "No stop number was provided.";
            } else {
                res = "Stop number " + id + " was not found.";
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
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

        String[] nameAndDirection = null;
        try {
            nameAndDirection = getNameAndDirection(response);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Stop stop = new Stop(id, nameAndDirection[0], nameAndDirection[1]);
        repo.save(stop);
        return stop;
    }

    // TODO: This depends on OneBusAway API having the response in a specific format
    private String[] getNameAndDirection(ResponseEntity<String> response) throws IOException {
        String xmlResponseBody = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(xmlResponseBody);
        JsonNode data = root.get("data");
        JsonNode entry = data.get("entry");

        String[] nameAndDirection = new String[2];
        nameAndDirection[0] = entry.get("name").asText();
        nameAndDirection[1] = entry.get("direction").asText();

        return nameAndDirection;
    }
}