package io.github.battorydev.springboot.demo.service;

import io.github.battorydev.springboot.demo.model.JobDataRepository;
import io.github.battorydev.springboot.demo.model.JobRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JobDataController {

    @GetMapping("/job_data")
    @ResponseBody
    public ResponseEntity<List<JobRecord>> getJobData(){
        List<JobRecord> result = JobDataRepository.getInstance().getAll();
        return ResponseEntity.ok(result);
    }
}
