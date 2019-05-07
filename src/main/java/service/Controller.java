package service;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Response greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Response(counter.incrementAndGet(),
                            String.format("Hi, %s!", name));
    }

    @RequestMapping("/foo")
    public Response foo(@RequestParam(value="bar", defaultValue="") String bar) {
        return new Response(counter.incrementAndGet(),
                            String.format("foo %s baz", bar));
    }
}
