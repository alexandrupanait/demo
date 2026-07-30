package com.example.demo.catalog;

public class AttributeValueOption {

    private final String value;
    private final int count;
    private final boolean checked;

    public AttributeValueOption(String value, int count, boolean checked) {
        this.value = value;
        this.count = count;
        this.checked = checked;
    }

    public String getValue() {
        return value;
    }

    public int getCount() {
        return count;
    }

    public boolean isChecked() {
        return checked;
    }
}
