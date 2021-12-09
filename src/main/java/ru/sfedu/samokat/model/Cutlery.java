package ru.sfedu.samokat.model;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import java.util.Date;
import java.util.Objects;

@Root(name = "Cutlery")
public class Cutlery extends Product {
    @Attribute(name = "crossWeight")
    @CsvBindByPosition(position = 6)
    private int crossWeight;

    public Cutlery() {
    }

    public Cutlery(long id, String name, int cost, Date prodDate, Date expDate, int crossWeight) {
        super(id, name, cost, prodDate, expDate, ProductType.CUTLERY);
        this.crossWeight = crossWeight;
    }

    public int getCrossWeight() {
        return crossWeight;
    }

    public void setCrossWeight(int crossWeight) {
        this.crossWeight = crossWeight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Cutlery cutlery = (Cutlery) o;
        return crossWeight == cutlery.crossWeight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), crossWeight);
    }

    @Override
    public String toString() {
        return "Cutlery{" +
                "crossWeight=" + crossWeight +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", prodDate=" + prodDate +
                ", expDate=" + expDate +
                ", eType=" + eType +
                '}';
    }
}
