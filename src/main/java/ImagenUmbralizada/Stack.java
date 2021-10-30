package imagenUmbralizada;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author Jorge
 */
public class Stack<T> {
    
    private LinkedList<T> stack;
    private final int maxSize;

    public Stack(int maxSize) {
        this.maxSize = maxSize;
        stack = new LinkedList<>();
    }

    public boolean add(T value){
        if (stack.size() == maxSize){
            stack.remove();
        }
        return stack.add(value);
    }
        
    public ListIterator<T> listIterator(){
        return stack.listIterator(stack.size());
    }
}
