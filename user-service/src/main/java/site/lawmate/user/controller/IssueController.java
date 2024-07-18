package site.lawmate.user.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.lawmate.user.component.Messenger;
import site.lawmate.user.domain.dto.IssueDto;
import site.lawmate.user.service.IssueService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/issues")
@Slf4j
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        @ApiResponse(responseCode = "404", description = "Customer not found")
})
public class IssueController {
    private final IssueService service;

    @SuppressWarnings("static-access")
    @PostMapping("/save")
    public ResponseEntity<Messenger> save(@RequestBody IssueDto dto) throws SQLException {
        log.info("Parameters received through controller: " + dto);
        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<IssueDto>> findAll() throws SQLException {
        return ResponseEntity.ok(service.findAll());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Messenger> delete(@PathVariable("id") Long id) throws SQLException {
        return ResponseEntity.ok(service.delete(id));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Messenger> update(@RequestBody IssueDto dto){
        return ResponseEntity.ok(service.update(dto));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<IssueDto>> findById(@PathVariable("id") Long id) throws SQLException {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/total")
    public ResponseEntity<Messenger> count() throws SQLException {
        return ResponseEntity.ok(service.count());
    }

    @GetMapping("/search")
    public ResponseEntity<Boolean> existsById(@RequestParam("id") Long id) throws SQLException {
        return ResponseEntity.ok(service.existsById(id));
    }
}
