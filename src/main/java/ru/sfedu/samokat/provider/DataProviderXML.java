package ru.sfedu.samokat.provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.samokat.model.*;
import ru.sfedu.samokat.utils.HistoryUtil;
import ru.sfedu.samokat.utils.SimpleXmlUtil;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.lang.Thread.currentThread;
import static ru.sfedu.samokat.Constant.*;
import static ru.sfedu.samokat.Constant.PROP_CUTLERY_XML;
import static ru.sfedu.samokat.model.ProductType.*;
import static ru.sfedu.samokat.model.Status.SUCCESS;
import static ru.sfedu.samokat.model.Status.UNSUCCESSFUL;
import static ru.sfedu.samokat.utils.ConfigurationUtil.getConfigurationEntry;
import static ru.sfedu.samokat.utils.HistoryUtil.saveToLog;

public class DataProviderXML implements IDataProvider {
    private static final Logger log = LogManager.getLogger(DataProviderXML.class);

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
        List<Order> objects = xmlToBean(PROP_ORDER_XML, method);
        if (objects.stream().anyMatch(o -> o.getId() == object.getId())
                || getCustomerById(object.getCustomer().getId()).isEmpty()
                || getProductByTypeAndId(object.getProductType(), object.getProduct().getId()).isEmpty()) {
            return new Result<Order>(object, UNSUCCESSFUL, null);
        }
        objects.add(object);
        if (beanToXml(objects, PROP_ORDER_XML, method) == UNSUCCESSFUL) {
            return new Result<>(object, UNSUCCESSFUL, null);
        }
        return new Result<>(object, SUCCESS, null);
    }

    @Override
    public Optional<Order> getOrderById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Order> objects = xmlToBean(PROP_ORDER_XML, method);
        return objects.stream().filter(o -> o.getId() == id).findFirst();
    }

    @Override
    public void deleteOrderById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Order> objects = xmlToBean(PROP_ORDER_XML, method);
        objects.removeIf(o -> o.getId() == id);
        beanToXml(objects, PROP_ORDER_XML, method);
    }

    private void deleteOrderByCustomerId(long customerId, String method) {
        List<Order> orderDependency = xmlToBean(PROP_ORDER_XML, method);
        orderDependency.removeIf(order -> order.getCustomer().getId() == customerId);
        beanToXml(orderDependency, PROP_ORDER_XML, method);
    }

    private void deleteOrderByProductId(ProductType type, long productId, String method) {
        List<Order> orderDependency = xmlToBean(PROP_ORDER_XML, method);
        orderDependency.removeIf(order -> order.getProduct().getId() == productId && order.getProductType() == type);
        beanToXml(orderDependency, PROP_ORDER_XML, method);
    }

    @Override
    public Result<Customer> saveCustomer(Customer object) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Customer> objects = xmlToBean(PROP_CUSTOMER_XML, method);
        if (objects.stream().anyMatch(o -> o.getId() == object.getId())) {
            return new Result<Customer>(object, UNSUCCESSFUL, format(ID_EXISTS, object.getId()));
        }
        objects.add(object);
        if (beanToXml(objects, PROP_CUSTOMER_XML, method) == UNSUCCESSFUL) {
            return new Result<Customer>(object, UNSUCCESSFUL, null);
        }
        return new Result<Customer>(object, SUCCESS, null);
    }

    @Override
    public Result<Customer> updateCustomer(Customer object) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Customer> objects = xmlToBean(PROP_CUSTOMER_XML, method);
        if (objects.stream().noneMatch(o -> o.getId() == object.getId())) {
            return new Result<Customer>(object, UNSUCCESSFUL, format(ID_NOT_EXISTS, object.getId()));
        }
        objects.removeIf(o -> o.getId() == object.getId());
        objects.add(object);
        if (beanToXml(objects, PROP_CUSTOMER_XML, method) == UNSUCCESSFUL) {
            return new Result<Customer>(object, UNSUCCESSFUL, null);
        }
        return new Result<Customer>(object, SUCCESS, null);
    }

    @Override
    public Optional<Customer> getCustomerById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Customer> objects = xmlToBean(PROP_CUSTOMER_XML, method);
        return objects.stream().filter(o -> o.getId() == id).findFirst();
    }

    @Override
    public void deleteCustomerById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        deleteOrderByCustomerId(id, method);
        List<Customer> objects = xmlToBean(PROP_CUSTOMER_XML, method);
        objects.removeIf(o -> o.getId() == id);
        beanToXml(objects, PROP_CUSTOMER_XML, method);
    }

    @Override
    public Result<CommonProduct> saveCommonProduct(CommonProduct object) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<CommonProduct> objects = xmlToBean(PROP_C_PRODUCT_XML, method);
        if (objects.stream().anyMatch(o -> o.getId() == object.getId())) {
            return new Result<CommonProduct>(object, UNSUCCESSFUL, format(ID_EXISTS, object.getId()));
        }
        objects.add(object);
        if (beanToXml(objects, PROP_C_PRODUCT_XML, method) == UNSUCCESSFUL) {
            return new Result<CommonProduct>(object, UNSUCCESSFUL, null);
        }
        return new Result<CommonProduct>(object, SUCCESS, null);
    }

    @Override
    public Result<CommonProduct> updateCommonProduct(CommonProduct object) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<CommonProduct> objects = xmlToBean(PROP_C_PRODUCT_XML, method);
        if (objects.stream().noneMatch(o -> o.getId() == object.getId())) {
            return new Result<CommonProduct>(object, UNSUCCESSFUL, format(ID_NOT_EXISTS, object.getId()));
        }
        objects.removeIf(o -> o.getId() == object.getId());
        objects.add(object);
        if (beanToXml(objects, PROP_C_PRODUCT_XML, method) == UNSUCCESSFUL) {
            return new Result<CommonProduct>(object, UNSUCCESSFUL, null);
        }
        return new Result<CommonProduct>(object, SUCCESS, null);
    }

    @Override
    public Optional<CommonProduct> getCommonProductById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<CommonProduct> objects = xmlToBean(PROP_C_PRODUCT_XML, method);
        return objects.stream().filter(o -> o.getId() == id).findFirst();
    }

    @Override
    public void deleteCommonProductById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        deleteOrderByProductId(COMMON_PRODUCT, id, method);
        List<CommonProduct> objects = xmlToBean(PROP_C_PRODUCT_XML, method);
        objects.removeIf(o -> o.getId() == id);
        beanToXml(objects, PROP_C_PRODUCT_XML, method);
    }

    @Override
    public Result<Drink> saveDrink(Drink object) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Drink> objects = xmlToBean(PROP_DRINK_XML, method);
        if (objects.stream().anyMatch(o -> o.getId() == object.getId())) {
            return new Result<Drink>(object, UNSUCCESSFUL, format(ID_EXISTS, object.getId()));
        }
        objects.add(object);
        if (beanToXml(objects, PROP_DRINK_XML, method) == UNSUCCESSFUL) {
            return new Result<Drink>(object, UNSUCCESSFUL, null);
        }
        return new Result<Drink>(object, SUCCESS, null);
    }

    @Override
    public Result<Drink> updateDrink(Drink object) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Drink> objects = xmlToBean(PROP_DRINK_XML, method);
        if (objects.stream().noneMatch(o -> o.getId() == object.getId())) {
            return new Result<Drink>(object, UNSUCCESSFUL, format(ID_NOT_EXISTS, object.getId()));
        }
        objects.removeIf(o -> o.getId() == object.getId());
        objects.add(object);
        if (beanToXml(objects, PROP_DRINK_XML, method) == UNSUCCESSFUL) {
            return new Result<Drink>(object, UNSUCCESSFUL, null);
        }
        return new Result<Drink>(object, SUCCESS, null);
    }

    @Override
    public Optional<Drink> getDrinkById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Drink> objects = xmlToBean(PROP_DRINK_XML, method);
        return objects.stream().filter(o -> o.getId() == id).findFirst();
    }

    @Override
    public void deleteDrinkById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        deleteOrderByProductId(DRINK, id, method);
        List<Drink> objects = xmlToBean(PROP_DRINK_XML, method);
        objects.removeIf(o -> o.getId() == id);
        beanToXml(objects, PROP_DRINK_XML, method);
    }

    @Override
    public Result<Cutlery> saveCutlery(Cutlery object) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Cutlery> objects = xmlToBean(PROP_CUTLERY_XML, method);
        if (objects.stream().anyMatch(o -> o.getId() == object.getId())) {
            return new Result<Cutlery>(object, UNSUCCESSFUL, format(ID_EXISTS, object.getId()));
        }
        objects.add(object);
        if (beanToXml(objects, PROP_CUTLERY_XML, method) == UNSUCCESSFUL) {
            return new Result<Cutlery>(object, UNSUCCESSFUL, null);
        }
        return new Result<Cutlery>(object, SUCCESS, null);
    }

    @Override
    public Result<Cutlery> updateCutlery(Cutlery object) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Cutlery> objects = xmlToBean(PROP_CUTLERY_XML, method);
        if (objects.stream().noneMatch(o -> o.getId() == object.getId())) {
            return new Result<Cutlery>(object, UNSUCCESSFUL, format(ID_NOT_EXISTS, object.getId()));
        }
        objects.removeIf(o -> o.getId() == object.getId());
        objects.add(object);
        if (beanToXml(objects, PROP_CUTLERY_XML, method) == UNSUCCESSFUL) {
            return new Result<Cutlery>(object, UNSUCCESSFUL, null);
        }
        return new Result<Cutlery>(object, SUCCESS, null);
    }

    @Override
    public Optional<Cutlery> getCutleryById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        List<Cutlery> objects = xmlToBean(PROP_CUTLERY_XML, method);
        return objects.stream().filter(o -> o.getId() == id).findFirst();
    }

    @Override
    public void deleteCutleryById(long id) {
        final String method = currentThread().getStackTrace()[1].getMethodName();
        deleteOrderByProductId(CUTLERY, id, method);
        List<Cutlery> objects = xmlToBean(PROP_CUTLERY_XML, method);
        objects.removeIf(o -> o.getId() == id);
        beanToXml(objects, PROP_CUTLERY_XML, method);
    }

    private <T> Status beanToXml(List<T> ts, String key, String method) {
        Status status;
        try {
            FileWriter fileWriter = new FileWriter(getConfigurationEntry(key));
            Serializer serializer = new Persister();
            SimpleXmlUtil<T> container = new SimpleXmlUtil<T>(ts);
            serializer.write(container, fileWriter);
            fileWriter.close();
            status = SUCCESS;
        } catch (Exception exception) {
            log.error(exception);
            status = UNSUCCESSFUL;
        }
        saveToLog(createHistoryContent(method, ts, status));
        return status;
    }

    private <T> List<T> xmlToBean(String key, String method) {
        try {
            FileReader reader = new FileReader(getConfigurationEntry(key));
            Serializer serializer = new Persister();
            SimpleXmlUtil<T> container = serializer.read(SimpleXmlUtil.class, reader);
            final List<T> querySet = container.getList();
            saveToLog(createHistoryContent(method, querySet, SUCCESS));
            reader.close();
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
