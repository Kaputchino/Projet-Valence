package org.example;

public class PlaceHolderStringInt {
    public String string;
    public int anInt;

    public PlaceHolderStringInt(String string, int anInt) {
        this.string = string;
        this.anInt = anInt;
    }

    public PlaceHolderStringInt() {
    }

    @Override
    public String toString() {
        return "PlaceHolderStringInt{" +
                "string='" + string + '\'' +
                ", anInt=" + anInt +
                '}';
    }
}
