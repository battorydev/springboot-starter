package io.github.battorydev.springboot.demo.util.converter;

import io.github.battorydev.springboot.demo.model.JobJsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.Locale;

/**
 * The converter class for SalaryConverter.
 * Uses Double.parseDouble() to converts number directly.
 */
public class NumberConverter implements Converter {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public boolean accept(JobJsonObject val) {
        try {
            double salary = NumberFormat.getInstance().parse(val.getSalary()).doubleValue();
            return true;
        } catch (ParseException e) {
            LOGGER.warn(e.getMessage());
            return false;
        }
    }

    @Override
    public JobJsonObject convert(JobJsonObject val) {
        try {
            double salary = NumberFormat.getInstance().parse(val.getSalary()).doubleValue();
            val.setSalary(String.valueOf(salary));
            val.setSalaryCurrency(Currency.getInstance(Locale.getDefault()).getSymbol());
            return val;
        } catch (ParseException e) {
            LOGGER.warn(e.getMessage());
            return null;
        }
    }
}
