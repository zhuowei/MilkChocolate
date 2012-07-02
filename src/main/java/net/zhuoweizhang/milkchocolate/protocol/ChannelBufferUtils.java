/*
 * This file was originally part of Vanilla, and adapted for the MilkChocolate project. The original copyright license is below.
 *
 * Copyright (c) 2011-2012, VanillaDev <http://www.spout.org/>
 * Vanilla is licensed under the SpoutDev License Version 1.
 *
 * Vanilla is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the SpoutDev License Version 1.
 *
 * Vanilla is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the SpoutDev License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://www.spout.org/SpoutDevLicenseV1.txt> for the full license,
 * including the MIT license.
 */
package net.zhuoweizhang.milkchocolate.protocol;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.jboss.netty.buffer.ChannelBuffer;

import org.spout.api.inventory.ItemStack;
import org.spout.api.material.Material;
import org.spout.api.math.MathHelper;
import org.spout.api.math.Vector2;
import org.spout.api.math.Vector3;
import org.spout.api.util.Parameter;

public final class ChannelBufferUtils {
	/**
	 * The UTF-8 character set.
	 */
	private static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");

	/**
	 * Writes a UTF16 string to the buffer.
	 * @param buf The buffer.
	 * @param str The string.
	 * @throws IllegalArgumentException if the string is too long <em>after</em>
	 * it is encoded.
	 */
	public static void writeUtf16String(ChannelBuffer buf, String str) {
		int len = str.length();
		if (len >= 65536) {
			throw new IllegalArgumentException("String too long.");
		}

		buf.writeShort(len);
		for (int i = 0; i < len; ++i) {
			buf.writeChar(str.charAt(i));
		}
	}

	/**
	 * Writes a UTF-8 string to the buffer.
	 * @param buf The buffer.
	 * @param str The string.
	 * @throws IllegalArgumentException if the string is too long <em>after</em>
	 * it is encoded.
	 */
	public static void writeString(ChannelBuffer buf, String str) {
		try {
			byte[] bytes = str.getBytes(CHARSET_UTF8.name());
			if (bytes.length >= 65536) {
				throw new IllegalArgumentException("Encoded UTF-8 string too long.");
			}

			buf.writeShort(bytes.length);
			buf.writeBytes(bytes);
		} catch (UnsupportedEncodingException e) {}
	}

	/**
	 * Reads a string from the buffer.
	 * @param buf The buffer.
	 * @return The string.
	 */
	public static String readUtf16String(ChannelBuffer buf) {
		int len = buf.readUnsignedShort();

		char[] characters = new char[len];
		for (int i = 0; i < len; i++) {
			characters[i] = buf.readChar();
		}

		return new String(characters);
	}

	/**
	 * Reads a UTF-8 encoded string from the buffer.
	 * @param buf The buffer.
	 * @return The string.
	 * @throws UnsupportedEncodingException if the encoding isn't supported.
	 */
	public static String readString(ChannelBuffer buf) {
		int len = buf.readUnsignedShort();

		try {
			byte[] bytes = new byte[len];
			buf.readBytes(bytes);

			return new String(bytes, CHARSET_UTF8.name());
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}


	public static Vector3 readVector3(ChannelBuffer buf) {
		float x = buf.readFloat();
		float y = buf.readFloat();
		float z = buf.readFloat();
		return new Vector3(x, y, z);
	}

	public static void writeVector3(Vector3 vec, ChannelBuffer buf) {
		buf.writeFloat(vec.getX());
		buf.writeFloat(vec.getY());
		buf.writeFloat(vec.getZ());
	}

	public static Vector2 readVector2(ChannelBuffer buf) {
		float x = buf.readFloat();
		float z = buf.readFloat();
		return new Vector2(x, z);
	}

	public static void writeVector2(Vector2 vec, ChannelBuffer buf) {
		buf.writeFloat(vec.getX());
		buf.writeFloat(vec.getY());
	}

	public static Color readColor(ChannelBuffer buf) {
		int argb = buf.readInt();
		return new Color(argb);
	}

	public static void writeColor(Color color, ChannelBuffer buf) {
		buf.writeInt(color.getRGB());
	}

	public static int protocolifyPosition(float pos) {
		return MathHelper.floor(pos * 32);
	}

	public static float deProtocolifyPosition(int pos) {
		return pos / 32F;
	}

	public static int protocolifyRotation(float rot) {
		return MathHelper.wrapByte(MathHelper.floor((rot / 360) * 256));
	}

	public static float deProtocolifyRotation(int rot) {
		return (rot / 256f) * 360;
	}

	/**
	 * Default private constructor to prevent instantiation.
	 */
	private ChannelBufferUtils() {
	}
}
