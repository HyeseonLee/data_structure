package cse2010.homework4;

public class check {
    public static void main(String[] args) {
        String make = "Top Spot";
        System.out.print(make.charAt(2));
        System.out.print(make.charAt(3));
        System.out.println(make.charAt(4));
        System.out.println(isAlpha(make.charAt(3)));



    }
    private static boolean isAlpha(final char ch) {
        if (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z')
            return true;
        else
            return false;
    }

}
