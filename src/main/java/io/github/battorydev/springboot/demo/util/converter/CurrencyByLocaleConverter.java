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
 * Uses NumberFormat.getCurrencyInstance(locale) to convert salary value.
 */
public class CurrencyByLocaleConverter implements Converter {

    private static final Logger LOGGER = LogManager.getLogger();
    private Locale[] supportLocales = new Locale[]{Locale.CANADA, Locale.CANADA_FRENCH, Locale.US, Locale.FRENCH, Locale.UK, Locale.GERMANY, Locale.ITALIAN, Locale.JAPANESE};

    @Override
    public boolean accept(JobJsonObject val) {
        for (Locale locale : supportLocales) {
            try {
                double salary = NumberFormat.getCurrencyInstance(locale).parse(val.getSalary()).doubleValue();
                return true;
            } catch (ParseException e) {
                continue;
            }
        }
        return false;
    }

    @Override
    public JobJsonObject convert(JobJsonObject val) {
        for (Locale locale : supportLocales) {
            try {
                double salary = NumberFormat.getCurrencyInstance(locale).parse(val.getSalary()).doubleValue();
                val.setSalary(String.valueOf(salary));
                val.setSalaryCurrency(Currency.getInstance(locale).getSymbol());
                return val;
            } catch (ParseException e) {
                continue;
            }
        }
        LOGGER.warn("Unable to parse value: {}", val.getSalaryCurrency());
        return null;
    }
}
