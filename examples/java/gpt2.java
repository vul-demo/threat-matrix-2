package io.threatrix.commandcenter.core;

import java.math.BigInteger;

public class Base64 {


    private static final byte[] STANDARD_ENCODE_TABLE = {
            (byte) 'A', (byte) 'B', (byte) 'C', (byte) 'D', (byte) 'E',
            (byte) 'F', (byte) 'G', (byte) 'H', (byte) 'I', (byte) 'J',
            (byte) 'K', (byte) 'L', (byte) 'M', (byte) 'N', (byte) 'O',
            (byte) 'P', (byte) 'Q', (byte) 'R', (byte) 'S', (byte) 'T',
            (byte) 'U', (byte) 'V', (byte) 'W', (byte) 'X', (byte) 'Y',
            (byte) 'Z',

            (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e',
            (byte) 'f', (byte) 'g', (byte) 'h', (byte) 'i', (byte) 'j',
            (byte) 'k', (byte) 'l', (byte) 'm', (byte) 'n', (byte) 'o',
            (byte) 'p', (byte) 'q', (byte) 'r', (byte) 's', (byte) 't',
            (byte) 'u', (byte) 'v', (byte) 'w', (byte) 'x', (byte) 'y',
            (byte) 'z',

            (byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4',
            (byte) '5', (byte) '6', (byte) '7', (byte) '8', (byte) '9',

            (byte) '+', (byte) '/'
    };

    private static final byte[] DECODE_TABLE = {
      -1, -1, -1, -1, -1, -1, -1, -1, // 0-7
      -1, -1, -1, -1, -1, -1, -1, -1, // 8-15
        -1, -1, -1, -1, -1, -1, -1, -1, // 16-23
        -1, -1, -1, -1, -1, -1, -1, -1, // 24-31
        -1, -1, -1, -1, -1, -1, -1, -1, // 32-39
        -1, -1, -1, 62, -1, -1, -1, 63, // 40-47
        52, 53, 54, 55, 56, 57, 58, 59, // 48-55
        60, 61, -1, -1, -1, -1, -1, -1, // 56-63
        -1,  0,  1,  2,  3,  4,  5,  6, // 64-71
         7,  8,  9, 10, 11, 12, 13, 14, // 72-79
        15, 16, 17, 18, 19, 20, 21, 22, // 80-87
        23, 24, 25, -1, -1, -1, -1, -1, // 88-95
        -1, 26, 27, 28, 29, 30, 31, 32, // 96-103
        33, 34, 35, 36, 37, 38, 39, 40, // 104-111
        41, 42, 43, 44, 45, 46, 47, 48, // 112-119
        49, 50, 51, -1, -1, -1, -1, -1, // 120-127
    };

    /**
     * Encodes a byte array into Base64 notation.
     *
     * @param data
     *            a byte array to encode.
     * @return a char array containing the Base64 encoded data.
     */
    static byte[] toIntegerBytes(final BigInteger bigInt) {
        int bitlen = bigInt.bitLength();
        bitlen = ((bitlen + 7) >> 3) << 3;
        final byte[] bigBytes = bigInt.toByteArray();
        if(((bigInt.bitLength() % 8) != 0) && (((bigInt.bitLength() / 8) + 1) == (bitlen / 8))) {
            return bigBytes;
        }
        
        int startSrc = 0;
        int len = bigBytes.length;
        if((bigInt.bitLength() % 8) == 0) {
            startSrc = 1;
            len--;
        }
        final int startDst = bitlen / 8 - len; // destination leading zeroes
        final byte[] resizedBytes = new byte[bitlen / 8];
        System.arraycopy(bigBytes, startSrc, resizedBytes, startDst, len);
        return resizedBytes;
        
        


    }


}
