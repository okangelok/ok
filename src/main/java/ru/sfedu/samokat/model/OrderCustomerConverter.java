package ru.sfedu.samokat.model;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import ru.sfedu.samokat.provider.DataProviderCSV;

public class OrderCustomerConverter extends AbstractBeanField<Customer, Long> {
    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        return new DataProviderCSV().getCustomerById(Long.parseLong(s)).orElseThrow(() -> {
            throw new UnsupportedOperationException();
        });
    }

    @Override
    protected String convertToWrite(Object value) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        return super.convertToWrite(((Customer) value).getId());
    }
}
