package ru.sfedu.samokat.model;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import java.util.Objects;

@Root(name = "Customer")
public class Customer {
    @Attribute(name = "id")
    @CsvBindByPosition(position = 0)
    private long id;
    @Attribute(name = "address")
    @CsvBindByPosition(position = 1)
    private String address;
    @Attribute(name = "phone")
    @CsvBindByPosition(position = 2)
    private String phone;
    @Attribute(name = "isCard")
    @CsvBindByPosition(position = 3)
    private boolean isCard;

    public Customer() {
    }

    public Customer(long id, String address, String phone, boolean isCard) {
        this.id = id;
        this.address = address;
        this.phone = phone;
        this.isCard = isCard;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isCard() {
        return isCard;
    }

    public void setCard(boolean card) {
        isCard = card;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id && isCard == customer.isCard && Objects.equals(address, customer.address) && Objects.equals(phone, customer.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, phone, isCard);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", isCard=" + isCard +
                '}';
    }
}
