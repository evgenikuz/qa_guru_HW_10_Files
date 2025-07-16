package model;

public class Cat {
    private String name;
    private int age;
    private String[] hobbies;
    private boolean hasLongTail;
    private boolean hasLongFur;
    private int whiskersAmount;

    private CatColors colors;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String[] getHobbies() {
        return hobbies;
    }

    public void setHobbies(String[] hobbies) {
        this.hobbies = hobbies;
    }

    public boolean isHasLongTail() {
        return hasLongTail;
    }

    public void setHasLongTail(boolean hasLongTail) {
        this.hasLongTail = hasLongTail;
    }

    public boolean isHasLongFur() {
        return hasLongFur;
    }

    public void setHasLongFur(boolean hasLongFur) {
        this.hasLongFur = hasLongFur;
    }

    public int getWhiskersAmount() {
        return whiskersAmount;
    }

    public void setWhiskersAmount(int whiskersAmount) {
        this.whiskersAmount = whiskersAmount;
    }

    public CatColors getColors() {
        return colors;
    }

    public void setColors(CatColors colors) {
        this.colors = colors;
    }
}
