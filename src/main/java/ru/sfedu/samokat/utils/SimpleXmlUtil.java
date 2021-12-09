package ru.sfedu.samokat.utils;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "List")
public class SimpleXmlUtil<T> {
    @ElementList(inline = true, required = false)
    private List<T> list;

    public SimpleXmlUtil() {
    }

    public SimpleXmlUtil(List<T> list) {
        this.list = list;
    }

    public List<T> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
