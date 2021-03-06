/*
 * Copyright (c) 2018 Alex Dubov <oakad@yahoo.com>
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */

package temulg.yzr.basis;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

public class NomenTest {
	private String fromUtf8(Nomen n) {
		try (var os = new ByteArrayOutputStream()) {
			var ch = Channels.newChannel(os);
			n.toUtf8Channel(ch, "/");
			return os.toString(StandardCharsets.UTF_8);
		} catch (IOException ex) {
			throw new AssertionError(ex);
		}
	}

	private static void assertEquals(Nomen actual, Nomen expected) {
		Assert.assertEquals((Object)actual, (Object)expected);
	}

	@Test
	public void constructBasic() throws Exception {
		var n0 = Nomen.from();
		Assert.assertEquals(n0.toString(), "");
		Assert.assertEquals(fromUtf8(n0), "");
		Assert.assertEquals(n0.size(), 0);

		var n1 = Nomen.from("abcdefg");
		Assert.assertEquals(n1.toString(), "/abcdefg");
		Assert.assertEquals(fromUtf8(n1), "/abcdefg");
		Assert.assertEquals(n1.size(), 1);

		var n2 = Nomen.from("abcdefgh");
		Assert.assertEquals(n2.toString(), "/abcdefgh");
		Assert.assertEquals(fromUtf8(n2), "/abcdefgh");
		Assert.assertEquals(n2.size(), 1);

		var n3 = Nomen.from("abcdefg", "hijklmn");
		Assert.assertEquals(n3.toString(), "/abcdefg/hijklmn");
		Assert.assertEquals(fromUtf8(n3), "/abcdefg/hijklmn");
		Assert.assertEquals(n3.size(), 2);

		var n4 = Nomen.from("abcdefgh", "i", "j");
		Assert.assertEquals(n4.toString(), "/abcdefgh/i/j");
		Assert.assertEquals(fromUtf8(n4), "/abcdefgh/i/j");
		Assert.assertEquals(n4.size(), 3);
	}

	@Test(
		dataProvider = "randomStringList_1",
		dataProviderClass = StringGenerator.class
	)
	public void construct(List<CharSequence> ss) throws Exception {
		var n = Nomen.from(ss);
		var sb = new StringBuilder();
		ss.forEach(s -> {
			sb.append('/').append(s);
		});
		var s = sb.toString();

		Assert.assertEquals(n.toString(), s);
		Assert.assertEquals(fromUtf8(n), s);
		Assert.assertEquals(n.size(), ss.size());
	}

	@Test(
		dataProvider = "randomStringList_1",
		dataProviderClass = StringGenerator.class
	)
	public void iterable(List<CharSequence> ss) throws Exception {
		var n = Nomen.from(ss);
		var nl0 = new ArrayList<Nomen>();
		var nl1 = new ArrayList<Nomen>();

		ss.forEach(s -> {
			nl0.add(Nomen.from(s));
		});

		n.forEach(nl1::add);

		Assert.assertEquals(nl0, nl1);
		Assert.assertEquals(n, nl0);
		Assert.assertEquals(n, nl1);
	}

	@Test(
		dataProvider = "randomStringList_2",
		dataProviderClass = StringGenerator.class
	)
	public void cat2(
		List<CharSequence> s0, List<CharSequence> s1
	) throws Exception {
		var n0 = Nomen.from(s0);
		var n1 = Nomen.from(s1);
		var n = n0.cat(n1);

		var sb = new StringBuilder();
		s0.forEach(s -> {
			sb.append('/').append(s);
		});
		s1.forEach(s -> {
			sb.append('/').append(s);
		});
		var s = sb.toString();

		Assert.assertEquals(n.toString(), s);
		Assert.assertEquals(n.size(), s0.size() + s1.size());
	}

	@Test(
		dataProvider = "randomStringList_3",
		dataProviderClass = StringGenerator.class
	)
	public void cat3(
		List<CharSequence> s0,
		List<CharSequence> s1,
		List<CharSequence> s2
	) throws Exception {
		var n0 = Nomen.from(s0);
		var n1 = Nomen.from(s1);
		var n2 = Nomen.from(s2);
		var n = n0.cat(n1, n2);

		var sb = new StringBuilder();
		s0.forEach(s -> {
			sb.append('/').append(s);
		});
		s1.forEach(s -> {
			sb.append('/').append(s);
		});
		s2.forEach(s -> {
			sb.append('/').append(s);
		});

		Assert.assertEquals(n.toString(), sb.toString());
		Assert.assertEquals(
			n.size(), s0.size() + s1.size() + s2.size()
		);
	}

