package seliv.randomizer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@Api(tags = {"Randomizer endpoints"}, produces = "application/json")
@RestController
@RequestMapping("/tokens")
public class RandomizerController {
    private static final ConcurrentMap<String, String> tokens = new ConcurrentHashMap<>();

    @ApiOperation(value = "Resets responses")
    @PostMapping("/reset")
    public ResponseEntity<Void> resetTokens() {
        log.info("UserController.resetTokens");
        tokens.clear();
        return ResponseEntity.ok(null);
    }

    @ApiOperation(value = "Returns tokens")
    @GetMapping
    public ResponseEntity<Map<String, String>> getTokenMap() {
        return ResponseEntity.ok(tokens);
    }

    @ApiOperation(value = "Submits a token")
    @PostMapping(value = {"/{pal}/{token}", "/{pal}"})
    public ResponseEntity<Map<String, String>> submitToken(@PathVariable String pal, @PathVariable(required = false) String token) {
        log.info("UserController.submitToken: " + pal + ", " + token);
        if (token == null) {
            tokens.remove(pal);
        } else {
            tokens.put(pal, token);
        }
        log.info("tokens = " + tokens);
        return ResponseEntity.ok(tokens);
    }
}
