package cse2010.homework4;

/*
 * CSE2010 Homework #4
 * Problem 1: Palindrome
 *
 * Complete the code below.
 *
 */

public class Palindrome {

    public static boolean isPalindrome(String target) {

        return isPalindrome(target, 0, target.length() - 1);
    }

    private static boolean isPalindrome(String as, int start, int end) {

        // your code goes here ...
        while(start<end){

            if(toLower(as.charAt(start)) != toLower(as.charAt(end))) {

                if (isAlpha(as.charAt(start)) == false && isAlpha(as.charAt(end)) == true) {
                    start += 1;
                } else if (isAlpha(as.charAt(start)) == true && isAlpha(as.charAt(end)) == false) {
                    end -= 1;
                } else if (isAlpha(as.charAt(start)) == false && isAlpha(as.charAt(end)) == false) {
                    start += 1;
                    end -= 1;
                } else {
                    if(toLower(as.charAt(start)) != toLower(as.charAt(end))){
                        return false;
                    }

                }
            }
            else{
                start += 1;
                end -= 1;
            }
        }

        return true;
    }

    private static boolean isAlpha(final char ch) {
        if (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z')
            return true;
        else
            return false;
    }

    private static int toLower(char c) {
        if ((c >= 'A') && (c <= 'Z'))
            return c + ('a' - 'A');
        return c;
    }

}
