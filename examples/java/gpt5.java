void decode(final byte[] input, int inPos, final int inAvail, final Context context) {
        if(context.eof) {
            return;
        }
        if(inAvail < 0) {
            context.eof = true;
        }
        for(int i = 0; i < inAvail; i++) {
            final byte b = input[inPos++];
            if(b == PAD) {
                context.eof = true;
                break;
            }
            final byte[] buffer = ensureBufferSize(decodeSize, context);
            if(b >= 0 && b < DECODE_TABLE.length) {
                final int result = DECODE_TABLE[b];
                if(result >= 0) {
                    context.modulus = (context.modulus + 1) % BYTES_PER_UNENCODED_BLOCK;
                    context.ibitWorkArea = (context.ibitWorkArea << BITS_PER_ENCODED_BYTE) + result;
                    if(context.modulus == 0) {
                        buffer[context.pos++] = (byte) ((context.ibitWorkArea >> 16) & MASK_8BITS);
                        buffer[context.pos++] = (byte) ((context.ibitWorkArea >> 8) & MASK_8BITS);
                        buffer[context.pos++] = (byte) (context.ibitWorkArea & MASK_8BITS);
                    }
                }
            }
        }
        
        if(context.eof && context.modulus >= 2) {
            final byte[] buffer = ensureBufferSize(decodeSize, context);
            switch(context.modulus) {
                case 2: // 12 bits = 8 + 4
                    buffer[context.pos++] = (byte) ((context.ibitWorkArea >> 2) & MASK_8BITS);
                    break;
                case 3: // 18 bits = 8 + 8 + 2
                    buffer[context.pos++] = (byte) ((context.ibitWorkArea >> 10) & MASK_8BITS);
                    buffer[context.pos++] = (byte) ((context.ibitWorkArea >> 2) & MASK_8BITS);
                    break;
                default:
                    throw new IllegalStateException("Impossible modulus " + context.modulus);
            }
        }
