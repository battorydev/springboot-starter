package io.github.battorydev.springboot.demo.controller;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.github.battorydev.springboot.demo.model.JobJsonObject;
import io.github.battorydev.springboot.demo.service.JobDataService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class JobDataController {

    private static final Logger LOGGER = LogManager.getLogger();

    private final JobDataService jobDataService;

    public JobDataController(JobDataService jobDataService) {
        this.jobDataService = jobDataService;
    }

    @GetMapping("/job_data")
    @ResponseBody
    public MappingJacksonValue getJobData(@RequestParam(required = false, value = "fields") String fields,
                                          @RequestParam(required = false, value = "sort") String sortFields,
                                          @RequestParam(required = false, value = "sort_type") String sortType,
                                          @RequestParam Map<String, String> allParam
    ) {
        LOGGER.info("fields={}, sort={}, sort_type={}, allParam={}", fields, sortFields, sortType,
                String.valueOf(allParam));

        List<JobJsonObject> result = jobDataService.getJobData(allParam, sortFields, sortType);

        // checks fields parameter
        MappingJacksonValue mapping = new MappingJacksonValue(result);
        SimpleFilterProvider titleFilter = new SimpleFilterProvider();
        if (fields != null) {
            titleFilter.addFilter("AttributeFilter.ID", SimpleBeanPropertyFilter.filterOutAllExcept(fields.split(",")));
        } else {
            titleFilter.addFilter("AttributeFilter.ID", SimpleBeanPropertyFilter.serializeAll());
        }
        mapping.setFilters(titleFilter);
        LOGGER.info("Total result returned: {}", result.size());

        return mapping;
    }

}
