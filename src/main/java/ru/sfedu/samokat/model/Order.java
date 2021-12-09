package ru.sfedu.samokat.model;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.Objects;

@Root(name = "Order")
public class Order {
    @Attribute(name = "id")
    @CsvBindByPosition(position = 0)
    private long id;
    @Element(name = "product")
    @CsvCustomBindByPosition(position = 1, converter = OrderProductConverter.class)
    private Product product;
    @Element(name = "customer")
    @CsvCustomBindByPosition(position = 2, converter = OrderCustomerConverter.class)
    private Customer customer;
    @Element(name = "productType")
    @CsvBindByPosition(position = 3)
    private ProductType productType;

    public Order() {
    }

    public Order(long id, Product product, Customer customer) {
        this.id = id;
        this.product = product;
        this.customer = customer;
        this.productType = product.getEType();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && Objects.equals(product, order.product) && Objects.equals(customer, order.customer) && productType == order.productType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, customer, productType);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", product=" + product +
                ", customer=" + customer +
                ", productType=" + productType +
                '}';
    }
}
