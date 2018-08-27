package curator;

import org.apache.curator.framework.recipes.locks.InterProcessMutex;

/**
 * 可重入共享锁
 * */
public class SharedReentrantLockDemo {
    private InterProcessMutex lock = null;
}
