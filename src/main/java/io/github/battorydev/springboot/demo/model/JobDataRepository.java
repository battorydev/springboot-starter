package io.github.battorydev.springboot.demo.model;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.battorydev.springboot.demo.util.SalaryConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Repository class for keeping data from JSON file.
 * (Singleton class)
 */
@Repository
public class JobDataRepository {

    private static final Logger LOGGER = LogManager.getLogger();

    private final List<JobJsonObject> records = new ArrayList<>();

    private final List<JobJsonObject> errorRecords = new ArrayList<>();

    private JobDataRepository() {
        loadData();
    }

    /**
     * Load data from JSON file
     *
     * @throws RuntimeException if file not found or unable to parse value
     */
    private void loadData() {
        ObjectMapper map = new ObjectMapper();
        map.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES);
        try {
            JobJsonObject[] jobRecords = map.readValue(ResourceUtils.getFile("data/salary_survey-3.json"),
                    JobJsonObject[].class);

            Arrays.stream(jobRecords).forEach(job -> {
                JobJsonObject convertedJob = SalaryConverter.convertSalary(job);
                if (convertedJob != null) {
                    records.add(convertedJob);
                } else {
                    errorRecords.add(job);
                }
            });
            LOGGER.info("Read Total:{} Error:{}", records.size() + errorRecords.size(), errorRecords.size());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<JobJsonObject> getValidSalaryRecord() {
        return records;
    }

    /**
     * Gets all valid records
     *
     * @return List of valid records.
     */
    public List<JobJsonObject> getAll() {
        List<JobJsonObject> result = new ArrayList<>();
        result.addAll(records);
        result.addAll(errorRecords);
        return result;
    }
}
