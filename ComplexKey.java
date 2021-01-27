import java.util.Objects;

class ComplexKey<K1, K2> {

    private final K1 id;
    private final K2 name;

    public K1 getId() {
        return id;
    }

    public K2 getName() {
        return name;
    }

    public ComplexKey(K1 id, K2 name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplexKey<?, ?> that = (ComplexKey<?, ?>) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}