package net.zhuoweizhang.milkchocolate.protocol.msg;

import org.apache.commons.lang3.builder.ToStringBuilder;

import org.spout.api.protocol.Message;
import org.spout.api.inventory.ItemStack;
import org.spout.api.util.SpoutToStringStyle;

public class GetPlayerDataMessage extends Message {

	private boolean fromClient;

	private final float x, y, z;

	private final ItemStack[] items;

	public GetPlayerDataMessage() {
		this.fromClient = true;
		this.x = this.y = this.z = 0;
		this.items = null;
	}

	public GetPlayerDataMessage(float x, float y, float z, ItemStack[] items) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.items = items;
		this.fromClient = false;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public ItemStack[] getItems() {
		return items;
	}

	public String toString() {
		ToStringBuilder b = new ToStringBuilder(this, SpoutToStringStyle.INSTANCE).append("fromClient", fromClient);
		if (!fromClient) {
			b.append("x", x).append("y", y).append("z", z).append("items", items, true);
		}
		return b.toString();
	}
}
