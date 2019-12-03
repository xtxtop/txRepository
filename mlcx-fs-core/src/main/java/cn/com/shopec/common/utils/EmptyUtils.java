package cn.com.shopec.common.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/** 判空相关工具类 */
public final class EmptyUtils {

	private EmptyUtils() {
		throw new UnsupportedOperationException("u can't instantiate me...");
	}

	/**
	 * 判断对象是否为空
	 *
	 * @param obj
	 *            对象
	 * @return {@code true}: 为空<br>
	 *         {@code false}: 不为空
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(final Object obj) {
		if (obj == null) { return true; }
		if (obj instanceof CharSequence && obj.toString().length() == 0) { return true; }
		if (obj.getClass().isArray() && Array.getLength(obj) == 0) { return true; }
		if (obj instanceof Collection && ((Collection) obj).isEmpty()) { return true; }
		if (obj instanceof Map && ((Map) obj).isEmpty()) { return true; }
		return false;
	}

	/**
	 * 判断对象是否非空
	 *
	 * @param obj
	 *            对象
	 * @return {@code true}: 非空<br>
	 *         {@code false}: 空
	 */
	public static boolean isNotEmpty(final Object obj) {
		return !isEmpty(obj);
	}
}
