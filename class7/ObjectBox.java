package cse2010.class7;

//type unsafe하다.(Ruby, python은 unsafe하다는거지~ 그래서 대규모 프로그램 짜기 적합하지않음)

// LSP : single most important principle
// parent <- child //OK

public class ObjectBox {
    private Object x;

    public ObjectBox(Object x){
        this.x = x;
    }

    public Object get() {
        return x;
    }

    public static void main(String[] args) {
        ObjectBox box = new ObjectBox("hello");
        String value = (String) box.get(); //box.get()의 return타입은 Object타입. 그걸(String)으로 명시적형변환한겨.
        System.out.println(value.toUpperCase()); //toUpperCase()는 String타입에 쓰는 함수임.

        ObjectBox box2 = new ObjectBox(123);
        Integer value2 = (Integer) box2.get();
        System.out.println(value);

        // ** Object Box는 unsafe 합니다.
        ObjectBox unsafebox = new ObjectBox(123);
        String unsafevalue = (String) unsafebox.get();
        // 컴파일러에서는 이 부분을 에러라고 잡지 않아. 근데 RUN하면 TypeCasting관련 에러가 뜨지요. unsafe하다는거야.
        System.out.println(unsafevalue);

        //Generics 사용하기 : Objcet 타입을 T(그냥 몰라)로 바꾼다.
    }

}
