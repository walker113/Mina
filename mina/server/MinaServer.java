package walker.learn.mina.server;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;

import walker.learn.mina.filter.CusTextLineFactory;

/**
 *  网络管理和消息处理做分隔，MiNa使用专门的Handler处理
 */
public class MinaServer {
    private void init() {
        try {
            NioSocketAcceptor acceptor = new NioSocketAcceptor();
            acceptor.setHandler(new ServerHandler());
            acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new CusTextLineFactory()));
            // 设置闲置响应时间
            acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 5);
            acceptor.bind(new InetSocketAddress(9898));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
