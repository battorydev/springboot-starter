package io.github.battorydev.springboot.demo.service;

import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.github.battorydev.springboot.demo.model.JobDataRepository;
import io.github.battorydev.springboot.demo.model.JobJsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class JobDataController {

    private static final Logger LOGGER = LogManager.getLogger();

    @GetMapping("/job_data")
    @ResponseBody
    public ResponseEntity<List<JobJsonObject>> getJobData(@RequestParam(required = false, value = "fields") String fields,
                                                          @RequestParam(required = false, value = "sort") String sortFields,
                                                          @RequestParam(required = false, value = "sort_type") String sortType) {
        LOGGER.info("field={}, sort={}, sort_type={}",  fields, sortFields, sortType);

        List<JobJsonObject> result = JobDataRepository.getInstance().getAll();

        if (result == null){
            return ResponseEntity.ok(new ArrayList<>());
        }

        if (sortFields != null) {
            result.sort((job1, job2) -> {
                if ("fields".equalsIgnoreCase(sortFields)){
                    if (sortType == null || sortType.equalsIgnoreCase("DESC")) {
                        return job1.getTitle().compareToIgnoreCase(job2.getTitle());
                    } else {
                        return job2.getTitle().compareToIgnoreCase(job1.getTitle());
                    }
                }
                return job1.hashCode() - job2.hashCode();
            });
        }

//        if (fields != null){
//            SimpleFilterProvider titleFilter = new SimpleFilterProvider().addFilter("AttributeFilter.ID", SimpleBeanPropertyFilter.filterOutAllExcept("title"));
//            SimpleFilterProvider retrieveAll = new SimpleFilterProvider().addFilter("AttributeFilter.ID", SimpleBeanPropertyFilter.serializeAll());
//            MappingJacksonValue mapping = new MappingJacksonValue(result);
//            mapping.setFilters(titleFilter);
//            return mapping;
//        } else {
//
//        }
        return ResponseEntity.ok(result);
    }
}
