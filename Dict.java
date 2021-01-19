import java.util.Objects;

class Dict<K, V> {

    private final DictEntry<K, V>[] table;
    private int capacity;
    private int size = 0;

    public Dict() {
        this(4);
    }

    public Dict(int capacity) {
        this.capacity = capacity;
        this.table = new DictEntry[capacity];
    }

    public void put(K key, V value) {
        DictEntry<K, V> data = new DictEntry<>(key, value, null);
        int cell = getHash(key) % table.length;
        DictEntry<K, V> exist = table[cell];
        if (exist == null) {
            table[cell] = data;
            size++;
        }
        else {
            while (exist.next != null) {
                if (exist.key.equals(key)) {
                    exist.value = value;
                    return;
                }
                exist = exist.next;
            }
            if (exist.key.equals(key)) {
                exist.value = value;
            }
            else {
                exist.next = data;
                size++;
            }
        }
    }

    public V get(K key) {
        DictEntry<K, V> cell = table[getHash(key) % table.length];
        while (cell != null) {
            if (key == cell.key) {
                return cell.value;
            }
            cell = cell.next;
        }
        return null;
    }

    public int getSize() {
        return size;
    }

    private int getHash(K key) {
        return key == null ? 0 : Math.abs(key.hashCode());
    }

    static class DictEntry<K, V> {

        final K key;
        V value;
        DictEntry<K, V> next;

        public DictEntry(K key, V value, DictEntry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DictEntry<?, ?> dictEntry = (DictEntry<?, ?>) o;
            return Objects.equals(key, dictEntry.key) &&
                    Objects.equals(value, dictEntry.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }
    }

}
