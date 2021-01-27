
class Dict<K, V> {

    private DictEntry<ComplexKey<?, ?>, V>[][] table;
    private int capacity;
    private int size = 0;

    public Dict() {
        this(2);
    }

    @SuppressWarnings("unchecked")
    public Dict(int capacity) {
        this.capacity = capacity;
        this.table = new DictEntry[capacity][capacity];
    }

    @SuppressWarnings("unchecked")
    public synchronized void put(ComplexKey<?, ?> key, V value) {
        if (size >= 0.9 * capacity * capacity) {
            DictEntry<ComplexKey<?, ?>, V>[][] old = table;
            capacity *= 2;
            size = 0;
            table = new DictEntry[capacity][capacity];
            for (DictEntry<ComplexKey<?, ?>, V>[] line : old) {
                for (DictEntry<ComplexKey<?, ?>, V> cell : line) {
                    while (cell != null) {
                        put(cell.key, cell.value);
                        cell = cell.next;
                    }
                }
            }
        }

        DictEntry<ComplexKey<?, ?>, V> data = new DictEntry<>(key, value, null);
        int cellID = getHashID(data.key.getId()) % capacity;
        int cellName = getHashName(data.key.getName()) % capacity;
        DictEntry<ComplexKey<?, ?>, V> exist = table[cellID][cellName];
        if (exist == null) {
            table[cellID][cellName] = data;
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

    public V get(ComplexKey<?, ?> key) {
        DictEntry<ComplexKey<?, ?>, V> cell =
                table[getHashID(key.getId()) % capacity][getHashName(key.getName()) % capacity];
        while (cell != null) {
            if (cell.key.equals(key)) {
                return cell.value;
            }
            cell = cell.next;
        }
        return null;
    }

    public V getByID(Object id) {
        DictEntry<ComplexKey<?, ?>, V>[] lineByID =
                table[getHashID(id) % capacity];
        for (DictEntry<ComplexKey<?, ?>, V> cell : lineByID) {
            while (cell != null) {
                if (id == cell.key.getId()) {
                    return cell.value;
                }
                cell = cell.next;
            }
        }
        return null;
    }

    public V getByName(Object name) {
        for (DictEntry<ComplexKey<?, ?>, V>[] lineByID : table) {
            for (DictEntry<ComplexKey<?, ?>, V> cell : lineByID) {
                while (cell != null) {
                    if (name.equals(cell.key.getName())) {
                        return cell.value;
                    }
                    cell = cell.next;
                }
            }
        }
        return null;
    }

    public synchronized void remove(ComplexKey<?, ?> key) {
        DictEntry<ComplexKey<?, ?>, V> prev = null;
        DictEntry<ComplexKey<?, ?>, V> cell =
                table[getHashID(key.getId()) % capacity][getHashName(key.getName()) % capacity];
        while (cell != null && cell.key != key) {
            prev = cell;
            cell = cell.next;
        }
        if (cell != null) {
            if (prev == null) {
                table[getHashID(key.getId()) % capacity][getHashName(key.getName()) % capacity] = null;
            } else {
                prev.next = cell.next;
            }
            size -= 1;
        }
    }

    public int getSize() {
        return size;
    }

    private int getHashID(Object id) {
        return id == null ? 0 : Math.abs(id.hashCode());
    }

    private int getHashName(Object name) {
        return name == null ? 0 : Math.abs(name.hashCode());
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (DictEntry<ComplexKey<?, ?>, V>[] line : table) {
            for (DictEntry<ComplexKey<?, ?>, V> cell : line) {
                while (cell != null) {
                    result.append(getHashID(cell.key.getId()) % capacity).append(", ")
                            .append(getHashName(cell.key.getName()) % capacity).append(" - ")
                            .append("[").append(cell.key.getId()).append("][").append(cell.key.getName())
                            .append("]: ").append(cell.value).append("\n");
                    cell = cell.next;
                }
            }
        }
        return result.toString();
    }

    static class DictEntry<ComplexKey, V> {

        final ComplexKey key;
        volatile V value;
        DictEntry<ComplexKey, V> next;

        public DictEntry(ComplexKey key, V value, DictEntry<ComplexKey, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
