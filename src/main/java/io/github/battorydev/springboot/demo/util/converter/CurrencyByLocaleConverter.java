package io.github.battorydev.springboot.demo.util.converter;

import io.github.battorydev.springboot.demo.model.JobJsonObject;
import org.apache.logging.log4j.LogManager;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.Locale;

public class CurrencyByLocaleConverter implements Converter {

    private Locale[] supportLocales = new Locale[]{Locale.CANADA, Locale.CANADA_FRENCH, Locale.US, Locale.FRENCH, Locale.UK, Locale.GERMANY, Locale.ITALIAN, Locale.JAPANESE};

    @Override
    public boolean accept(JobJsonObject val) {
        for (Locale locale : supportLocales) {
            try {
                double salary = NumberFormat.getCurrencyInstance(locale).parse(val.getSalary()).doubleValue();
                //LogManager.getLogger().info("Accept: {}, Converted to:{}", val.getSalary(), salary);
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
        LogManager.getLogger().error("Unable to parse value: {}", val.getSalaryCurrency());
        return null;
    }
}
