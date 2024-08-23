package com.job.jobportal.exception;

public class JobPostingNotFoundException extends RuntimeException{
    public JobPostingNotFoundException(Long id) {
        super("Job posting not found with ID: " + id);
    }
    public JobPostingNotFoundException(String s) {

    }
}
