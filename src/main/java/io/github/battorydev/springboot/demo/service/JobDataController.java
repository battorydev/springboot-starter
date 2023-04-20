package io.github.battorydev.springboot.demo.service;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.github.battorydev.springboot.demo.model.JobDataRepository;
import io.github.battorydev.springboot.demo.model.JobJsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    public MappingJacksonValue getJobData(@RequestParam(required = false, value = "fields") String fields,
                                          @RequestParam(required = false, value = "sort") String sortFields,
                                          @RequestParam(required = false, value = "sort_type") String sortType) {
        LOGGER.info("field={}, sort={}, sort_type={}", fields, sortFields, sortType);

        List<JobJsonObject> result = JobDataRepository.getInstance().getAll();

        if (result == null) {
            return new MappingJacksonValue(new ArrayList<>());
        }

        if (sortFields != null) {
            result.sort((job1, job2) -> {
                if ("fields".equalsIgnoreCase(sortFields)) {
                    if (sortType == null || sortType.equalsIgnoreCase("DESC")) {
                        return job1.getTitle().compareToIgnoreCase(job2.getTitle());
                    } else {
                        return job2.getTitle().compareToIgnoreCase(job1.getTitle());
                    }
                }

                // TODO sort remaining fields

                return job1.hashCode() - job2.hashCode();
            });
        }

        MappingJacksonValue mapping = new MappingJacksonValue(result);
        if (fields != null) {
            SimpleFilterProvider titleFilter = new SimpleFilterProvider();
            titleFilter.addFilter("AttributeFilter.ID", SimpleBeanPropertyFilter.filterOutAllExcept(fields.split(",")));

            mapping.setFilters(titleFilter);
        }

        return mapping;
    }
}
