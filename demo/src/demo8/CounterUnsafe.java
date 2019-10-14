package demo8;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class CounterUnsafe {
    volatile int i = 0;

    private static Unsafe unsafe = null;

    private static long valueOffset;

    static {
        //通过该方法实例化会报错
//        unsafe = Unsafe.getUnsafe();
        //通过反射
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);

            Field ifield = CounterUnsafe.class.getDeclaredField("i");
            valueOffset = unsafe.objectFieldOffset(ifield);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

    public void add() {
        for (; ; ) {
            //拿旧值
            int current = unsafe.getIntVolatile(this, valueOffset);
            //通过cas操作来修改i的值
            if (unsafe.compareAndSwapInt(this, valueOffset, current, current + 1)) {
                break;
            }
        }
    }
}
