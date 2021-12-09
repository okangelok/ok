package ru.sfedu.samokat.model;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import ru.sfedu.samokat.provider.DataProviderCSV;

import static java.lang.Long.parseLong;
import static java.lang.String.format;
import static ru.sfedu.samokat.model.ProductType.valueOf;

public class OrderProductConverter extends AbstractBeanField<Product, String> {

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        final String[] s1 = s.split(":");
        return new DataProviderCSV().getProductByTypeAndId(valueOf(s1[0]), parseLong(s1[1])).orElseThrow(() -> {
            throw new UnsupportedOperationException();
        });
    }

    @Override
    protected String convertToWrite(Object value) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        return super.convertToWrite(format("%s:%d", ((Product) value).getEType().name(), ((Product) value).getId()));
    }

}
