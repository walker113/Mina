package walker.learn.mina.filter;

import android.text.TextUtils;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.w3c.dom.Text;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;


public class CusTextLineEncoder implements ProtocolEncoder {
    private static final String EN_TAG = "encoder";
    @Override
    public void encode(IoSession ioSession, Object o, ProtocolEncoderOutput protocolEncoderOutput) throws Exception {
        String s = null;
        if (o instanceof String) {
            s = (String) o;
        }
        if (!TextUtils.isEmpty(s)) {
            // 获取系统默认编码
            CharsetEncoder charsetEncoder = (CharsetEncoder) ioSession.getAttribute(EN_TAG);
            if (charsetEncoder == null) {
                charsetEncoder = Charset.defaultCharset().newEncoder();
                ioSession.setAttribute(EN_TAG, charsetEncoder);
            }
            // 开辟内存
            IoBuffer ioBuffer = IoBuffer.allocate(s.length());
            // 是IoBuffuer可以自动扩展
            ioBuffer.setAutoExpand(true);
            ioBuffer.putString(s, charsetEncoder);
            ioBuffer.flip();
            protocolEncoderOutput.write(ioBuffer);
        }
    }

    @Override
    public void dispose(IoSession ioSession) throws Exception {

    }
}
