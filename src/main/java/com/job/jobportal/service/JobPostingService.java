package com.job.jobportal.service;

import com.job.jobportal.entity.JobPosting;

import java.util.List;
import java.util.Optional;

public interface JobPostingService {
    public JobPosting createJobPosting(JobPosting jobPosting);
    public JobPosting getJobPostingById(Long id);

    public List<JobPosting> getAllJobPostings();
    public JobPosting updateJobPosting(Long id, JobPosting jobPosting);
    public void deleteJobPosting(Long id);
    public List<JobPosting> searchJobPostings(String keyword);
}
