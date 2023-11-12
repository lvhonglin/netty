package com.kunpeng.test.day13;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress(2222));
        sc.write(Charset.defaultCharset().encode("cdd"));
        sc.write(Charset.defaultCharset().encode("cdd"));
        byte[] bytes = "中国".getBytes("utf-8");

        sc.write(ByteBuffer.allocate(bytes.length).put("中国".getBytes("utf-8")));

        System.out.println("end");
        try {
            TimeUnit.SECONDS.sleep(12313);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
