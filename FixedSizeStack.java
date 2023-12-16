import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.EmptyStackException;


public class FixedSizeStack<T> implements Iterable<T> {
    private Object[] elements;
    private int size;
    private int capacity;

    public FixedSizeStack(int initialCapacity) {
        this.capacity = initialCapacity;
        this.elements = new Object[initialCapacity];
        this.size = 0;
    }

 

    public int getCapacity() {
        return capacity;
    }

   public boolean isFull(){
   if(size<capacity){
   return false;
   }
   else {
   return true;
   }
   }
    public void push(T element) {
      
 if(! isFull()){
        elements[size++] = element;
      }
    //  System.out.println(elements);  
    }

    public void resize(int newCapacity) {
        if (newCapacity < 0) {
            throw new IllegalArgumentException("New capacity must be greater than or equal to the current size.");
        }
        Object[] newElements = new Object[newCapacity];
        System.arraycopy(elements, 0, newElements, 0, size);
        elements = newElements;
        capacity = newCapacity;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    public void pop() {
     
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        else {
        @SuppressWarnings("unchecked")
        T element = (T) elements[--size];
        elements[size] = null; // Clear the reference
        //      System.out.println(size);  
        }
      
   
    
}
    public T peek() {
        
        if (isEmpty()) {
            throw new EmptyStackException();
        }
    
        @SuppressWarnings("unchecked")
        T element = (T) elements[size - 1];
        System.out.println(element);
     
    
   return element;
    
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            result.append(elements[i]);
            if (i < size - 1) {
                result.append(", ");
            }
        }
        result.append("]");
        return result.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int currentIndex = size - 1;

            @Override
            public boolean hasNext() {
                return currentIndex >= 0;
            }

            @Override
            @SuppressWarnings("unchecked")
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (T) elements[currentIndex--];
            }
        };
    }



}

