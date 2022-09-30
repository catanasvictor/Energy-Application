package ro.tuc.ds2020.controllers;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.config.ApplianceService;
import ro.tuc.ds2020.dtos.RecordDTO;
import ro.tuc.ds2020.services.RecordService;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin("*")
public class RecordController {

    private final RecordService recordService;

    private final ApplianceService applianceService;

    public RecordController(RecordService recordService, ApplianceService applianceService) {
        this.recordService = recordService;
        this.applianceService = applianceService;
    }

    @GetMapping("/record")
    public ResponseEntity<List<RecordDTO>> getAllRecords() {
        List<RecordDTO> dtos = recordService.findAllRecords();
        for (RecordDTO dto : dtos) {
            Link recordLink = linkTo(methodOn(RecordController.class)
                    .getRecordById(dto.getId())).withRel("recordDetails");
            dto.add(recordLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping("/record")
    public ResponseEntity<RecordDTO> createRecord(@RequestBody RecordDTO recordDTO) {
        return new ResponseEntity<>(recordService.insert(recordDTO), HttpStatus.CREATED);
    }

    @GetMapping("/record/{id}")
    public RecordDTO getRecordById(@PathVariable UUID id) {
        return recordService.findRecordById(id);
    }

    @PutMapping("/record")
    public ResponseEntity<RecordDTO> updateRecord(@RequestBody RecordDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(recordService.update(dto));
    }

    @DeleteMapping("/record/{id}")
    public ResponseEntity<RecordDTO> deleteRecordById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(recordService.deleteRecordById(id));
    }

    @GetMapping("/chart/{id}/{data}")
    public ResponseEntity getRecordById(@PathVariable UUID id, @PathVariable String data) {
        return ResponseEntity.status(HttpStatus.OK).body(recordService.chartClient(id, data));
    }

    @GetMapping("/record/user/{id}")
    public ResponseEntity<List<RecordDTO>> findRecordsByUser(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(recordService.findRecordsByUser(id));
    }
}