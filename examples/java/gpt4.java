public void decode(final String data) {
        final byte[] bytes = data.getBytes();
        final int length = bytes.length;
        final int blocks = length / 4;
        final int padding = (bytes[length - 2] == '=') ? 2 : (bytes[length - 1] == '=') ? 1 : 0;
        final int length2 = length - padding;
        final byte[] decoded = new byte[length2 / 4 * 3 + padding];
        int src = 0, dst = 0;
        for(int i = 0; i < blocks; i++) {
            final int block = (DECODE_TABLE[bytes[src++]] << 18) | (DECODE_TABLE[bytes[src++]] << 12) | (DECODE_TABLE[bytes[src++]] << 6) | (DECODE_TABLE[bytes[src++]]);
            decoded[dst++] = (byte) (block >> 16);
            decoded[dst++] = (byte) (block >> 8);
            decoded[dst++] = (byte) (block);
        }
        if(padding == 1) {
            final int block = (DECODE_TABLE[bytes[src++]] << 18) | (DECODE_TABLE[bytes[src++]] << 12) | (DECODE_TABLE[bytes[src++]] << 6);
            decoded[dst++] = (byte) (block >> 16);
            decoded[dst++] = (byte) (block >> 8);
        } else if(padding == 2) {
            final int block = (DECODE_TABLE[bytes[src++]] << 18) | (DECODE_TABLE[bytes[src++]] << 12);
            decoded[dst++] = (byte) (block >> 16);
        }
