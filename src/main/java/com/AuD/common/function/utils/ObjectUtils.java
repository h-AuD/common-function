package com.AuD.common.function.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 *
 */
public class ObjectUtils {


    /**
     * Determine whether the given object is empty.
     * <p>This method supports the following object types.
     * <ul>
     * <li>{@code Optional}: considered empty if {@link Optional#empty()}</li>
     * <li>{@code Array}: considered empty if its length is zero</li>
     * <li>{@link CharSequence}: considered empty if its length is zero</li>
     * <li>{@link Collection}: delegates to {@link Collection#isEmpty()}</li>
     * <li>{@link Map}: delegates to {@link Map#isEmpty()}</li>
     * </ul>
     * <p>If the given object is non-null and not one of the aforementioned
     * supported types, this method returns {@code false}.
     * @param obj the object to check
     * @return {@code true} if the object is {@code null} or <em>empty</em>
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }

        if (obj instanceof Optional) {
            return !((Optional<?>) obj).isPresent();
        }
        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        }
        if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }
        if (obj instanceof Collection) {
            return ((Collection<?>) obj).isEmpty();
        }
        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).isEmpty();
        }

        // else
        return false;
    }

}
