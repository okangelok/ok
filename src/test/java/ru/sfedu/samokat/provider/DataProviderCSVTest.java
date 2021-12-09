package ru.sfedu.samokat.provider;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sfedu.samokat.TestBase;
import ru.sfedu.samokat.model.Cutlery;
import ru.sfedu.samokat.model.Drink;
import ru.sfedu.samokat.model.Status;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class DataProviderCSVTest extends TestBase {

    DataProviderCSV provider = new DataProviderCSV();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testOrder() {
        provider.saveCustomer(getOrder1().getCustomer());
        provider.saveDrink((Drink) getOrder1().getProduct());
        provider.saveCutlery((Cutlery) getOrder2().getProduct());

        assertEquals(provider.saveOrder(getOrder1()).getStatus(), Status.SUCCESS);
        assertEquals(provider.saveOrder(getOrder2()).getStatus(), Status.SUCCESS);
        provider.deleteOrderById(getOrder2().getId());
        assertTrue(provider.getOrderById(getOrder2().getId()).isEmpty());
        provider.deleteOrderById(getOrder1().getId());
        assertTrue(provider.getOrderById(getOrder1().getId()).isEmpty());

        provider.deleteCustomerById(getOrder1().getCustomer().getId());
        provider.deleteDrinkById(getOrder1().getProduct().getId());
        provider.deleteCutleryById(getOrder2().getProduct().getId());
    }

    @Test
    void testCustomer() {
        assertTrue(provider.getCustomerById(getCustomer1().getId()).isEmpty());
        assertEquals(provider.saveCustomer(getCustomer1()).getStatus(), Status.SUCCESS);
        assertEquals(provider.saveCustomer(getCustomer1()).getStatus(), Status.UNSUCCESSFUL);
        assertEquals(provider.updateCustomer(getCustomer1()).getStatus(), Status.SUCCESS);
        assertTrue(provider.getCustomerById(getCustomer1().getId()).isPresent());
        assertEquals(provider.updateCustomer(getCustomer2()).getStatus(), Status.UNSUCCESSFUL);
        provider.deleteCustomerById(getCustomer1().getId());
        assertTrue(provider.getCustomerById(getCustomer1().getId()).isEmpty());
    }

    @Test
    void testCommonProduct() {
        assertTrue(provider.getCommonProductById(getCommonProduct1().getId()).isEmpty());
        assertEquals(provider.saveCommonProduct(getCommonProduct1()).getStatus(), Status.SUCCESS);
        assertEquals(provider.saveCommonProduct(getCommonProduct1()).getStatus(), Status.UNSUCCESSFUL);
        assertEquals(provider.updateCommonProduct(getCommonProduct1()).getStatus(), Status.SUCCESS);
        assertTrue(provider.getCommonProductById(getCommonProduct1().getId()).isPresent());
        assertEquals(provider.updateCommonProduct(getCommonProduct2()).getStatus(), Status.UNSUCCESSFUL);
        provider.deleteCommonProductById(getCommonProduct1().getId());
        assertTrue(provider.getCommonProductById(getCommonProduct1().getId()).isEmpty());
    }

    @Test
    void testDrink() {
        assertTrue(provider.getDrinkById(getDrink1().getId()).isEmpty());
        assertEquals(provider.saveDrink(getDrink1()).getStatus(), Status.SUCCESS);
        assertEquals(provider.saveDrink(getDrink1()).getStatus(), Status.UNSUCCESSFUL);
        assertEquals(provider.updateDrink(getDrink1()).getStatus(), Status.SUCCESS);
        assertTrue(provider.getDrinkById(getDrink1().getId()).isPresent());
        assertEquals(provider.updateDrink(getDrink2()).getStatus(), Status.UNSUCCESSFUL);
        provider.deleteDrinkById(getDrink1().getId());
        assertTrue(provider.getDrinkById(getDrink1().getId()).isEmpty());
    }

    @Test
    void testCutlery() {
        assertTrue(provider.getCutleryById(getCutlery1().getId()).isEmpty());
        assertEquals(provider.saveCutlery(getCutlery1()).getStatus(), Status.SUCCESS);
        assertEquals(provider.saveCutlery(getCutlery1()).getStatus(), Status.UNSUCCESSFUL);
        assertEquals(provider.updateCutlery(getCutlery1()).getStatus(), Status.SUCCESS);
        assertTrue(provider.getCutleryById(getCutlery1().getId()).isPresent());
        assertEquals(provider.updateCutlery(getCutlery2()).getStatus(), Status.UNSUCCESSFUL);
        provider.deleteCutleryById(getCutlery1().getId());
        assertTrue(provider.getCutleryById(getCutlery1().getId()).isEmpty());
    }

}