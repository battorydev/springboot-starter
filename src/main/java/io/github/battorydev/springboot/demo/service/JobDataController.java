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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
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
        List<JobJsonObject> result = Collections.emptyList();

        if (condition != null) {
            result = JobDataRepository.getInstance().getValidSalaryRecord();
            if (condition.startsWith("title")) {
                String val = condition.replaceFirst("title=", "");
                result = result.stream().filter(job -> job.getTitle() != null && job.getTitle().equalsIgnoreCase(val)).collect(Collectors.toList());

            }

            if (condition.startsWith("gender")) {
                String val = condition.replaceFirst("gender=", "");
                result = result.stream().filter(job -> job.getGender() != null && job.getGender().equalsIgnoreCase(val)).collect(Collectors.toList());
            }

            if (condition.startsWith("salary")) {
                String val = null;
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

                if (val != null) {
                    try {
                        double target = Double.parseDouble(val);
                        Predicate<JobJsonObject> filter = (job) -> {
                            return Double.parseDouble(job.getSalary()) >= target;
                        };
                        result = result.stream().filter(filter).collect(
                                Collectors.toList());
                    } catch (NumberFormatException | NullPointerException e) {
                        throw new RuntimeException(e.getMessage());
                    }
                }
            }
        } else {
            result = JobDataRepository.getInstance().getAll();
        }

        if (sortFields != null) {
            result.sort((job1, job2) -> {
                if ("title".equalsIgnoreCase(sortFields)) {
                    if (sortType != null && sortType.equalsIgnoreCase("DESC")) {
                        return job2.getTitle().compareToIgnoreCase(job1.getTitle());
                    } else {
                        return job1.getTitle().compareToIgnoreCase(job2.getTitle());
                    }
                }

                // TODO sort remaining fields and more fields to sort

                return job1.hashCode() - job2.hashCode();
            });
        }

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
