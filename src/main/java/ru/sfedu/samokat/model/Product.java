package ru.sfedu.samokat.model;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import org.simpleframework.xml.Attribute;

import java.util.Date;
import java.util.Objects;

abstract public class Product {
    @Attribute(name = "id")
    @CsvBindByPosition(position = 0)
    protected long id;
    @Attribute(name = "name")
    @CsvBindByPosition(position = 1)
    protected String name;
    @Attribute(name = "cost")
    @CsvBindByPosition(position = 2)
    protected int cost;
    @Attribute(name = "prodDate")
    @CsvDate(value = "yyyy-MM-dd")
    @CsvBindByPosition(position = 3)
    protected Date prodDate;
    @Attribute(name = "expDate")
    @CsvDate(value = "yyyy-MM-dd")
    @CsvBindByPosition(position = 4)
    protected Date expDate;
    @Attribute(name = "eType")
    @CsvBindByPosition(position = 5)
    protected ProductType eType;

    public Product() {
    }

    public Product(long id, String name, int cost, Date prodDate, Date expDate, ProductType eType) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.prodDate = prodDate;
        this.expDate = expDate;
        this.eType = eType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public ProductType getEType() {
        return eType;
    }

    public void setEType(ProductType eType) {
        this.eType = eType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && cost == product.cost && Objects.equals(name, product.name) && Objects.equals(prodDate, product.prodDate) && Objects.equals(expDate, product.expDate) && eType == product.eType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cost, prodDate, expDate, eType);
    }
}
