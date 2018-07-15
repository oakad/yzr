/*
 * Copyright (c) 2018 Alex Dubov <oakad@yahoo.com>
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package yzr.basis;

public class ByteHelper {
	private ByteHelper() {
	}

	public static int bitCount(byte b) {
		return BYTE_PARAMS[b] & 0xf;
	}

	public static int trailingZeroes(byte b) {
		return BYTE_PARAMS[b] >>> 4;
	}

	private static byte[] BYTE_PARAMS = new byte[] {
		(byte)0x80, (byte)0x01, (byte)0x11, (byte)0x02, // 0x00
		(byte)0x21, (byte)0x02, (byte)0x12, (byte)0x03,
		(byte)0x31, (byte)0x02, (byte)0x12, (byte)0x03,
		(byte)0x22, (byte)0x03, (byte)0x13, (byte)0x04,
		(byte)0x41, (byte)0x02, (byte)0x12, (byte)0x03, // 0x10
		(byte)0x22, (byte)0x03, (byte)0x13, (byte)0x04,
		(byte)0x32, (byte)0x03, (byte)0x13, (byte)0x04,
		(byte)0x23, (byte)0x04, (byte)0x14, (byte)0x05,
		(byte)0x51, (byte)0x02, (byte)0x12, (byte)0x03, // 0x20
		(byte)0x22, (byte)0x03, (byte)0x13, (byte)0x04,
		(byte)0x32, (byte)0x03, (byte)0x13, (byte)0x04,
		(byte)0x23, (byte)0x04, (byte)0x14, (byte)0x05,
		(byte)0x42, (byte)0x03, (byte)0x13, (byte)0x04, // 0x30
		(byte)0x23, (byte)0x04, (byte)0x14, (byte)0x05,
		(byte)0x33, (byte)0x04, (byte)0x14, (byte)0x05,
		(byte)0x24, (byte)0x05, (byte)0x15, (byte)0x06,
		(byte)0x61, (byte)0x02, (byte)0x12, (byte)0x03, // 0x40
		(byte)0x22, (byte)0x03, (byte)0x13, (byte)0x04,
		(byte)0x32, (byte)0x03, (byte)0x13, (byte)0x04,
		(byte)0x23, (byte)0x04, (byte)0x14, (byte)0x05,
		(byte)0x42, (byte)0x03, (byte)0x13, (byte)0x04, // 0x50
		(byte)0x23, (byte)0x04, (byte)0x14, (byte)0x05,
		(byte)0x33, (byte)0x04, (byte)0x14, (byte)0x05,
		(byte)0x24, (byte)0x05, (byte)0x15, (byte)0x06,
		(byte)0x52, (byte)0x03, (byte)0x13, (byte)0x04, // 0x60
		(byte)0x23, (byte)0x04, (byte)0x14, (byte)0x05,
		(byte)0x33, (byte)0x04, (byte)0x14, (byte)0x05,
		(byte)0x24, (byte)0x05, (byte)0x15, (byte)0x06,
		(byte)0x43, (byte)0x04, (byte)0x14, (byte)0x05, // 0x70
		(byte)0x24, (byte)0x05, (byte)0x15, (byte)0x06,
		(byte)0x34, (byte)0x05, (byte)0x15, (byte)0x06,
		(byte)0x25, (byte)0x06, (byte)0x16, (byte)0x07,
		(byte)0x71, (byte)0x02, (byte)0x12, (byte)0x03, // 0x80
		(byte)0x22, (byte)0x03, (byte)0x13, (byte)0x04,
		(byte)0x32, (byte)0x03, (byte)0x13, (byte)0x04,
		(byte)0x23, (byte)0x04, (byte)0x14, (byte)0x05,
		(byte)0x42, (byte)0x03, (byte)0x13, (byte)0x04, // 0x90
		(byte)0x23, (byte)0x04, (byte)0x14, (byte)0x05,
		(byte)0x33, (byte)0x04, (byte)0x14, (byte)0x05,
		(byte)0x24, (byte)0x05, (byte)0x15, (byte)0x06,
		(byte)0x52, (byte)0x03, (byte)0x13, (byte)0x04, // 0xa0
		(byte)0x23, (byte)0x04, (byte)0x14, (byte)0x05,
		(byte)0x33, (byte)0x04, (byte)0x14, (byte)0x05,
		(byte)0x24, (byte)0x05, (byte)0x15, (byte)0x06,
		(byte)0x43, (byte)0x04, (byte)0x14, (byte)0x05, // 0xb0
		(byte)0x24, (byte)0x05, (byte)0x15, (byte)0x06,
		(byte)0x34, (byte)0x05, (byte)0x15, (byte)0x06,
		(byte)0x25, (byte)0x06, (byte)0x16, (byte)0x07,
		(byte)0x62, (byte)0x03, (byte)0x13, (byte)0x04, // 0xc0
		(byte)0x23, (byte)0x04, (byte)0x14, (byte)0x05,
		(byte)0x33, (byte)0x04, (byte)0x14, (byte)0x05,
		(byte)0x24, (byte)0x05, (byte)0x15, (byte)0x06,
		(byte)0x43, (byte)0x04, (byte)0x14, (byte)0x05, // 0xd0
		(byte)0x24, (byte)0x05, (byte)0x15, (byte)0x06,
		(byte)0x34, (byte)0x05, (byte)0x15, (byte)0x06,
		(byte)0x25, (byte)0x06, (byte)0x16, (byte)0x07,
		(byte)0x53, (byte)0x04, (byte)0x14, (byte)0x05, // 0xe0
		(byte)0x24, (byte)0x05, (byte)0x15, (byte)0x06,
		(byte)0x34, (byte)0x05, (byte)0x15, (byte)0x06,
		(byte)0x25, (byte)0x06, (byte)0x16, (byte)0x07,
		(byte)0x44, (byte)0x05, (byte)0x15, (byte)0x06, // 0xf0
		(byte)0x25, (byte)0x06, (byte)0x16, (byte)0x07,
		(byte)0x35, (byte)0x06, (byte)0x16, (byte)0x07,
		(byte)0x26, (byte)0x07, (byte)0x17, (byte)0x08
	};
}