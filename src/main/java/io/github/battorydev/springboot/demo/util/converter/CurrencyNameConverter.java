package io.github.battorydev.springboot.demo.util.converter;

import io.github.battorydev.springboot.demo.model.JobJsonObject;
import org.apache.logging.log4j.LogManager;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CurrencyNameConverter implements Converter {

    private Map<String, String> nameToSymbol = new HashMap<>();

    public CurrencyNameConverter() {
        // not allow instantiated
        nameToSymbol.put("AUD $", "$");
        nameToSymbol.put("au$", "$");
        nameToSymbol.put("CAD ", "$");
        nameToSymbol.put("CAD $", "$");
        nameToSymbol.put("CAN$", "$");
        nameToSymbol.put("CHF ", "$");
        nameToSymbol.put("CHF ", "$");
        nameToSymbol.put("DKK ", "Kr.");
        nameToSymbol.put("DKK ", "Kr.");
        nameToSymbol.put("EUR ", "€");
        nameToSymbol.put("HK$", "$");
        nameToSymbol.put("HUF ", "$");
        nameToSymbol.put("HUF ", "$");
        nameToSymbol.put("JPY ", "¥");
        nameToSymbol.put("INR ", "₹");
        nameToSymbol.put("NOK ", "$");
        nameToSymbol.put("NOK ", "$");
        nameToSymbol.put("NZ$", "$");
        nameToSymbol.put("PKR ", "$");
        nameToSymbol.put("PKR ", "$");
        nameToSymbol.put("PLN ", "$");
        nameToSymbol.put("PLN ", "$");
        nameToSymbol.put("R$", "$");
        nameToSymbol.put("R$ ", "$");
        nameToSymbol.put("Rs. ", "$");
        nameToSymbol.put("RMB ", "¥");
        nameToSymbol.put("RMB: ", "¥");
        nameToSymbol.put("SEK ", "kr");
        nameToSymbol.put("SEK ", "kr");
        nameToSymbol.put("SGD ", "$");
        nameToSymbol.put("SGD ", "$");
        nameToSymbol.put("USD ", "$");
        nameToSymbol.put("USD$", "$");
        nameToSymbol.put("USD", "$");
        nameToSymbol.put("ZAR ", "$");
    }

    @Override
    public boolean accept(JobJsonObject val) {
        String valSalary = val.getSalary();
        Optional<String> currencyName = nameToSymbol.keySet().stream().filter(
                currency -> valSalary != null && valSalary.length() >= currency.length() && currency.equals(
                        valSalary.substring(0, currency.length()))).findAny();

        if (currencyName.isEmpty()) {
            return false;
        }

        try {
            NumberFormat.getInstance().parse(valSalary.replace(currencyName.get(), "")).doubleValue();
            return true;
        } catch (ParseException e) {
            LogManager.getLogger().error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public JobJsonObject convert(JobJsonObject val) {
        String valSalary = val.getSalary();
        Optional<String> currencyName = nameToSymbol.keySet().stream().filter(
                currency -> valSalary != null && valSalary.length() >= currency.length() && currency.equals(
                        valSalary.substring(0, currency.length()))).findAny();

        if (currencyName.isEmpty()) {
            LogManager.getLogger().error("");
            return null;
        }

        try {
            double salary = NumberFormat.getInstance().parse(valSalary.replace(currencyName.get(), "")).doubleValue();
            val.setSalary(String.valueOf(salary));
            val.setSalaryCurrency(nameToSymbol.get(currencyName.get()));
            return val;
        } catch (ParseException e) {
            LogManager.getLogger().error(e.getMessage(), e);
            return null;
        }
    }
}
