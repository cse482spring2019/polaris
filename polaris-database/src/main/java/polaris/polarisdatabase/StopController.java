package polaris.polarisdatabase;

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

    @PutMapping("/{id}/increase/{tag}")
    public @ResponseBody String incrementTag(@PathVariable int id, @PathVariable String tag) {
        Stop stop = repo.findById(id);
        if (stop == null) {
            return String.format("Stop with id %d not found", id);
        }

        stop.incrementTag(tag);
        repo.save(stop);
        return String.format("Updated stop %d successfully", id);
    }

    @PutMapping("/{id}/decrease/{tag}")
    public @ResponseBody String decrementTag(@PathVariable int id, @PathVariable String tag) {
        Stop stop = repo.findById(id);
        if (stop == null) {
            return String.format("Stop with id %d not found", id);
        }

        stop.decrementTag(tag);
        repo.save(stop);
        return String.format("Updated stop %d successfully", id);
    }

    @PutMapping("/{id}/add/{imagUrl}")
    public @ResponseBody String addImage(@PathVariable int id, @PathVariable String imageUrl) {
        Stop stop = repo.findById(id);
        if (stop == null) {
            return String.format("Stop with id %d not found", id);
        }

        stop.addImage(imageUrl);
        repo.save(stop);
        return String.format("Updated stop %d successfully", id);
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