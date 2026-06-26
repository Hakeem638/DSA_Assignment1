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
        // TODO 13: Return true when size equals capacity.
        return false;
    }

    public boolean isEmpty() {
        // TODO 14: Return true when size is zero.
        return false;
    }

    public boolean addRear(Request request) {
        // TODO 15: Add a normal correction request to the rear using modulo arithmetic.
        return false;
    }

    public boolean addFront(Request request) {
        // TODO 16: Add a corrected urgent request to the front using modulo arithmetic.
        // Hint: front = (front - 1 + data.length) % data.length
        return false;
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