	@Test(
		dataProvider = "randomStringList_2",
		dataProviderClass = StringGenerator.class
	)
	public void prefix1(
		List<CharSequence> s0,
		List<CharSequence> s1
	) throws Exception {
		var n0 = Nomen.from(s0);
		var n1 = Nomen.from(s1);

		var n01 = n0.cat(n1);

		var nc0 = n01.commonPrefix(n0);
		var nc1 = n0.commonPrefix(n01);

		var sb = new StringBuilder();
		s0.forEach(s -> {
			sb.append('/').append(s);
		});

		// Compare as Object
		assertEquals(nc0, n0);
		assertEquals(nc1, n0);

		// Compare as Iterable
		Assert.assertEquals(nc0, n0);
		Assert.assertEquals(nc1, n0);
		Assert.assertEquals(nc0.toString(), sb.toString());
		Assert.assertEquals(nc1.toString(), sb.toString());
	}

	@Test(
		dataProvider = "randomStringList_3",
		dataProviderClass = StringGenerator.class
	)
	public void prefix2(
		List<CharSequence> s0,
		List<CharSequence> s1,
		List<CharSequence> s2
	) throws Exception {
		var n0 = Nomen.from(s0);
		var n1 = Nomen.from(s1);
		var n2 = Nomen.from(s2);
		var n01 = n0.cat(n1);
		var n02 = n0.cat(n2);

		var common = n0.cat(n1.commonPrefix(n2));
		var nc1 = n01.commonPrefix(n02);
		var nc2 = n02.commonPrefix(n01);

		var sb = new StringBuilder();
		s0.forEach(s -> {
			sb.append('/').append(s);
		});

		int sz = Math.min(s1.size(), s2.size());
		for (int pos = 0; pos < sz; pos++) {
			if (s1.get(pos).equals(s2.get(pos)))
				sb.append('/').append(s1.get(pos));
			else
				break;
		}

		// Compare as Object
		assertEquals(nc1, common);
		assertEquals(nc2, common);

		// Compare as Iterable
		Assert.assertEquals(nc1, common);
		Assert.assertEquals(nc2, common);
		Assert.assertEquals(nc1.toString(), sb.toString());
		Assert.assertEquals(nc2.toString(), sb.toString());
	}

	@Test(
		dataProvider = "randomStringList_2",
		dataProviderClass = StringGenerator.class
	)
	public void rel2(
		List<CharSequence> s0, List<CharSequence> s1
	) throws Exception {
		var n0 = Nomen.from(s0);
		var n1 = Nomen.from(s1);
		var n = n0.cat(n1);

		var rel = n0.relativize(n);

		Assert.assertTrue(n0.isPrefixOf(n));

		// Compare as Object
		assertEquals(rel, n1);

		// Compare as Iterable
		Assert.assertEquals(rel, n1);
	}

	@Test(
		dataProvider = "randomStringList_1_pos_1",
		dataProviderClass = StringGenerator.class
	)
	public void sub1(List<CharSequence> ss, int first) throws Exception {
		var n = Nomen.from(ss);
		var n0 = n.subNomen(first);

		var s1 = ss.subList(first, ss.size());
		var n1 = Nomen.from(s1);

		// Compare as Object
		assertEquals(n0, n1);

		// Compare as Iterable
		Assert.assertEquals(n0, n1);
	}

	@Test(
		dataProvider = "randomStringList_1_pos_1",
		dataProviderClass = StringGenerator.class
	)
	public void get1(List<CharSequence> ss, int first) throws Exception {
		if (ss.isEmpty())
			return;

		var n = Nomen.from(ss);
		var n0 = n.get(first);

		var s1 = ss.get(first);
		var n1 = Nomen.from(s1);

		// Compare as Object
		assertEquals(n0, n1);

		// Compare as Iterable
		Assert.assertEquals(n0, n1);
	}

	@Test(
		dataProvider = "randomStringList_1_pos_2",
		dataProviderClass = StringGenerator.class
	)
	public void sub2(
		List<CharSequence> ss, int first, int last
	) throws Exception {
		if (ss.isEmpty() || (first == last))
			return;

		var n = Nomen.from(ss);
		var n0 = n.subNomen(first, last);

		var s1 = ss.subList(first, last);
		var n1 = Nomen.from(s1);

		// Compare as Object
		assertEquals(n0, n1);

		// Compare as Iterable
		Assert.assertEquals(n0, n1);
	}
}
