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
        if (isFull()) return false;
        rear = (rear + 1) % data.length;    // mpove rear forward, wrap if needed
        data[rear] = request;       // insert at new rear
        size++;
        return true;
    }

    public boolean addFront(Request request) {
        if (isFull()) return false;
        front = (front - 1 + data.length) % data.length;  // step backward
        data[front] = request;
        size++;
        return true;
    }

    public Request removeFront() {
        if (isEmpty()) return null;

        Request item = data[front];   // save the item first
        data[front] = null;  // clear the slot the item is
        front = (front + 1) % data.length;  // then move front forward
        size--;
        return item;
    }

    public Request removeRear() {
        if (isEmpty()) return null;

        Request item = data[rear];
        data[rear] = null;
        rear = (rear - 1 + data.length) % data.length;
        size--;
        return item; 
    }

    public int size() {
        return size;
    }
}



