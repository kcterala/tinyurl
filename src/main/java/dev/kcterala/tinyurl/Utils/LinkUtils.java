package dev.kcterala.tinyurl.Utils;

public class LinkUtils {

    private static final char[] alphaNumericString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

    public static String generateRandomShortCode(int randomNumber) {
        StringBuilder stringBuilder = new StringBuilder();
        while (randomNumber != 0) {
            stringBuilder.append(alphaNumericString[randomNumber % 62]);
            randomNumber /= 62;
        }

        return new String(stringBuilder.reverse());
    }

    public static int decodeShortCode(final String shortCode) {
        int id = 0;
        for (int i = 0; i < shortCode.length(); i++) {
            if ('a' <= shortCode.charAt(i) && shortCode.charAt(i) <= 'z') {
                id = id * 62 + shortCode.charAt(i) - 'a';
            }

            if ('A' <= shortCode.charAt(i) && shortCode.charAt(i) <= 'Z') {
                id = id * 62 + shortCode.charAt(i) - 'A' + 26;
            }

            if ('0' <= shortCode.charAt(i) && shortCode.charAt(i) <= '9') {
                id = id * 62 + shortCode.charAt(i) - '0' + 52;
            }
        }
        return id;
    }


    public static void main(String[] args) {
        int randomNumber = 12345678;
        String shortCode = generateRandomShortCode(randomNumber);
        System.out.println(shortCode);
        int id = decodeShortCode(shortCode);
        System.out.println(id);
    }
}
