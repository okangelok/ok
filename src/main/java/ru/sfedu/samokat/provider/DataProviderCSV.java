package ru.sfedu.samokat.provider;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.samokat.model.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

import static java.lang.String.format;
import static java.lang.Thread.currentThread;
import static ru.sfedu.samokat.Constant.*;
import static ru.sfedu.samokat.model.ProductType.*;
import static ru.sfedu.samokat.model.Status.SUCCESS;
import static ru.sfedu.samokat.model.Status.UNSUCCESSFUL;
import static ru.sfedu.samokat.utils.ConfigurationUtil.getConfigurationEntry;
import static ru.sfedu.samokat.utils.HistoryUtil.saveToLog;

public class DataProviderCSV implements IDataProvider {
    private static final Logger log = LogManager.getLogger(DataProviderCSV.class);

    @Override
    public Result<Customer> getDiscount(long customerId) {
        Optional<Customer> optionalCustomer = getCustomerById(customerId);
        if (optionalCustomer.isEmpty()) {
            return new Result<>(null, UNSUCCESSFUL, null);
        }
        Customer customer = optionalCustomer.get();
        customer.setCard(true);
        return updateCustomer(customer);
    }

    @Override
    public Result<Order> saveOrder(Order object) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Order> objects = csvToBean(Order.class, PROP_ORDER_CSV, method);
        if (objects.stream().anyMatch(o -> o.getId() == object.getId())
                || getCustomerById(object.getCustomer().getId()).isEmpty()
                || getProductByTypeAndId(object.getProductType(), object.getProduct().getId()).isEmpty()) {
            return new Result<>(object, UNSUCCESSFUL, null);
        }
        objects.add(object);
        if (beanToCsv(objects, PROP_ORDER_CSV, method) == UNSUCCESSFUL) {
            return new Result<>(object, UNSUCCESSFUL, null);
        }
        return new Result<>(object, SUCCESS, null);
    }

    @Override
    public Optional<Order> getOrderById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Order> objects = csvToBean(Order.class, PROP_ORDER_CSV, method);
        return objects.stream().filter(o -> o.getId() == id).findFirst();
    }

    @Override
    public void deleteOrderById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Order> objects = csvToBean(Order.class, PROP_ORDER_CSV, method);
        objects.removeIf(o -> o.getId() == id);
        beanToCsv(objects, PROP_ORDER_CSV, method);
    }

    private void deleteOrderByCustomerId(long customerId, String method) {
        List<Order> orderDependency = csvToBean(Order.class, PROP_ORDER_CSV, method);
        orderDependency.removeIf(order -> order.getCustomer().getId() == customerId);
        beanToCsv(orderDependency, PROP_ORDER_CSV, method);
    }

    private void deleteOrderByProductId(ProductType type, long productId, String method) {
        List<Order> orderDependency = csvToBean(Order.class, PROP_ORDER_CSV, method);
        orderDependency.removeIf(order -> order.getProduct().getId() == productId && order.getProductType() == type);
        beanToCsv(orderDependency, PROP_ORDER_CSV, method);
    }

    @Override
    public Result<Customer> saveCustomer(Customer object) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Customer> objects = csvToBean(Customer.class, PROP_CUSTOMER_CSV, method);
        if (objects.stream().anyMatch(o -> o.getId() == object.getId())) {
            return new Result<>(object, UNSUCCESSFUL, format(ID_EXISTS, object.getId()));
        }
        objects.add(object);
        if (beanToCsv(objects, PROP_CUSTOMER_CSV, method) == UNSUCCESSFUL) {
            return new Result<>(object, UNSUCCESSFUL, null);
        }
        return new Result<>(object, SUCCESS, null);
    }

    @Override
    public Result<Customer> updateCustomer(Customer object) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Customer> objects = csvToBean(Customer.class, PROP_CUSTOMER_CSV, method);
        if (objects.stream().noneMatch(o -> o.getId() == object.getId())) {
            return new Result<>(object, UNSUCCESSFUL, format(ID_NOT_EXISTS, object.getId()));
        }
        objects.removeIf(o -> o.getId() == object.getId());
        objects.add(object);
        if (beanToCsv(objects, PROP_CUSTOMER_CSV, method) == UNSUCCESSFUL) {
            return new Result<>(object, UNSUCCESSFUL, null);
        }
        return new Result<>(object, SUCCESS, null);
    }

    @Override
    public Optional<Customer> getCustomerById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Customer> objects = csvToBean(Customer.class, PROP_CUSTOMER_CSV, method);
        return objects.stream().filter(o -> o.getId() == id).findFirst();
    }

    @Override
    public void deleteCustomerById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        deleteOrderByCustomerId(id, method);
        List<Customer> objects = csvToBean(Customer.class, PROP_CUSTOMER_CSV, method);
        objects.removeIf(o -> o.getId() == id);
        beanToCsv(objects, PROP_CUSTOMER_CSV, method);
    }

    @Override
    public Result<CommonProduct> saveCommonProduct(CommonProduct object) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<CommonProduct> objects = csvToBean(CommonProduct.class, PROP_C_PRODUCT_CSV, method);
        if (objects.stream().anyMatch(o -> o.getId() == object.getId())) {
            return new Result<>(object, UNSUCCESSFUL, format(ID_EXISTS, object.getId()));
        }
        objects.add(object);
        if (beanToCsv(objects, PROP_C_PRODUCT_CSV, method) == UNSUCCESSFUL) {
            return new Result<>(object, UNSUCCESSFUL, null);
        }
        return new Result<>(object, SUCCESS, null);
    }

    @Override
    public Result<CommonProduct> updateCommonProduct(CommonProduct object) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<CommonProduct> objects = csvToBean(CommonProduct.class, PROP_C_PRODUCT_CSV, method);
        if (objects.stream().noneMatch(o -> o.getId() == object.getId())) {
            return new Result<>(object, UNSUCCESSFUL, format(ID_NOT_EXISTS, object.getId()));
        }
        objects.removeIf(o -> o.getId() == object.getId());
        objects.add(object);
        if (beanToCsv(objects, PROP_C_PRODUCT_CSV, method) == UNSUCCESSFUL) {
            return new Result<>(object, UNSUCCESSFUL, null);
        }
        return new Result<>(object, SUCCESS, null);
    }

    @Override
    public Optional<CommonProduct> getCommonProductById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<CommonProduct> objects = csvToBean(CommonProduct.class, PROP_C_PRODUCT_CSV, method);
        return objects.stream().filter(o -> o.getId() == id).findFirst();
    }

    @Override
    public void deleteCommonProductById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        deleteOrderByProductId(COMMON_PRODUCT, id, method);
        List<CommonProduct> objects = csvToBean(CommonProduct.class, PROP_C_PRODUCT_CSV, method);
        objects.removeIf(o -> o.getId() == id);
        beanToCsv(objects, PROP_C_PRODUCT_CSV, method);
    }

    @Override
    public Result<Drink> saveDrink(Drink object) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Drink> objects = csvToBean(Drink.class, PROP_DRINK_CSV, method);
        if (objects.stream().anyMatch(o -> o.getId() == object.getId())) {
            return new Result<>(object, UNSUCCESSFUL, format(ID_EXISTS, object.getId()));
        }
        objects.add(object);
        if (beanToCsv(objects, PROP_DRINK_CSV, method) == UNSUCCESSFUL) {
            return new Result<>(object, UNSUCCESSFUL, null);
        }
        return new Result<>(object, SUCCESS, null);
    }

    @Override
    public Result<Drink> updateDrink(Drink object) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Drink> objects = csvToBean(Drink.class, PROP_DRINK_CSV, method);
        if (objects.stream().noneMatch(o -> o.getId() == object.getId())) {
            return new Result<>(object, UNSUCCESSFUL, format(ID_NOT_EXISTS, object.getId()));
        }
        objects.removeIf(o -> o.getId() == object.getId());
        objects.add(object);
        if (beanToCsv(objects, PROP_DRINK_CSV, method) == UNSUCCESSFUL) {
            return new Result<>(object, UNSUCCESSFUL, null);
        }
        return new Result<>(object, SUCCESS, null);
    }

    @Override
    public Optional<Drink> getDrinkById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Drink> objects = csvToBean(Drink.class, PROP_DRINK_CSV, method);
        return objects.stream().filter(o -> o.getId() == id).findFirst();
    }

    @Override
    public void deleteDrinkById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        deleteOrderByProductId(DRINK, id, method);
        List<Drink> objects = csvToBean(Drink.class, PROP_DRINK_CSV, method);
        objects.removeIf(o -> o.getId() == id);
        beanToCsv(objects, PROP_DRINK_CSV, method);
    }

    @Override
    public Result<Cutlery> saveCutlery(Cutlery object) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Cutlery> objects = csvToBean(Cutlery.class, PROP_CUTLERY_CSV, method);
        if (objects.stream().anyMatch(o -> o.getId() == object.getId())) {
            return new Result<>(object, UNSUCCESSFUL, format(ID_EXISTS, object.getId()));
        }
        objects.add(object);
        if (beanToCsv(objects, PROP_CUTLERY_CSV, method) == UNSUCCESSFUL) {
            return new Result<>(object, UNSUCCESSFUL, null);
        }
        return new Result<>(object, SUCCESS, null);
    }

    @Override
    public Result<Cutlery> updateCutlery(Cutlery object) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Cutlery> objects = csvToBean(Cutlery.class, PROP_CUTLERY_CSV, method);
        if (objects.stream().noneMatch(o -> o.getId() == object.getId())) {
            return new Result<>(object, UNSUCCESSFUL, format(ID_NOT_EXISTS, object.getId()));
        }
        objects.removeIf(o -> o.getId() == object.getId());
        objects.add(object);
        if (beanToCsv(objects, PROP_CUTLERY_CSV, method) == UNSUCCESSFUL) {
            return new Result<>(object, UNSUCCESSFUL, null);
        }
        return new Result<>(object, SUCCESS, null);
    }

    @Override
    public Optional<Cutlery> getCutleryById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Cutlery> objects = csvToBean(Cutlery.class, PROP_CUTLERY_CSV, method);
        return objects.stream().filter(o -> o.getId() == id).findFirst();
    }

    @Override
    public void deleteCutleryById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        deleteOrderByProductId(CUTLERY, id, method);
        List<Cutlery> objects = csvToBean(Cutlery.class, PROP_CUTLERY_CSV, method);
        objects.removeIf(o -> o.getId() == id);
        beanToCsv(objects, PROP_CUTLERY_CSV, method);
    }

    private <T> Status beanToCsv(List<T> ts, String key, String method) {
        Status status;
        try {
            FileWriter fileWriter = new FileWriter(getConfigurationEntry(key), false);
            CSVWriter csvWriter = new CSVWriter(fileWriter);
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(csvWriter).build();
            beanToCsv.write(ts);
            csvWriter.close();
            fileWriter.close();
            status = SUCCESS;
        } catch (Exception exception) {
            log.error(exception);
            status = UNSUCCESSFUL;
        }
        saveToLog(createHistoryContent(method, ts, status));
        return status;
    }

    private <T> List<T> csvToBean(Class<T> cls, String key, String method) {
        try {
            CSVReader csvReader = new CSVReader(new FileReader(getConfigurationEntry(key)));
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(csvReader).withType(cls).build();
            List<T> querySet = csvToBean.parse();
            csvReader.close();
            saveToLog(createHistoryContent(method, querySet, SUCCESS));
            return querySet;
        } catch (Exception exception) {
            log.error(exception);
        }
        saveToLog(createHistoryContent(method, null, UNSUCCESSFUL));
        return new ArrayList<>();
    }

    private HistoryContent createHistoryContent(String method, Object object, Status status) {
        return new HistoryContent(this.getClass().getSimpleName(), new Date(), DEFAULT_ACTOR, method, object, status);
    }
}
