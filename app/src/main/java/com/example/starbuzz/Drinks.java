package com.example.starbuzz;

public class Drinks {

    private String name;
    private String description;
    private int imageResourceId;

    public static final Drinks[] drinks = {
      new Drinks("Latte", "Двойной эспрессо с молоком", R.drawable.latte),
      new Drinks("Cappucino", "Горячий эспрессо со взбитым молоком", R.drawable.cappucino),
      new Drinks("Filter", "Чистый фильтрованный молотый кофе. Ничего лишнего", R.drawable.filter)
    };

    private Drinks(String name, String description, int imageResourceId){
        this.name = name;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    public int getImageResourceId(){
        return imageResourceId;
    }

    public String getDescription(){
        return description;
    }

    public String getName(){
        return name;
    }

    //вот тут я прекола не понял
    public String toString(){
        return this.name;
    }
}
