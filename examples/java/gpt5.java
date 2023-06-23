@Override
    void decode(final byte[] input, int inPos, final int inAvail, final Context context) {
        if (context.eof) {
            return;
        }
        if (inAvail < 0) {
            context.eof = true;
        }
        for (int i = 0; i < inAvail; i++) {
            final byte[] buffer = ensureBufferSize(decodeSize, context);
            final byte b = input[inPos++];
            if (b == pad) {
                // We're done.
                context.eof = true;
                break;
            }
            if (b >= 0 && b < DECODE_TABLE.length) {
                final int result = DECODE_TABLE[b];
                if (result >= 0) {
                    context.modulus = (context.modulus+1) % BYTES_PER_ENCODED_BLOCK;
                    context.ibitWorkArea = (context.ibitWorkArea << BITS_PER_ENCODED_BYTE) + result;
                    if (context.modulus == 0) {
                        buffer[context.pos++] = (byte) ((context.ibitWorkArea >> 16) & MASK_8BITS);
                        buffer[context.pos++] = (byte) ((context.ibitWorkArea >> 8) & MASK_8BITS);
                        buffer[context.pos++] = (byte) (context.ibitWorkArea & MASK_8BITS);
                    }
                }
            }
        }
