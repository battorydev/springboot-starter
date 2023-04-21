package io.github.battorydev.springboot.demo.util.converter;

import io.github.battorydev.springboot.demo.model.JobJsonObject;
import org.apache.logging.log4j.LogManager;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.regex.Pattern;

public class CurrencySymbolConverter implements Converter {

    @Override
    public boolean accept(JobJsonObject val) {
        String salary = val.getSalary();
        boolean matches = Pattern.matches("[€£$¥₹]+[0-9,.]+", salary);

        if (matches) {
            try {
                NumberFormat.getInstance().parse(val.getSalary().substring(1)).doubleValue();
                return true;
            } catch (ParseException e) {
                return false;
            }
        }

        return false;
    }

    @Override
    public JobJsonObject convert(JobJsonObject val) {

        try {
            String symbol = val.getSalary().substring(0,  1);
            double salary = NumberFormat.getInstance().parse(val.getSalary().substring(1)).doubleValue();
//            LogManager.getLogger().info("IncomingVal:{}, Output:{}", val.getSalary(), salary);
            val.setSalary(String.valueOf(salary));
            val.setSalaryCurrency(symbol);
            return val;
        } catch (ParseException e) {
            LogManager.getLogger().error(e.getMessage(),e);
            return null;
        }
    }
}
