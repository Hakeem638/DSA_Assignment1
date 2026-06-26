public class ActionStack {
    private ActionRecord[] data;
    private int top;

    public ActionStack(int capacity) {
        data = new ActionRecord[capacity];
        top = -1;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean push(ActionRecord action) {

        if (top == data.length - 1) return false; // checks if the stack is not full.

        data[++top] = action; // pre-increments top before the insert, if top was 2, it becomes 3 then the action is inserted.
        
        return true;
    }

    public ActionRecord pop() {
        if (isEmpty()) return null;

        ActionRecord item = data[top];
        data[top] = null;
        top --;
        
        return item;
    }

    public ActionRecord peek() {
       if (isEmpty()) return null;

       return data[top];
    }

    public int size() {
        return top + 1;
    }
}
