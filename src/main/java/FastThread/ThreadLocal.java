package FastThread;

import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.concurrent.FastThreadLocalThread;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: 喜欢排长队
 * @Date: 2024/03/31/19:07
 * @Description:
 */
public class ThreadLocal {
    private static final FastThreadLocal<String> fastThreadLocal = new FastThreadLocal(){
        @Override
        protected Object initialValue() throws Exception {
            return "NAN";
        }
    };
    public static void main(String[] args) {

        FastThreadLocalThread fastThreadLocalThread = new FastThreadLocalThread(()->{
            fastThreadLocal.set("nihao");
            System.out.println(fastThreadLocal.get());
            fastThreadLocal.removeAll();
            HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
            objectObjectHashMap.put("1","q");
            Set<Map.Entry<Object, Object>> entries = objectObjectHashMap.entrySet();


        });
        fastThreadLocalThread.start();
        Object o = new Object();
        Object o1 = new Object();
        System.out.println(o == o1 );
    }
}
