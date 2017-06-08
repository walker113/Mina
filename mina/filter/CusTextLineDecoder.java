package walker.learn.mina.filter;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;


public class CusTextLineDecoder implements ProtocolDecoder {
    @Override
    public void decode(IoSession ioSession, IoBuffer ioBuffer, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {

        int startPosition = ioBuffer.position();
        while (ioBuffer.hasRemaining()) {
            byte b = ioBuffer.get();
            if (b == '\n') {
                // 记录当前位置
                int currentPosition = ioBuffer.position();
                // 记录当前终点
                int limit = ioBuffer.limit();

                // 重定向起点
                ioBuffer.position(startPosition);
                // 终点
                ioBuffer.limit(currentPosition);
                // 截取数据
                IoBuffer buffer = ioBuffer.slice();
                byte[] dest = new byte[buffer.limit()];
                // 把buffer字节赋值到dest数组
                buffer.get(dest);
                String str = new String(dest);
                // 把字符串写出
                protocolDecoderOutput.write(str);

                // 还原位置
                ioBuffer.position(currentPosition);
                // 还原终点
                ioBuffer.limit(limit);
            }
        }
    }

    @Override
    public void finishDecode(IoSession ioSession, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {

    }

    @Override
    public void dispose(IoSession ioSession) throws Exception {

    }
}
