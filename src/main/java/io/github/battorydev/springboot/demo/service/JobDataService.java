package io.github.battorydev.springboot.demo.service;

import io.github.battorydev.springboot.demo.dao.JobDataRepository;
import io.github.battorydev.springboot.demo.model.JobJsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JobDataService {

    private static final Logger LOGGER = LogManager.getLogger();

    private final JobDataRepository jobDataRepository;

    public JobDataService(JobDataRepository jobDataRepository) {
        this.jobDataRepository = jobDataRepository;
    }

    public List<JobJsonObject> getJobData(Map<String, String> allParam, String sortFields, String sortType) {

        List<JobJsonObject> result;

        // checks condition parameter to filter job_data
        String condition = allParam.get("condition");
        if (condition != null) {
            result = jobDataRepository.getValidSalaryRecord();

            // job title
            if (condition.startsWith("title")) {
                String val = condition.replaceFirst("title=", "");
                result = result.stream().filter(job -> job.getTitle() != null && job.getTitle().equalsIgnoreCase(val)).collect(Collectors.toList());

            }

            // gender
            if (condition.startsWith("gender")) {
                String val = condition.replaceFirst("gender=", "");
                result = result.stream().filter(job -> job.getGender() != null && job.getGender().equalsIgnoreCase(val)).collect(Collectors.toList());
            }

            // salary
            if (condition.startsWith("salary")) {
                String val;
                if (condition.startsWith("salary>=")) {
                    val = condition.replaceFirst("salary>=", "");
                } else if (condition.startsWith("salary<=")) {
                    val = condition.replaceFirst("salary<=", "");
                } else if (condition.startsWith("salary>")) {
                    val = condition.replaceFirst("salary>", "");
                } else if (condition.startsWith("salary<")) {
                    val = condition.replaceFirst("salary<", "");
                } else if (condition.startsWith("salary=")) {
                    val = condition.replaceFirst("salary=", "");
                } else {
                    LOGGER.warn("Operation is not valid.");
                    throw new IllegalArgumentException("Illegal Operation");
                }

                try {
                    double target = Double.parseDouble(val);
                    result = result.stream()
                            .filter((job) -> Double.parseDouble(job.getSalary()) >= target)
                            .collect(Collectors.toList());
                } catch (NumberFormatException | NullPointerException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        } else if (sortFields != null && sortFields.contains("salary")) {
            result = jobDataRepository.getValidSalaryRecord();
        } else {
            result = jobDataRepository.getAll();
        }

        // check sort and sort_type parameter
        if (sortFields != null) {
            result.sort((job1, job2) -> {
                if ("salary".equalsIgnoreCase(sortFields)) {
                    if (sortType != null && sortType.equalsIgnoreCase("DESC")) {
                        return (int) (Double.parseDouble(job2.getSalary()) - Double.parseDouble(job1.getSalary()));
                    } else {
                        return (int) (Double.parseDouble(job1.getSalary()) - Double.parseDouble(job2.getSalary()));
                    }
                } else {
                    if (sortType != null && sortType.equalsIgnoreCase("DESC")) {
                        return String.valueOf(job2.getByName(sortFields)).compareToIgnoreCase(String.valueOf(job1.getByName(sortFields)));
                    } else {
                        return String.valueOf(job1.getByName(sortFields)).compareToIgnoreCase(String.valueOf(job2.getByName(sortFields)));
                    }
                }
            });
        }

        return result;
    }
}
