package ru.sfedu.samokat.model;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import java.util.Date;
import java.util.Objects;

@Root(name = "CommonProduct")
public class CommonProduct extends Product {
    @Attribute(name = "netWeight")
    @CsvBindByPosition(position = 6)
    private int netWeight;
    @Attribute(name = "type")
    @CsvBindByPosition(position = 7)
    private CommonProductType type;

    public CommonProduct() {
    }

    public CommonProduct(long id, String name, int cost, Date prodDate, Date expDate, int netWeight, CommonProductType type) {
        super(id, name, cost, prodDate, expDate, ProductType.COMMON_PRODUCT);
        this.netWeight = netWeight;
        this.type = type;
    }

    public int getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(int netWeight) {
        this.netWeight = netWeight;
    }

    public CommonProductType getType() {
        return type;
    }

    public void setType(CommonProductType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CommonProduct that = (CommonProduct) o;
        return netWeight == that.netWeight && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), netWeight, type);
    }

    @Override
    public String toString() {
        return "CommonProduct{" +
                "netWeight=" + netWeight +
                ", type=" + type +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", prodDate=" + prodDate +
                ", expDate=" + expDate +
                ", eType=" + eType +
                '}';
    }
}
