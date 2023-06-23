public class Base64Encoder {
    private static final char[] BASE64_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
    public static String encode(String input) {
        byte[] bytesToEncode = input.getBytes();
        StringBuilder encodedStringBuilder = new StringBuilder();
        int paddingCount = (3 - (bytesToEncode.length % 3)) % 3; // Calculate padding count
        for (int i = 0; i < bytesToEncode.length; i += 3) {
            int block = (bytesToEncode[i] & 0xFF) << 16 |
                        (bytesToEncode[i + 1] & 0xFF) << 8 |
                        (bytesToEncode[i + 2] & 0xFF);
            encodedStringBuilder.append(BASE64_ALPHABET[(block >> 18) & 0x3F]);
            encodedStringBuilder.append(BASE64_ALPHABET[(block >> 12) & 0x3F]);
            encodedStringBuilder.append(BASE64_ALPHABET[(block >> 6) & 0x3F]);
            encodedStringBuilder.append(BASE64_ALPHABET[block & 0x3F]);
        }
        for (int i = 0; i < paddingCount; i++) {
            encodedStringBuilder.setCharAt(encodedStringBuilder.length() - 1 - i, '=');
        }
        return encodedStringBuilder.toString();
    }
    public static void main(String[] args) {
        String originalString = "Hello, World!";
        System.out.println("Original String: " + originalString);
        String encodedString = encode(originalString);
        System.out.println("Encoded String: " + encodedString);
    }
}

public class Base64Decoder {
    private static final int[] BASE64_ALPHABET = new int[256];
    static {
        for (int i = 0; i < 64; i++) {
            BASE64_ALPHABET["ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(i)] = i;
        }
    }
    public static String decode(String encodedString) {
        StringBuilder decodedStringBuilder = new StringBuilder();
        int paddingCount = 0;
        int length = encodedString.length();
        for (int i = length - 1; encodedString.charAt(i) == '='; i--) {
            paddingCount++;
        }
        int block = 0;
        int blockLength = 0;
        for (int i = 0; i < length; i++) {
            char c = encodedString.charAt(i);
            if (c == '=')
                continue;
            int value = BASE64_ALPHABET[c];
            block = (block << 6) | value;
            blockLength += 6;
            if (blockLength >= 8) {
                blockLength -= 8;
                int decodedByte = (block >> blockLength) & 0xFF;
                decodedStringBuilder.append((char) decodedByte);
            }
        }
        return decodedStringBuilder.toString();
    }
    public static void main(String[] args) {
        String encodedString = "SGVsbG8sIFdvcmxkIQ==";
        System.out.println("Encoded String: " + encodedString);
        String decodedString = decode(encodedString);
        System.out.println("Decoded String: " + decodedString);
    }
}
