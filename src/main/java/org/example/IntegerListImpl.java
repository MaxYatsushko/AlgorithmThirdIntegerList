package org.example;

import org.example.exceptions.FullListException;
import org.example.exceptions.InvalidIndexException;
import org.example.exceptions.NullItemException;

import java.util.Arrays;

public class IntegerListImpl implements IntegerList{

    private Integer[] integerList;
    private int size;
    private static final Integer[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    private static final int DEFAULT_CAPACITY = 10;

    public IntegerListImpl() {
        integerList = new Integer[DEFAULT_CAPACITY];
    }

    public IntegerListImpl(int size) {
        integerList = new Integer[size];
    }

    @Override
    public Integer add(Integer item) {
        validateItem(item);
        if (size == integerList.length)
            integerList = grow();

        integerList[size++] = item;
        return item;
    }

    @Override
    public Integer add(int index, Integer item) {
        validateItem(item);
        validateIndex(index);
        if (size == integerList.length)
            integerList = grow();

        if(index == size) {
            integerList[size++] = item;
            return item;
        }

        System.arraycopy(integerList, index, integerList, index + 1, size - index);
        integerList[index] = item;
        size++;

        return item;
    }

    @Override
    public Integer set(int index, Integer item) {
        validateIndex(index);
        validateItem(item);
        integerList[index] = item;

        return item;
    }

    @Override
    public Integer remove(Integer item) {
        validateItem(item);
        int index = indexOf(item);

        return remove(index);
    }

    @Override
    public Integer remove(int index) {
        validateIndex(index);

        Integer item = integerList[index];
        if ((index != size) && (index != size - 1))
            System.arraycopy(integerList, index + 1, integerList, index, size - index);
        else if (index == size - 1)
               System.arraycopy(integerList, 0, integerList, size - 2 , size - 1);
        size--;
        return item;
    }

    @Override
    public boolean contains(Integer item) {
        return contains(integerList, item, this.size);
    }

    @Override
    public int indexOf(Integer item) {
        for (int i = 0; i < size; i++) {
            if(integerList[i].equals(item))
                return i;
        }
        return -1;
    }

    @Override
    public String toString() {
        return "IntegerListImpl{" +
                "integerList=" + Arrays.toString(integerList) +
                ", size=" + size +
                '}';
    }

    @Override
    public int lastIndexOf(Integer item) {
        for (int i = size - 1; i >= 0; i--) {
            if(integerList[i].equals(item))
                return i;
        }
        return -1;
    }

    @Override
    public Integer get(int index) {
        validateIndex(index);
        return integerList[index];
    }

    @Override
    public boolean equals(IntegerList otherList) {
        return Arrays.equals(this.toArray(), otherList.toArray());
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public Integer[] toArray() {
        return Arrays.copyOf(integerList, size);
    }

    private void validateItem(Integer item){
        if(item == null)
            throw new NullItemException("Item is Null ");
    }

    private void validateSize(){
        if(size == integerList.length)
            throw new FullListException("List is full");
    }

    private void validateIndex(int index){
        if(index < 0 || index > size)
            throw new InvalidIndexException("Index is incorrect");
    }

    private static void swapElements(Integer[] arr, int indexA, int indexB) {
        int tmp = arr[indexA];
        arr[indexA] = arr[indexB];
        arr[indexB] = tmp;
    }

    public void sortBubble() {
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1 - i; j++) {
                if (integerList[j] > integerList[j + 1]) {
                    swapElements(integerList, j, j + 1);
                }
            }
        }
    }

    public  void sortSelection() {
        for (int i = 0; i < size - 1; i++) {
            int minElementIndex = i;
            for (int j = i + 1; j < size; j++) {
                if (integerList[j] < integerList[minElementIndex]) {
                    minElementIndex = j;
                }
            }
            swapElements(integerList, i, minElementIndex);
        }
    }

    public void sortInsertion() {
        sortSelection(integerList, this.size);
    }

    private boolean contains(Integer[] arr, int element, int size) {
        sortSelection(arr, size);
        int min = 0;
        int max = size - 1;

        while (min <= max) {
            int mid = (min + max) / 2;

            if (element == arr[mid]) {
                return true;
            }

            if (element < arr[mid]) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        return false;
    }

    private void sortSelection(Integer[] arr, int size) {
        for (int i = 0; i < size - 1; i++) {
            int minElementIndex = i;
            for (int j = i + 1; j < size; j++) {
                if (arr[j] < arr[minElementIndex]) {
                    minElementIndex = j;
                }
            }
            swapElements(arr, i, minElementIndex);
        }
    }

    private Integer[] grow(int minCapacity) {
        return integerList = Arrays.copyOf(integerList,
                newCapacity(minCapacity));
    }

    private Integer[] grow() {
        return grow((int)(size * 1.5));
    }

    private int newCapacity(int minCapacity) {
        int oldCapacity = integerList.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity <= 0) {
            if (integerList == DEFAULTCAPACITY_EMPTY_ELEMENTDATA)
                return Math.max(DEFAULT_CAPACITY, minCapacity);
            if (minCapacity < 0) // overflow
                throw new OutOfMemoryError();
            return minCapacity;
        }
        return (newCapacity - MAX_ARRAY_SIZE <= 0)
                ? newCapacity
                : hugeCapacity(minCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE)
                ? Integer.MAX_VALUE
                : MAX_ARRAY_SIZE;
    }

    public void quickSort(int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(integerList, begin, end);

            quickSort(begin, partitionIndex - 1);
            quickSort(partitionIndex + 1, end);
        }
    }

    private int partition(Integer[] arr, int begin, int end) {
        int pivot = arr[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;

                swapElements(arr, i, j);
            }
        }

        swapElements(arr, i + 1, end);
        return i + 1;
    }
}
