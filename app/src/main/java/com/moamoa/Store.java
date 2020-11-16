package com.moamoa;

public class Store {
    private String name;
    private String address;
    private String telephone;

    public Store(String name, String address, String telephone) {
        this.name = name;
        this.address = address;
        this.telephone = telephone;
    }

    public String getName() {
        return name;
    }

    public void setName() {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress() {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone() {
        this.telephone = telephone;
    }
}
