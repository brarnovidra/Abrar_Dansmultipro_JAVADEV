package com.dans.pro.mrbro.recruitment.controller;

import com.dans.pro.mrbro.recruitment.service.PositionsService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"*"}, exposedHeaders = {"Content-Disposition","content-disposition"}, allowedHeaders = {"*"}, maxAge = 3600)
@RestController
@RequestMapping("/api/recruitment")
public class PositionsController {

    private final PositionsService positionsService;

    public PositionsController(PositionsService positionsService) {
        this.positionsService = positionsService;
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/positions")
    public ResponseEntity positions() {

        return new ResponseEntity(positionsService.Jobs(), HttpStatus.OK);

    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/positions/{jobId}")
    public ResponseEntity<?> positionsDetail(@PathVariable @NotBlank String jobId) {

        return new ResponseEntity(positionsService.JobsDetail(jobId), HttpStatus.OK);

    }

}
