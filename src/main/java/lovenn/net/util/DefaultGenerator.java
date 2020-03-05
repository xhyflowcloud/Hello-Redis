package lovenn.net.util;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class DefaultGenerator implements Generator {
    private AtomicLong atomicLong = new AtomicLong(100000000L);

    @Override
    public long generate() {
        return atomicLong.getAndIncrement();
    }
}
