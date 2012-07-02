package net.zhuoweizhang.milkchocolate.protocol.msg;

import org.apache.commons.lang3.builder.ToStringBuilder;

import org.spout.api.protocol.Message;
import org.spout.api.util.SpoutToStringStyle;

public class LoginRequestMessage extends Message {

	private final boolean fromClient;

	private final String version, name, password;

	public LoginRequestMessage(String version, String name, String password) {
		this.version = version;
		this.name = name;
		this.password = password;
		this.fromClient = true;
	}

	public LoginRequestMessage(String status) {
		this.version = status;
		this.name = null;
		this.password = null;
		this.fromClient = false;
	}

	public String getVersion() {
		return version;
	}

	public String getStatus() {
		return version;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String toString() {
		ToStringBuilder b = new ToStringBuilder(this, SpoutToStringStyle.INSTANCE).append("fromClient", fromClient);
		if (fromClient) {
			b.append("version", version).append("name", name).append("password", password);
		} else {
			b.append("status", version);
		}
		return b.toString();
	}
}
