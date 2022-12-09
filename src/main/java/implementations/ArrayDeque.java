package implementations;

import interfaces.Deque;

import java.util.Arrays;
import java.util.Iterator;

public class ArrayDeque<E> implements Deque<E> {

    private final int INITIAL_CAPACITY = 7;
    private Object[] elements;
    private int capacity;
    private int size;
    private int head;
    private int tail;

    public ArrayDeque() {
        elements = new Object[INITIAL_CAPACITY];
        this.capacity = INITIAL_CAPACITY;
        int middle = INITIAL_CAPACITY / 2;
        this.head = this.tail = middle;
        this.size = 0;
    }

    @Override
    public void add(E element) {
        addLast(element);
    }

    @Override
    public void offer(E element) {
        add(element);
    }

    @Override
    public void addFirst(E element) {
        if (isEmpty()) {
            addLast(element);
        } else {
            if (this.head == 0) {
                this.elements = grow();
            }
            this.elements[--this.head] = element;
            this.size++;
        }
    }

    @Override
    public void addLast(E element) {
        if (isEmpty()) {
            this.elements[this.tail] = element;
        } else {
            if (this.tail == this.elements.length - 1) {
                this.elements = grow();
            }
            this.elements[++this.tail] = element;
        }
        this.size++;
    }

    @Override
    public void push(E element) {
        addFirst(element);
    }

    @Override
    public void insert(int index, E element) {
        int realIndex = this.head + index;
        this.isValidIndex(realIndex);

        if (realIndex - this.head < this.tail - realIndex) {
            insertAndShiftLeft(realIndex - 1, element);
        } else {
            insertAndSiftRight(realIndex, element);
        }
    }

    @Override
    public void set(int index, E element) {
        int realIndex = this.head + index;
        isValidIndex(realIndex);
        this.elements[realIndex] = element;
    }

    @Override
    public E peek() {
        if (!isEmpty()) {
            return getAt(this.head);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private E getAt(int index) {
        return (E) this.elements[index];
    }

    @Override
    public E poll() {
        return removeFirst();
    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public E get(int index) {
        int realIndex = this.head + index;
        isValidIndex(realIndex);
        return this.getAt(realIndex);
    }

    @Override
    public E get(Object object) {
        if(isEmpty()) {
            return null;
        }
        for (int i = this.head; i <= this.tail; i++) {
            if (this.elements[i].equals(object)) {
                return this.getAt(i);
            }
        }
        return null;
    }

    @Override
    public E remove(int index) {
        int realIndex = this.head + index;
        isValidIndex(realIndex);
        return this.getAt(realIndex);
    }

    @Override
    public E remove(Object object) {
        if(isEmpty()) {
            return null;
        }
        for (int i = this.head; i <= this.tail; i++) {
            if (this.elements[i].equals(object)) {
               E element = this.getAt(i);
               this.elements[i] = null;

                for (int j = i; j < this.tail; j++) {
                    this.elements[j] = this.elements[j + 1];
                }
                this.removeLast();

               return element;
            }
        }
        return null;
    }

    @Override
    public E removeFirst() {
        if (!isEmpty()) {
            E element = this.getAt(this.head);
            this.elements[this.head] = null;
            this.head++;
            this.size--;
            return element;
        }
        return null;
    }

    @Override
    public E removeLast() {
        if (!isEmpty()) {
            E element = this.getAt(this.tail);
            this.elements[this.tail--] = null;
            this.size--;
            return element;
        }
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int capacity() {
        return this.elements.length;
    }

    @Override
    public void trimToSize() {
        Object[] newElements = new Object[this.size];
        int index = 0;
        for (int i = this.head; i <= this.tail; i++) {
            newElements[index++] = this.elements[i];
        }
        this.elements = newElements;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = head;

            @Override
            public boolean hasNext() {
                return this.index <= tail;
            }

            @Override
            public E next() {
                return getAt(this.index++);
            }
        };
    }

    private void ensureCapacity() {
        if (this.size == this.capacity) {
            resize();
        }
    }

    private void resize() {
        this.capacity *= 2;
        Object[] tmp = new Object[this.capacity];
        for (int i = 0; i < this.size; i++) {
            tmp[i] = this.elements[i];
        }
        this.elements = tmp;
    }

    private void shiftRight(int index) {
        for (int i = this.size; i > index; i--) {
            this.elements[i] = this.elements[i - 1];
        }
    }

    private void shiftLeft(int index) {
        for (int i = index; i < this.size - 1; i++) {
            this.elements[i] = this.elements[i + 1];
        }
        this.elements[this.size - 1] = null;
    }

    private void isValidIndex(int index) {
        if (index < this.head || index > this.tail) {
            throw new IndexOutOfBoundsException("Index of of bounds: " + (index - this.head));
        }
    }

    private Object[] grow() {
        int newCapacity = this.elements.length * 2 + 1;

        Object[] newElements = new Object[newCapacity];

        int middle = newCapacity / 2;

        int begin = middle - this.size / 2;

        int index = this.head;

        for (int i = begin; index <= this.tail; i++) {
            newElements[i] = this.elements[index++];
        }
        this.head = begin;
        this.tail = this.head + this.size - 1;

        return newElements;
    }

    private void insertAndSiftRight(int index, E element) {
        E lastElement = this.getAt(this.tail);
        for (int i = this.tail; i > index; i--) {
            this.elements[i] = this.elements[i - 1];
        }
        this.elements[index] = element;
        this.addLast(lastElement);
    }

    private void insertAndShiftLeft(int index, E element) {
        E firstElement = this.getAt(this.head);
        for (int i = this.head; i <= index; i++) {
            this.elements[i] = this.elements[i + 1];
        }
        this.elements[index] = element;
        this.addFirst(firstElement);
    }
}
