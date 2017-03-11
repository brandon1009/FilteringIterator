package com.dis.was.fun.iterator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class FilteringIterator<T> implements Iterator<T> {

	private final Iterator<T> m_iter;

	private final IObjectTest<T> m_filter;

	private T m_nextElem;

	private FilteringIterator(Iterator<T> iter, IObjectTest<T> filter) {
		m_iter = iter;
		m_filter = filter;
		m_nextElem = getNextMatch();
	}

	public static <T> FilteringIterator<T> create(Iterator<T> iter, IObjectTest<T> filter) {
		if (iter == null || filter == null) {
			return null;
		}

		return new FilteringIterator<T>(iter, filter);
	}

	@Override
	public boolean hasNext() {
		return m_nextElem != null;
	}

	private T getNextMatch() {
		while (m_iter.hasNext()) {
			T candidateElem = m_iter.next();

			if (m_filter.test(candidateElem)) {
				return candidateElem;
			}
		}

		return null;
	}

	@Override
	public T next() {
		if (!hasNext()) {
			throw new NoSuchElementException("There are no elements available that pass the filter: " + m_filter.toString());
		}

		T prevMatch = m_nextElem;

		m_nextElem = getNextMatch();

		return prevMatch;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
