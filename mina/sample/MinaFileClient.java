package walker.learn.mina.sample;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.handler.stream.StreamIoHandler;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MinaFileClient extends StreamIoHandler{
    IoSession session;
    public void setSession(IoSession session) {
        this.session = session;
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        System.out.println("sessionCreated");
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        super.exceptionCaught(session, cause);
        System.out.println(cause);
    }



    public IoSession getSession() {
        return session;
    }

    @Override
    protected void processStreamIo(IoSession session, InputStream in,
                                   OutputStream out) {
        //客户端发送文件
        System.out.println("processStreamIo");
        File sendFile = new File("/sdcard/" + "bb.FLV");
        if (!sendFile.exists()) {
            System.out.println("File not Exists");
        }
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(sendFile);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //放入线程让其执行
        //客户端一般都用一个线程实现即可 不用线程池
        new IoStreamThreadWork(fis,out).start();
        return;
    }

    public void createClienStream(){
        //  /192.168.1.111:9899
        int port = 9899;
        String local = "192.168.1.111";

        NioSocketConnector connector = new NioSocketConnector();
        DefaultIoFilterChainBuilder chain = connector.getFilterChain();
        ObjectSerializationCodecFactory factory = new ObjectSerializationCodecFactory();
        factory.setDecoderMaxObjectSize(Integer.MAX_VALUE);
        factory.setEncoderMaxObjectSize(Integer.MAX_VALUE);
        chain.addLast("logging", new LoggingFilter());//用于打印日志可以不写
        connector.setHandler(new MinaFileClient());
        ConnectFuture connectFuture = connector.connect(new InetSocketAddress(local, port));
        System.out.println(connectFuture.isConnected());
        System.out.println(connectFuture.getException());
        connectFuture.awaitUninterruptibly();//写上这句为了得到下面的session 意思是等待连接创建完成 让创建连接由异步变同步
        //后来表明我开始的想法不行 动态依旧不能做到
//      @SuppressWarnings("unused")
//      IoSession session = connectFuture.getSession();
//      setSession(session);
    }
    public static void main(String[] args) {
        MinaFileClient client = new MinaFileClient();
        client.createClienStream();
    }
}