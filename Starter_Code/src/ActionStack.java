public class ActionStack {
    private ActionRecord[] data;
    private int top;

    public ActionStack(int capacity) {
        data = new ActionRecord[capacity];
        top = -1;
    }

    public boolean isEmpty() {
        // TODO 9: Return true when top is -1.
        return false;
    }

    public boolean push(ActionRecord action) {
        // TODO 10: Push action if there is space; return false if stack is full.
        return false;
    }

    public ActionRecord pop() {
        // TODO 11: Remove and return the most recent action; return null if empty.
        return null;
    }

    public ActionRecord peek() {
        // TODO 12: Return the top action without removing it.
        return null;
    }

    public int size() {
        return top + 1;
    }
}
