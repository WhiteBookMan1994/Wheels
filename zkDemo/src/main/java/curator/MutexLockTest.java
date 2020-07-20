package curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

/**
 * @author dingchenchen
 * @since 2020/7/19
 */
public class MutexLockTest {
    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", retryPolicy);
        client.start();
        /*try {
            System.out.println(client.create().forPath("/curator/test","curatorTest".getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        /**
         * 本质就是在/curator/test/mutexlock节点（分布式场景下）下创建临时序列节，序列小的获取到锁
         * */
        InterProcessMutex lock = new InterProcessMutex(client, "/curator/test/mutexlock");
        new Thread(()->{
            try {
                if ( lock.acquire(20L, TimeUnit.SECONDS) ){
                    System.out.println("myThread 线程释放锁成功......");
                    try {
                        Thread.sleep(15000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lock.release();
                        System.out.println("myThread 线程释放锁成功......");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"myThread").start();
        if ( lock.acquire(20L, TimeUnit.SECONDS) )
        {
            try
            {
                System.out.println("main 线程获取锁成功......");
                Thread.sleep(16000);
            }
            finally
            {
                lock.release();
                System.out.println("main 线程释放锁成功......");
            }
        }

    }
}
