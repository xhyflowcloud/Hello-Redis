package lovenn.net.pool;

import redis.clients.jedis.Jedis;

import java.util.*;

public abstract class Pool<T extends XRedis> extends Base {
    private int poolSize = 5;
    private int idle = 30000;
    private List<T> pools;
    private final Map<String, Long> idleMap = new HashMap<>();

    public Pool() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                check();
            }
        }).start();
    }

    public Jedis getResource() {
        synchronized (this) {
            if (pools == null) initPools();
            if (pools.size() == poolSize) {
                try {
                    wait();
                    T instance = allocate();
                    pools.add(instance);
                    idleMap.put(instance.getUuid(), new Date().getTime());
                    return instance.getJedis();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }


    private void initPools() {
        if (pools == null && poolSize > 0) {
            pools = new ArrayList<>(poolSize);
        }
    }

    private void check() {
        while (true) {
            try {
                wait(idle);
                synchronized (idleMap) {
                    if (idleMap.size() > 0) {
                        Iterator<Map.Entry<String, Long>> iterator = idleMap.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Map.Entry<String, Long> entry = iterator.next();
                            if (new Date().getTime() >= entry.getValue() + idle) iterator.remove();
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    protected abstract T allocate();
}
