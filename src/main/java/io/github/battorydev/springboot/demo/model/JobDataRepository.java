package io.github.battorydev.springboot.demo.model;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Repository class for keeping data from JSON file.
 * (Singleton class)
 */
public class JobDataRepository {

    private static final Logger LOGGER = LogManager.getLogger();

    private static JobDataRepository instance;

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
            JobJsonObject[] jobRecords = map.readValue(ResourceUtils.getFile("data/salary_survey-3.json"), JobJsonObject[].class);

            Arrays.stream(jobRecords).forEach(job -> {
                // TODO transform data

                try {
                    NumberFormat.getInstance().parse(job.getSalary()).doubleValue();
                    records.add(job);
                } catch (ParseException | NumberFormatException e) {
                    LOGGER.error("Error salary format on record. Value:" + job.getSalary());
                    errorRecords.add(job);
                }

            });
            LOGGER.info("Read Total:{} Error:{}", records.size() + errorRecords.size(), errorRecords.size());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JobDataRepository getInstance() {
        //if (instance == null) {
        instance = new JobDataRepository();
        //}
        return instance;
    }

    /**
     * Gets all valid records
     *
     * @return List of valid records.
     */
    public List<JobJsonObject> getAll() {
        return records;
    }
}
