package com.tsb.study.nio.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

    /**
     * Socket服务端
     */
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket=new ServerSocket(8888);

            while (true){
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("接受到一个客户端。。。。。。。。。。");
                    InputStream inputStream=socket.getInputStream();
                    System.out.println("1111111");
                    InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
                    System.out.println("222222");
                    BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                    System.out.println("3333333");
                    String temp=null;
                    String info="";
                    while((temp=bufferedReader.readLine())!=null){
                        info+=temp;
                        System.out.println("已接收到客户端连接，当前信息："+temp);
                        System.out.println("服务端接收到客户端信息："+info+",当前客户端ip为："+socket.getInetAddress().getHostAddress());
                    }


                    OutputStream outputStream=socket.getOutputStream();//获取一个输出流，向服务端发送信息
                    PrintWriter printWriter=new PrintWriter(outputStream);//将输出流包装成打印流
                    printWriter.print("你好，服务端已接收到您的信息");
                    printWriter.flush();
                    socket.shutdownOutput();


                    printWriter.close();
                    outputStream.close();
                    bufferedReader.close();
                    inputStream.close();
                    socket.close();
                    System.out.println("结束一个客户端。。。。。。。。。。");
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}