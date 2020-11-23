package com.moamoa;

public class Menu {
    private String name;
    private String menu;
    private String price;

    public Menu(String name, String menu, String price) {
        this.name = name;
        this.menu = menu;
        this.price = price;
    }

    public String getMenu() {
        return menu;
    }

    public String getPrice() {
        return price;
    }

    public String getName() { return name; };
}
