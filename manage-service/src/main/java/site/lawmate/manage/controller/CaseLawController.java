package site.lawmate.manage.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.lawmate.manage.domain.dto.CaseLawDetailDto;
import site.lawmate.manage.domain.dto.CaseLawDto;
import site.lawmate.manage.domain.dto.SearchCriteria;
import site.lawmate.manage.service.CaseLawService;
import site.lawmate.user.domain.dto.UserDto;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/caselaw")
public class CaseLawController {

    private final CaseLawService caselawService;

    @GetMapping("/all")
    public ResponseEntity<List<CaseLawDto>> getCaseLawList() {
        return ResponseEntity.ok(caselawService.getCaseLawList());
    }

    @GetMapping("/{serialNumber}")
    public ResponseEntity<CaseLawDetailDto> getCaseLawDetail(@PathVariable("serialNumber") String serialNumber) {
        log.info("serialNumber: {}", serialNumber);
        return ResponseEntity.ok(caselawService.getCaseLawDetail(serialNumber));
    }

    @PostMapping("/search")
    public ResponseEntity<List<CaseLawDto>> getCaseLawListByKeyword(@RequestBody SearchCriteria searchCriteria) {
        return ResponseEntity.ok(caselawService.getCaseLawListByKeyword(searchCriteria));
    }


}
