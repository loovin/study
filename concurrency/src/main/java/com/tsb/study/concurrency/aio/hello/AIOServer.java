package com.tsb.study.concurrency.aio.hello;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AIOServer {
    AsynchronousServerSocketChannel server;

    public void start() throws Exception {
        server = AsynchronousServerSocketChannel.open().bind( new InetSocketAddress(8888));

        server.accept(null,
                new CompletionHandler<AsynchronousSocketChannel, Object>() {
                    final ByteBuffer buffer = ByteBuffer.allocate(1024);

                    public void completed(AsynchronousSocketChannel result,
                                          Object attachment) {
                        System.out.println(Thread.currentThread().getName());
                        Future<Integer> writeResult = null;
                        try {
                            buffer.clear();
                            result.read(buffer).get(100, TimeUnit.SECONDS);
                            buffer.flip();
                            writeResult = result.write(buffer);
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        } catch (TimeoutException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                server.accept(null, this);
                                writeResult.get();
                                result.close();
                            } catch (Exception e) {
                                System.out.println(e.toString());
                            }
                        }
                    }

                    @Override
                    public void failed(Throwable exc, Object attachment) {
                        System.out.println("failed: " + exc);
                    }
                });
    }
}
