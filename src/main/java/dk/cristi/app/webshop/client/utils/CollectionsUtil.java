package dk.cristi.app.webshop.client.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public final class CollectionsUtil {
    public static <E> List<E> toList(Iterable<E> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
    }
}
