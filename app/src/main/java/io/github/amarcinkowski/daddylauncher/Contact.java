package io.github.amarcinkowski.daddylauncher;

/**
 * Created by amarcinkowski on 10.10.16.
 */
public class Contact implements Comparable<Contact> {

    String number;
    String name;
    Integer count;

    public Contact(String name, String number, int count) {
        this.name = name;
        this.number = number;
        this.count = count;
    }

    @Override
    public int compareTo(Contact o) {
        return name.compareTo(o.name);
    }

}
