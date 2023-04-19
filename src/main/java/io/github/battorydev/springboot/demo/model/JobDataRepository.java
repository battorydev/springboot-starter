package io.github.battorydev.springboot.demo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JobDataRepository {

    private static JobDataRepository instance;

    private List<JobRecord> record = Collections.emptyList();

    private JobDataRepository(){
        // singleton

        // TODO read & store JSON
        initSampleData();
    }

    public static JobDataRepository getInstance(){
        if (instance == null){
            instance = new JobDataRepository();
        }
        return instance;
    }

    public List<JobRecord> getAll(){
       return record;
    }

    private void initSampleData() {
        List<JobRecord> sample = new ArrayList<>();
        JobRecord r = new JobRecord();
        r.setJob("Engineer");
        sample.add(r);
        record = Collections.unmodifiableList(sample);
    }
}
