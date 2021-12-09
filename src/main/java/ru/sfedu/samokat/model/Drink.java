package ru.sfedu.samokat.model;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import java.util.Date;
import java.util.Objects;

@Root(name = "Drink")
public class Drink extends Product {
    @Attribute(name = "volume")
    @CsvBindByPosition(position = 6)
    private int volume;
    @Attribute(name = "type")
    @CsvBindByPosition(position = 7)
    private DrinkType type;

    public Drink() {
    }

    public Drink(long id, String name, int cost, Date prodDate, Date expDate, int volume, DrinkType type) {
        super(id, name, cost, prodDate, expDate, ProductType.DRINK);
        this.volume = volume;
        this.type = type;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public DrinkType getType() {
        return type;
    }

    public void setType(DrinkType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Drink drink = (Drink) o;
        return volume == drink.volume && type == drink.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), volume, type);
    }

    @Override
    public String toString() {
        return "Drink{" +
                "volume=" + volume +
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
