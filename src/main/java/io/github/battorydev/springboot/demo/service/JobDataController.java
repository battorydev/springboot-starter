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
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class JobDataController {

    private static final Logger LOGGER = LogManager.getLogger();

    @GetMapping("/job_data")
    @ResponseBody
    public MappingJacksonValue getJobData(@RequestParam(required = false, value = "fields") String fields,
                                          @RequestParam(required = false, value = "sort") String sortFields,
                                          @RequestParam(required = false, value = "sort_type") String sortType,
                                          @RequestParam Map<String, String> allParam
    ) {
        LOGGER.info("field={}, sort={}, sort_type={}, allParam={}", fields, sortFields, sortType,
                String.valueOf(allParam));
        String condition = allParam.get("condition");
        List<JobJsonObject> result = JobDataRepository.getInstance().getAll();

        if (result == null) {
            return new MappingJacksonValue(new ArrayList<>());
        }

        if (condition != null) {
            // TODO job title equals ...
            // TODO gender equals ...
            // TODO salary (less than ..., equals ...,)
            if (condition.startsWith("salary>=")) {
                String val = condition.split(">=")[1];
                try {
                    double target = Double.parseDouble(val);
                    result = result.stream().filter(job -> Double.parseDouble(job.getSalary()) >= target).collect(
                            Collectors.toList());
                } catch (NumberFormatException | NullPointerException e) {
                    LogManager.getLogger().warn(e.getMessage(), e);
                }
            }
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
        } else {
            SimpleFilterProvider titleFilter = new SimpleFilterProvider();
            titleFilter.addFilter("AttributeFilter.ID", SimpleBeanPropertyFilter.serializeAll());
            mapping.setFilters(titleFilter);
        }
        LOGGER.info("Total result returned: {}", result.size());
        return mapping;
    }
}
