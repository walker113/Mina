package walker.learn.mina.client;



import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.io.BufferedReader;
import java.net.InetSocketAddress;

public class MinaClient {

    private void init() {

        NioSocketConnector connector = new NioSocketConnector();
        connector.setHandler(new ClientHandler());
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory()));
        ConnectFuture connect = connector.connect(new InetSocketAddress("", 9898));

        // 由于mina是非阻塞的，因此不会等待连接后才继续允许，以下设置即等待连接后才继续运行
        connect.awaitUninterruptibly();

        IoSession session = connect.getSession();
    }

}
