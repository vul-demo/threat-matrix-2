private final int size;
        private final int[] bits;
    
        public BloomFilter(int size) {
            this.size = size;
            this.bits = new int[size];
        }
    
        public void add(String value) {
            int hash = value.hashCode();
            int index = Math.abs(hash % size);
            bits[index] = 1;
        }
    
        public boolean contains(String value) {
            int hash = value.hashCode();
            int index = Math.abs(hash % size);
            return bits[index] == 1;
        }
    
        public int getSize() {
            return size;
        }
    
        public int[] getBits() {
            return bits;
        }
