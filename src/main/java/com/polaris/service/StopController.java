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
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path="/stops")
public class StopController {

    public static final String ONE_BUS_AWAY_STOP_API_URL = "http://api.pugetsound.onebusaway.org/api/where/stop/";
    public static final String ONE_BUS_AWAY_API_KEY = "572b4576-1384-4b7f-8644-044f868780f8";

    @Autowired
    private StopRepository repo;

    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStop(@PathVariable String id, @RequestBody Stop info) {
        Stop stop = verifyStop(repo.findById(id), id);
        if (stop == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("Stop with id %s not found", id));
        }

        if (info.getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Stop id not provided");
        }
        if (!id.equals(info.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(String.format("Provided id %s does not match stop id %s", info.getId(), id));
        }

        // name and direction should not be changed
        if (info.getName() != null && !stop.getName().equals(info.getName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(String.format("Provided name %s does not match stop name %s", info.getName(), stop.getName()));
        }
        if (info.getDirection() != null && !stop.getDirection().equals(info.getDirection())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(String.format("Provided direction %s does not match direction name %s", info.getDirection(), stop.getDirection()));
        }

        if (info.getImages() != null) {
            stop.setImages(info.getImages());
        }
        if (info.getTags() != null) {
            List<Tag> infoTags = info.getTags();
            for (Tag tag : infoTags) {
                String label = tag.getLabel();
                int count = tag.getCount();
                if (count < 0) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(String.format("Count for tag %s cannot be below 0 (was %d)", tag, count));
                }
                try {
                    stop.updateTagCount(label, count);
                } catch (IllegalArgumentException e) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(String.format("Invalid tag %s supplied", tag));
                }
            }
        }

        if (info.getYesAccessible() != null) {
            int count = info.getYesAccessible();
            if (count < 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(String.format("Count for yesAccessible cannot be below 0 (was %d)", count));
            }
            stop.setYesAccessible(count);
        }
        if (info.getNoAccessible() != null) {
            int count = info.getNoAccessible();
            if (count < 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(String.format("Count for noAccessible cannot be below 0 (was %d)", count));
            }
            stop.setNoAccessible(count);
        }

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
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(res);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(stop);
    }

    private Stop verifyStop(Optional<Stop> stopResult, String id) {
        if (stopResult.isPresent()) {
            return stopResult.get();
        }

        RestTemplate rest = new RestTemplate();
        String obaUrl = ONE_BUS_AWAY_STOP_API_URL + id + ".json?key=" + ONE_BUS_AWAY_API_KEY;
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