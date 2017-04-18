package com.mws.web.net.mina;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mws.web.net.bo.Constant;

/**
 * 包解码器，实现分包和粘包的处理逻辑
 * 
 * @author ranfi
 *
 */
public class DataProtocolDecoder extends CumulativeProtocolDecoder {

	private static Logger logger = LoggerFactory.getLogger(DataProtocolDecoder.class);

	private Charset charset = Charset.forName("UTF-8");

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		// 如果包的长度小于8,则重新获取数据包
		if (in.remaining() < 8) {
			return false;
		}

		if (in.remaining() > 0) {
			in.mark();
			int magicWord = in.getInt();
			// 如果码头不正确, 则丢弃
			if (magicWord != Constant.magicWord) {
				logger.error("Server found incorrect protocol header decoding");

				return false;
			}
			int msgLength = in.getInt();
			// 如果包的长度不够,则继续拼装新的数据
			if (msgLength > in.remaining()) {
				in.reset();
				logger.info("发生粘包,继续读取...");
				return false;
			} else {
				byte contents[] = new byte[msgLength];
				in.get(contents, 0, msgLength);

				String content = new String(contents, charset);
				out.write(content);
				in.free();
			}
		}

		// 处理成功继续接收下一个包
		if (in.remaining() == 0) {
			return true;
		}

		return false;
	}

	// @Override
	// public void decode(IoSession session, IoBuffer in,
	// ProtocolDecoderOutput output) throws Exception {
	//
	// while (in.hasRemaining()) {
	// byte b = in.get();
	// buf.put(b);
	// }
	// buf.flip();
	//
	// logger.info("decode data:" + new String(buf.array(), charset));
	//
	// int magicWord = buf.getInt();
	// // 如果解码头格式正确
	// if (magicWord == Constant.magicWord) {
	// buf.mark();
	// int length = buf.getInt();
	// buf.mark();
	// byte[] bytes = new byte[length];
	// buf.get(bytes);
	// buf.clear();
	//
	// String message = new String(bytes, charset);
	// output.write(message);
	// buf.free();
	// } else {
	// logger.error("Server found incorrect protocol header decoding");
	// output.write("{\"status\" : "
	// + ExceptionCode.MAGIC_WORD_NOT_EXISTS.code + "}");
	// }
	// }

	@Override
	public void dispose(IoSession arg0) throws Exception {

	}

	@Override
	public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1)
			throws Exception {

	}
}
