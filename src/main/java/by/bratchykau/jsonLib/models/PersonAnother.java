package by.bratchykau.jsonLib.models;

import java.util.Arrays;
import java.util.List;

public class PersonAnother {
    private String name;
    private int age;
    private boolean isMarried;
    private List<String> phones;

    public PersonAnother(String name, int age, boolean isMarried, List<String> phones) {
        this.name = name;
        this.age = age;
        this.isMarried = isMarried;
        this.phones = phones;
    }

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

    public boolean isMarried() {
        return isMarried;
    }

    public void setMarried(boolean married) {
        isMarried = married;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "PersonAnother{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", isMarried=" + isMarried +
                ", phones=" + phones +
                '}';
    }
}
