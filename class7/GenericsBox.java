package cse2010.class7;

public class GenericsBox<T> {
    private T x;

    public GenericsBox(T x){
        this.x = x;
    }

    public T get() {
        return x;
    }

    public static void main(String[] args) {
        GenericsBox<String> sbox = new GenericsBox<>("hello");
        String value = sbox.get();
        System.out.println(value.toUpperCase());

        GenericsBox<Integer> ibox = new GenericsBox<>(123);
        Integer value2 = ibox.get();
        System.out.println(value.toUpperCase());
    }
}

