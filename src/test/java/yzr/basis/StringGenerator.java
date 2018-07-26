/*
 * Copyright (c) 2018 Alex Dubov <oakad@yahoo.com>
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package yzr.basis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.DataProvider;

public class StringGenerator {
	public static Iterator<Object[]> randomStringList(int arity) {
		return new Iterator<Object[]>() {
			@Override
			public boolean hasNext() {
				return remaining > 0;
			}

			@Override
			public Object[] next() {
				var r = ThreadLocalRandom.current();

				var out = new Object[arity];
				for (int a = 0; a < arity; a++)
					out[a] = genString(r);

				remaining--;
				return out;
			}

			private ArrayList<String> genString(
				ThreadLocalRandom r
			) {
				var ls = r.nextInt(1, 16);
				var out = new ArrayList<String>(ls);
				for (; ls > 0; ls--)
					out.add(RandomStringUtils.random(
						r.nextInt(1, 16)
					));

				return out;
			}

			private int remaining = 10;
		};
	}

	@DataProvider(name = "randomStringList_1")
	public static Iterator<Object[]> randomStringList_1() {
		return randomStringList(1);
	}

	@DataProvider(name = "randomStringList_2")
	public static Iterator<Object[]> randomStringList_2() {
		return randomStringList(2);
	}

	@DataProvider(name = "randomStringList_3")
	public static Iterator<Object[]> randomStringList_3() {
		return randomStringList(3);
	}
}
