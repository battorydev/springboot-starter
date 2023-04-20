package io.github.battorydev.springboot.demo.model;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JobDataRepository {

    private static JobDataRepository instance;

    private List<JobJsonObject> records = new ArrayList<>();

    private JobDataRepository() {
        // singleton

        ObjectMapper map = new ObjectMapper();
        map.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES);
        try {
            JobJsonObject[] jobRecords = map.readValue(ResourceUtils.getFile("data/salary_survey-3.json"), JobJsonObject[].class);

            // sample for testing
            for (int i=0;i<10; i++){
                records.add(jobRecords[i]);
            }
//            Arrays.stream(jobRecords).forEach(job -> {
//                // TODO transform data
//
//
//                records.add(job);
//            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JobDataRepository getInstance() {
        if (instance == null) {
            instance = new JobDataRepository();
        }
        return instance;
    }

    public List<JobJsonObject> getAll() {
        return records;
    }
}
