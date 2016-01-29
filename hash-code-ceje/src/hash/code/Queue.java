package hash.code;

import java.util.Collection;

public interface Queue<E> extends Collection<E> {

	E element();

	boolean offer(E e);

	E peek();

	E poll();

	E remove();

}
