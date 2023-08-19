package io.github.battorydev.springboot.demo.util;

import io.github.battorydev.springboot.demo.model.JobJsonObject;
import io.github.battorydev.springboot.demo.util.converter.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Helper class to convert check and convert salary into comparable values.
 * Usage: SalaryConverter#convertSalary(JobJsonObject)
 */
public class SalaryConverter {

    private static final Logger LOGGER = LogManager.getLogger();
    private static List<Converter> mapper = new ArrayList<>();

    static {
        mapper.add(new NumberConverter());
        mapper.add(new CurrencySymbolConverter());
        mapper.add(new CurrencyByLocaleConverter());
        mapper.add(new CurrencyNameConverter());
    }

    private SalaryConverter() {
        // should not be instantiated
    }

    /**
     * Check and covert salary in JobJsonObject.
     *
     * @param val the JobJsonObject
     * @return the updated JobJsonObject
     */
    public static JobJsonObject convertSalary(JobJsonObject val) {
        Optional<Converter> converter = mapper.stream().filter(mapper -> mapper.accept(val)).findFirst();
        if (!converter.isPresent()) {
            LOGGER.error("Converter for salary value not found: {}", val.getSalary());
            return null;
        }

        JobJsonObject converted = converter.get().convert(val);
        if (converted == null) {
            LOGGER.info("Found converter but unable to convert value. Converter:{}, Value:{} ",
                    converter.get().getClass().getSimpleName(), val.getSalary());
        }
        return converted;

    }
}
