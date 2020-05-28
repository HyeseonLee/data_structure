package cse2010.class7;

public class IntBox {
    private int x;

    public IntBox(int x) {
        this.x = x;
    }
    public int get() {
        return x;
    }

    public static void main(String[] args) {
        IntBox box = new IntBox(123);

        int some = box.get();
    }
}
// Box가 Int, String, Double 등등 다양한 Type을 커버하고 싶다면 ..?
//1. 복붙으로 IntBox, StringBox, DoubleBox 등등을 만든다 ..
//2. 객체지향 개념을 쓴다.