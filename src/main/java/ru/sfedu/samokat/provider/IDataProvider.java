package ru.sfedu.samokat.provider;

import ru.sfedu.samokat.model.*;

import java.util.Optional;

public interface IDataProvider {
    Result<Customer> getDiscount(long customerId);
    Result<Order> saveOrder(Order object);
    Optional<Order> getOrderById(long id);
    void deleteOrderById(long id);

    Result<Customer> saveCustomer(Customer object);
    Result<Customer> updateCustomer(Customer object);
    Optional<Customer> getCustomerById(long id);
    void deleteCustomerById(long id);

    Result<CommonProduct> saveCommonProduct(CommonProduct object);
    Result<CommonProduct> updateCommonProduct(CommonProduct object);
    Optional<CommonProduct> getCommonProductById(long id);
    void deleteCommonProductById(long id);

    Result<Drink> saveDrink(Drink object);
    Result<Drink> updateDrink(Drink object);
    Optional<Drink> getDrinkById(long id);
    void deleteDrinkById(long id);

    Result<Cutlery> saveCutlery(Cutlery object);
    Result<Cutlery> updateCutlery(Cutlery object);
    Optional<Cutlery> getCutleryById(long id);
    void deleteCutleryById(long id);

    default Optional<? extends Product> getProductByTypeAndId(ProductType type, long id) {
        switch (type) {
            case DRINK:
                return getDrinkById(id);
            case CUTLERY:
                return getCutleryById(id);
            case COMMON_PRODUCT:
                return getCommonProductById(id);
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }
}
