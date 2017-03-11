package com.dis.was.fun.iterator;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class FilteringIteratorTest {

	private final static List<String> s_firstNames1 = Arrays.asList("");

	private final static List<String> s_firstNames2 = Arrays.asList("Brandon");

	private final static List<String> s_firstNames3 = Arrays.asList("", "Brandon", "Sam", "Greg");

	private final static List<String> s_firstNames4 = Arrays.asList("Brandon", null, "Sam", "Greg");

	private final static List<Integer> s_ages1 = Arrays.asList();

	private final static List<Integer> s_ages2 = Arrays.asList(-1);

	private final static List<Integer> s_ages3 = Arrays.asList(5);

	private final static List<Integer> s_ages4 = Arrays.asList(5, -1, 3);

	private final static List<Integer> s_ages5 = Arrays.asList(-1, 5, 3);

	private static final IObjectTest<String> s_nonEmptyFilter = new IObjectTest<String>() {
		@Override
		public boolean test(String s) {
			return s != null && !s.isEmpty();
		}
	};

	private static final IObjectTest<Integer> s_nonNegativeFilter = new IObjectTest<Integer>() {
		@Override
		public boolean test(Integer i) {
			return i != null && i > 0;
		}
	};

	@Test
	public void factoryInstantiationTest() {
		final FilteringIterator<?> filteringIter = FilteringIterator.create(null, null);
		assertNull(filteringIter);
	}

	@Test
	public void nonEmptyFilterTest() {
		assertFilteringIterator(s_nonEmptyFilter, s_firstNames1, Collections.<String> emptyList());
		assertFilteringIterator(s_nonEmptyFilter, s_firstNames2, Arrays.asList("Brandon"));
		assertFilteringIterator(s_nonEmptyFilter, s_firstNames3, Arrays.asList("Brandon", "Sam", "Greg"));
		assertFilteringIterator(s_nonEmptyFilter, s_firstNames4, Arrays.asList("Brandon", "Sam", "Greg"));
	}

	@Test
	public void nonNegativeFilterTest() {
		assertFilteringIterator(s_nonNegativeFilter, s_ages1, Collections.<Integer> emptyList());
		assertFilteringIterator(s_nonNegativeFilter, s_ages2, Collections.<Integer> emptyList());
		assertFilteringIterator(s_nonNegativeFilter, s_ages3, Arrays.asList(5));
		assertFilteringIterator(s_nonNegativeFilter, s_ages4, Arrays.asList(5, 3));
		assertFilteringIterator(s_nonNegativeFilter, s_ages5, Arrays.asList(5, 3));
	}

	private <T> boolean assertFilteringIterator(IObjectTest<T> filter, Collection<T> nonFilteredValues, Collection<T> expectedValues) {
		final FilteringIterator<T> filteringIter = FilteringIterator.create(nonFilteredValues.iterator(), filter);
		final Iterator<T> expectedIter = expectedValues.iterator();

		while (filteringIter.hasNext()) {
			T actual = filteringIter.next();
			T expected = expectedIter.next();

			Assert.assertEquals(actual, expected);
		}

		return true;
	}
}
