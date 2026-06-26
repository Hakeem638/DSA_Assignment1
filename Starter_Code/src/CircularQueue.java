public class CircularQueue {
    private Request[] data;
    private int front;
    private int rear;
    private int size;

    public CircularQueue(int capacity) {
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

    public boolean enqueue(Request request) {
        if (isFull()) {
            return false;
        }

        rear = (rear + 1) % data.length;
        data[rear] = request;
        size++;
        return true;
    }

    public Request dequeue() {
        if (isEmpty()) {
            return null;
        }

        Request item = data[front];
        data[front] = null;
        front = (front + 1) % data.length;
        size--;
        return item;
    }

    public Request peek() {
        if (isEmpty()) {
            return null;
        }
        return data[front];
    }

    public int size() {
        return size;
    }

    public int frontIndex() {
        return front;
    }

    public int rearIndex() {
        return rear;
    }
}
