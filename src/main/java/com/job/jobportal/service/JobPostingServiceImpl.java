package com.job.jobportal.service;

import com.job.jobportal.entity.JobPosting;
import com.job.jobportal.exception.JobPostingNotFoundException;
import com.job.jobportal.repository.JobPostingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class JobPostingServiceImpl implements JobPostingService{

    private final JobPostingRepository jobPostingRepository;

    @Override
    public JobPosting createJobPosting(JobPosting jobPosting) {
        return jobPostingRepository.save(jobPosting);
    }

    @Override
    public JobPosting getJobPostingById(Long id) {
        return jobPostingRepository.findById(id)
                .orElseThrow(() -> new JobPostingNotFoundException(id));
    }

    @Override
    public List<JobPosting> getAllJobPostings() {
        return jobPostingRepository.findAll();
    }

    @Override
    public JobPosting updateJobPosting(Long id, JobPosting jobPostingDetails) {
        JobPosting jobPosting = jobPostingRepository.findById(id)
                .orElseThrow(() -> new JobPostingNotFoundException(id));

        jobPosting.setTitle(jobPostingDetails.getTitle());
        jobPosting.setDescription(jobPostingDetails.getDescription());
        jobPosting.setLocation(jobPostingDetails.getLocation());
        jobPosting.setCompany(jobPostingDetails.getCompany());
        jobPosting.setSalaryRange(jobPostingDetails.getSalaryRange());
        jobPosting.setRequiredSkills(jobPostingDetails.getRequiredSkills());
        jobPosting.setApplicationDeadline(jobPostingDetails.getApplicationDeadline());

        return jobPostingRepository.save(jobPosting);
    }

    @Override
    public void deleteJobPosting(Long id) {
        JobPosting jobPosting = jobPostingRepository.findById(id)
                .orElseThrow(() -> new JobPostingNotFoundException(id));
        jobPostingRepository.deleteById(jobPosting.getId());
    }

    @Override
    public List<JobPosting> searchJobPostings(String keyword) {
        return jobPostingRepository.findByTitleContainingOrLocationContainingOrRequiredSkillsContaining(keyword, keyword, keyword);
    }
}
