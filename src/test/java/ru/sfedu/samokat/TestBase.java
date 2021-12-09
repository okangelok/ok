package ru.sfedu.samokat;

import ru.sfedu.samokat.model.*;

import java.util.Date;

import static ru.sfedu.samokat.model.CommonProductType.SNAKE;

abstract public class TestBase {
    protected static Order getOrder1() {
        return new Order(1L, getDrink1(), getCustomer1());
    }
    protected static Order getOrder2() {
        return new Order(2L, getCutlery1(), getCustomer1());
    }
    protected static Customer getCustomer1() {
        return new Customer(1L, "address1", "+1000000", true);
    }
    protected static Customer getCustomer2() {
        return new Customer(2L, "address2", "+2000000", false);
    }
    protected static CommonProduct getCommonProduct1() {
        return new CommonProduct(1L, "Mars", 200, new Date(), new Date(), 12, SNAKE);
    }
    protected static CommonProduct getCommonProduct2() {
        return new CommonProduct(2L, "Snickers", 200, new Date(), new Date(), 12, SNAKE);
    }
    protected static Drink getDrink1() {
        return new Drink(1L, "RedBull", 200, new Date(), new Date(), 11, DrinkType.ENERGY);
    }
    protected static Drink getDrink2() {
        return new Drink(2L, "Adrenaline", 200, new Date(), new Date(), 11, DrinkType.ENERGY);
    }
    protected static Cutlery getCutlery1() {
        return new Cutlery(1L, "Vilka", 200, new Date(), new Date(), 11);
    }
    protected static Cutlery getCutlery2() {
        return new Cutlery(2L, "Tarelka", 200, new Date(), new Date(), 11);
    }
}
