public class CorrectionDeque {
    private Request[] data;
    private int front;
    private int rear;
    private int size;

    public CorrectionDeque(int capacity) {
        data = new Request[capacity];
        front = 0;
        rear = -1;
        size = 0;
    }

    public boolean isFull() {
        return size == data.length;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean addRear(Request request) {
        // TODO 15: Add a normal correction request to the rear using modulo arithmetic.
        return false;
    }

    public boolean addFront(Request request) {
        if (isFull()) return false;
        rear = (rear + 1) % data.length;  // move rear forward, wrap if needed
        data[rear] = request;              // insert at new rear
        size++;
        return true;
    }

    public Request removeFront() {
        // TODO 17: Remove and return the front request.
        return null;
    }

    public Request removeRear() {
        // TODO 18: Remove and return the rear request.
        return null;
    }

    public int size() {
        return size;
    }
}



