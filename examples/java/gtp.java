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
