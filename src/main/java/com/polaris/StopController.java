package com.polaris;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping(path="/stops")
public class StopController {
    @Autowired
    private StopRepository repo;

    @PostMapping
    public @ResponseBody String createStop(@RequestBody Stop stop) {
        repo.save(stop);
        return String.format("Added %s", stop);
    }

    @GetMapping
    public @ResponseBody Iterable<Stop> getAllStops() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody Optional<Stop> getStop(@PathVariable long id) {
        return Optional.ofNullable(repo.findById(id));
    }

    @DeleteMapping("/{id}")
    public @ResponseBody String deleteStop(@PathVariable long id) {
        repo.deleteById(id);
        return "Deleted " + id;
    }
}
