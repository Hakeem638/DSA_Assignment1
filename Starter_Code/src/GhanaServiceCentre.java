import java.awt.Desktop;
import java.util.PriorityQueue;

public class GhanaServiceCentre {
    private CircularQueue normalQueue;
    private CorrectionDeque correctionDeque;
    private PriorityQueue<Request> urgentQueue;
    private ActionStack actions;

    private int overflowCount = 0;
    private int servedCount = 0;
    private int urgentServed = 0;
    private int normalServed = 0;
    private int correctionServed = 0;
    private int totalEstimatedMinutesServed = 0;
    private int urgencyBonus;

    public GhanaServiceCentre(int normalCapacity, int correctionCapacity, int urgencyBonus) {
        this.normalQueue = new CircularQueue(normalCapacity);
        this.correctionDeque = new CorrectionDeque(correctionCapacity);
        this.urgencyBonus = urgencyBonus;
        this.actions = new ActionStack(200);

        this.urgentQueue = new PriorityQueue<>((a, b) -> {
            // Higher priority score should be served first.
            return Integer.compare(computePriority(b), computePriority(a));
        });
    }

    public int computePriority(Request request) {
        // TODO 19: Design a priority score.
        // Required minimum: urgencyLevel must matter most.
        // Suggested formula:
        // (urgencyLevel * 10) + urgencyBonus - estimatedMinutes + small arrival fairness adjustment
        return (request.urgencyLevel * 10) + this.urgencyBonus - request.estimatedMinutes + (100 - request.arrivalOrder);

    }

    public void admitRequest(Request request) {
        // TODO 20: Route request into the correct structure.
        // Rule 1: If needsCorrection is true, add to correctionDeque.
        // Rule 2: Else if urgencyLevel >= 4, add to urgentQueue.
        // Rule 3: Else add to normalQueue.
        // Rule 4: If any bounded structure is full, increase overflowCount.
        // Rule 5: Push an ActionRecord for successful admissions.

        if (request.needsCorrection) {
            if (correctionDeque.isFull()) {
                overflowCount++;
            } else {
                correctionDeque.addRear(request);
                actions.push(new ActionRecord("ADMIT_CORRECTION", request));
            }
        } else if (request.urgencyLevel >= 4) {
            urgentQueue.add(request);
            actions.push(new ActionRecord("ADMIT_URGENT", request));
        } else {
            if (normalQueue.isFull()) {
                overflowCount++;
            } else {
                normalQueue.enqueue(request);
                actions.push(new ActionRecord("ADMIT_NORMAL", request));
            }
        }
    }

    public Request serveNextRequest() {
        // TODO 21: Serve urgentQueue first, then correctionDeque, then normalQueue.
        // TODO 22: Update served counters and totalEstimatedMinutesServed.
        // TODO 23: Push a SERVE action onto the stack.

        Request served = null;
        if (!urgentQueue.isEmpty()) {
            urgentQueue.poll();
            // update urgentServed and servedCount
            urgentServed++;
            servedCount++;
            // update totalEstimatedMinutesServed
            totalEstimatedMinutesServed++;

        } else if (!correctionDeque.isEmpty()) {
            // remove from correctionDeque
            correctionDeque.removeFront();
            // update correctionServed and servedCount
            correctionServed++;
            servedCount++;
            // update totalEstimatedMinutesServed
            totalEstimatedMinutesServed++;

        } else if (!normalQueue.isEmpty()) {
            // remove from normalQueue
            normalQueue.dequeue();
            // update normalServed and servedCount
            normalServed++;
            servedCount++;
            // update totalEstimatedMinutesServed
            totalEstimatedMinutesServed++;
        }

        if (served != null) {
            // push SERVE ActionRecord onto stack
            actions.push(new ActionRecord("SERVE_ACTION", request));
        }

        return served;
    }

    public void undoLastAction() {
        // TODO 24: Pop the last action and reverse it where possible.
        // Minimum acceptable behaviour:
        // - If the last action was SERVE, re-admit the request using admitRequest(request).
        // - If the last action was an admission, explain in your report how you handled or limited undo.
        // Stronger solution: remove the exact request from the structure it entered.
    }

    public void printReport() {
        System.out.println("--- FINAL REPORT ---");
        System.out.println("Served total: " + servedCount);
        System.out.println("Urgent served: " + urgentServed);
        System.out.println("Correction served: " + correctionServed);
        System.out.println("Normal served: " + normalServed);
        System.out.println("Overflow count: " + overflowCount);
        System.out.println("Remaining urgent: " + urgentQueue.size());
        System.out.println("Remaining correction: " + correctionDeque.size());
        System.out.println("Remaining normal: " + normalQueue.size());
        double avg = servedCount == 0 ? 0.0 : (double) totalEstimatedMinutesServed / servedCount;
        System.out.printf("Average estimated minutes served: %.2f%n", avg);
    }
}
