package site.lawmate.user.controller;


import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import site.lawmate.user.component.Messenger;
import site.lawmate.user.domain.dto.ProductDto;
import site.lawmate.user.service.ProductService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "/product")
@Slf4j
@Controller
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        @ApiResponse(responseCode = "404", description = "Customer not found")
})
public class ProductController {
    private final ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<Messenger> saveProduct(@RequestBody ProductDto dto) {
        log.info("Parameters received through controller" + dto);
        return ResponseEntity.ok(productService.save(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProductDto>> findById(@RequestParam("id") Long id) {
        log.info("Parameter information of findById: " + id);
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> findAll(Long id) throws SQLException {
        return ResponseEntity.ok(productService.findAll());
    }

}
