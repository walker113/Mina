package walker.learn.mina.filter;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;



public class CusTextLineFactory implements ProtocolCodecFactory {

    private CusTextLineEncoder mEncoder;
    private CusTextLineDecoder mDecoder;
    private CusTextLineCumulativeDecoder mCumulativeDecoder;

    public CusTextLineFactory() {
        mEncoder = new CusTextLineEncoder();
        mDecoder = new CusTextLineDecoder();
        mCumulativeDecoder = new CusTextLineCumulativeDecoder();
    }

    @Override
    public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
        return mEncoder;
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
        return mCumulativeDecoder;
    }
}
