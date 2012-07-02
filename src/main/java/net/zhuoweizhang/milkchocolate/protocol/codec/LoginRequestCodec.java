package net.zhuoweizhang.milkchocolate.protocol.codec;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import org.spout.api.protocol.MessageCodec;

import net.zhuoweizhang.milkchocolate.protocol.ChannelBufferUtils;
import net.zhuoweizhang.milkchocolate.protocol.msg.LoginRequestMessage;

public final class LoginRequestCodec extends MessageCodec<LoginRequestMessage> {
	public LoginRequestCodec() {
		super(LoginRequestMessage.class, 0x01);
	}

	@Override
	public LoginRequestMessage decodeFromServer(ChannelBuffer buffer) {
		String status = ChannelBufferUtils.readString(buffer);
		return new LoginRequestMessage(status);
	}

	@Override
	public LoginRequestMessage decodeFromClient(ChannelBuffer buffer) {
		String version = ChannelBufferUtils.readString(buffer);
		String name = ChannelBufferUtils.readString(buffer);
		String password = ChannelBufferUtils.readString(buffer);
		return new LoginRequestMessage(version, name, password);
	}

	@Override
	public ChannelBuffer encodeToClient(LoginRequestMessage message) {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		ChannelBufferUtils.writeString(buffer, message.getStatus());
		return buffer;
	}

	@Override
	public ChannelBuffer encodeToServer(LoginRequestMessage message) {
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		ChannelBufferUtils.writeString(buffer, message.getVersion());
		ChannelBufferUtils.writeString(buffer, message.getName());
		ChannelBufferUtils.writeString(buffer, message.getPassword());
		return buffer;
	}

}
