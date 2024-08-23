package com.job.jobportal.controller;

import com.job.jobportal.entity.JobPosting;
import com.job.jobportal.exception.JobPostingNotFoundException;
import com.job.jobportal.service.JobPostingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-postings")
@AllArgsConstructor
public class JobPostingController {

    private final JobPostingService jobPostingService;

    @Operation(summary = "Create a new job posting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Job posting created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<JobPosting> createJobPosting(@RequestBody JobPosting jobPosting) {
        JobPosting createdJobPosting =  jobPostingService.createJobPosting(jobPosting);
        return new ResponseEntity<>(createdJobPosting, HttpStatus.CREATED);
    }

    @Operation(summary = "Get a job posting by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Job posting found"),
            @ApiResponse(responseCode = "404", description = "Job posting not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<JobPosting> getJobPostingById(@PathVariable Long id) {
        JobPosting jobPosting = jobPostingService.getJobPostingById(id);
        return new ResponseEntity<>(jobPosting, HttpStatus.OK);
    }


    @Operation(summary = "Get all job postings")
    @ApiResponse(responseCode = "200", description = "Found the job postings")
    @GetMapping
    public ResponseEntity<List<JobPosting>> getAllJobPostings() {
        List<JobPosting> jobPostings = jobPostingService.getAllJobPostings();
        return new ResponseEntity<>(jobPostings, HttpStatus.OK);
    }

    @Operation(summary = "Update an existing job posting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Job posting updated successfully"),
            @ApiResponse(responseCode = "404", description = "Job posting not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<JobPosting> updateJobPosting(@PathVariable Long id, @RequestBody JobPosting jobPostingDetails) {
        JobPosting updatedJobPosting = jobPostingService.updateJobPosting(id, jobPostingDetails);
        return new ResponseEntity<>(updatedJobPosting, HttpStatus.OK);
    }

    @Operation(summary = "Delete a job posting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Job posting deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Job posting not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobPosting(@PathVariable Long id) {
        jobPostingService.deleteJobPosting(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Search job postings by keyword")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found job postings",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JobPosting.class)) }),
            @ApiResponse(responseCode = "404", description = "Job postings not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping("/search")
    public List<JobPosting> searchJobPostings(@RequestParam String keyword) {
        List<JobPosting> jobPostings = jobPostingService.searchJobPostings(keyword);

        if (jobPostings.isEmpty()) {
            throw new JobPostingNotFoundException("No job postings found for keyword: " + keyword);
        }

        return jobPostings;
    }
}
