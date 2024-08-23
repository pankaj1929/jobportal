package com.job.jobportal;
import com.job.jobportal.exception.JobPostingNotFoundException;
import com.job.jobportal.entity.JobPosting;
import com.job.jobportal.repository.JobPostingRepository;
import com.job.jobportal.service.JobPostingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JobPostingServiceTest {

    @Mock
    private JobPostingRepository jobPostingRepository;

    @InjectMocks
    private JobPostingServiceImpl jobPostingService;

    private JobPosting jobPosting;
    private Date applicationDeadline;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Parse the date string to a Date object
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        applicationDeadline = dateFormat.parse("2024-12-31");

        jobPosting = new JobPosting();
        jobPosting.setId(1L);
        jobPosting.setTitle("Java Developer");
        jobPosting.setDescription("Responsible for developing backend services.");
        jobPosting.setLocation("Hyderabad");
        jobPosting.setCompany("Tech Corp");
        jobPosting.setSalaryRange("70,000 - 90,000 INR");
        jobPosting.setRequiredSkills(Arrays.asList("Java", "Spring Boot", "Microservices"));
        jobPosting.setApplicationDeadline(applicationDeadline);
    }

    @Test
    void createJobPostingTest() {
        when(jobPostingRepository.save(jobPosting)).thenReturn(jobPosting);
        JobPosting createdJobPosting = jobPostingService.createJobPosting(jobPosting);
        assertNotNull(createdJobPosting);
        assertEquals("Java Developer", createdJobPosting.getTitle());
    }

    @Test
    void getJobPostingById_WhenFoundTest() {
        when(jobPostingRepository.findById(1L)).thenReturn(Optional.of(jobPosting));
        JobPosting foundJobPosting = jobPostingService.getJobPostingById(1L);
        assertNotNull(foundJobPosting);
        assertEquals("Java Developer", foundJobPosting.getTitle());
    }

    @Test
    void getJobPostingById_WhenNotFoundTest() {
        when(jobPostingRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(JobPostingNotFoundException.class, () -> jobPostingService.getJobPostingById(1L));
    }

    @Test
    void getAllJobPostingsTest() {
        when(jobPostingRepository.findAll()).thenReturn(Arrays.asList(jobPosting));
        List<JobPosting> allJobPostings = jobPostingService.getAllJobPostings();
        assertEquals(1, allJobPostings.size());
        assertEquals("Java Developer", allJobPostings.get(0).getTitle());
    }

    @Test
    void updateJobPosting_WhenFoundTest() {
        when(jobPostingRepository.findById(1L)).thenReturn(Optional.of(jobPosting));
        when(jobPostingRepository.save(jobPosting)).thenReturn(jobPosting);

        JobPosting jobPostingDetails = getJobPosting();

        JobPosting updatedJobPosting = jobPostingService.updateJobPosting(1L, jobPostingDetails);

        assertNotNull(updatedJobPosting);
        assertEquals("Java Developer", updatedJobPosting.getTitle());
        assertEquals("Lead development and mentoring team members.", updatedJobPosting.getDescription());
    }

    private JobPosting getJobPosting() {
        JobPosting jobPostingDetails = new JobPosting();
        jobPostingDetails.setTitle("Java Developer");
        jobPostingDetails.setDescription("Lead development and mentoring team members.");
        jobPostingDetails.setLocation("Hyderabad");
        jobPostingDetails.setCompany("Infosys");
        jobPostingDetails.setSalaryRange("100,000 - 120,000 INR");
        jobPostingDetails.setRequiredSkills(Arrays.asList("Java", "Spring Boot", "Microservices", "Leadership"));
        jobPostingDetails.setApplicationDeadline(applicationDeadline);
        return jobPostingDetails;
    }

    @Test
    void updateJobPosting_WhenNotFoundTest() {
        when(jobPostingRepository.findById(1L)).thenReturn(Optional.empty());
        JobPosting jobPostingDetails = new JobPosting();
        assertThrows(JobPostingNotFoundException.class, () -> jobPostingService.updateJobPosting(1L, jobPostingDetails));
    }

    @Test
    void deleteJobPosting_WhenFoundTest() {
        when(jobPostingRepository.findById(1L)).thenReturn(Optional.of(jobPosting));
        doNothing().when(jobPostingRepository).deleteById(1L);
        assertDoesNotThrow(() -> jobPostingService.deleteJobPosting(1L));
        verify(jobPostingRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteJobPosting_WhenNotFoundTest() {
        when(jobPostingRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(JobPostingNotFoundException.class, () -> jobPostingService.deleteJobPosting(1L));
    }
}

