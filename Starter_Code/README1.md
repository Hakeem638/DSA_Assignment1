# GhanaServiceSimulator

A Java console-based queue management simulator built for **DCIT 308: Data Structures and Algorithms II** at the University of Ghana, Legon. The simulator models a hospital triage system at the UG Clinic, demonstrating the practical application of four core data structures: a Circular Queue, a Priority Queue, a Deque, and a Stack.

---

## Table of Contents

- [Project Overview](#project-overview)
- [Ghanaian Context](#ghanaian-context)
- [Data Structures Used](#data-structures-used)
- [Personalised Parameters](#personalised-parameters)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [How to Compile and Run](#how-to-compile-and-run)
- [Understanding the Output](#understanding-the-output)
- [CSV Dataset Format](#csv-dataset-format)
- [Routing Logic](#routing-logic)
- [Class Descriptions](#class-descriptions)
- [Author](#author)

---

## Project Overview

The `GhanaServiceSimulator` reads a CSV file of patient requests and routes each one into the appropriate data structure based on urgency level and documentation status. It then serves patients in priority order across a fixed number of service steps and prints a final report summarising the simulation results.

Key features:
- Triage-based priority serving — emergencies are always served first
- Correction lane for patients with incomplete documentation
- Undo support — the last serve action can be reversed
- Overflow tracking — patients turned away when structures are full
- Detailed final report with counters and average service time

---

## Ghanaian Context

The simulation is set at the **University of Ghana (UG) Clinic** on the Legon campus. The clinic serves students, staff, and faculty and operates a triage system where patients are attended to based on severity rather than arrival order.

Three patient lanes are modelled:

| Lane | Description |
|---|---|
| **Normal Queue** | Standard patients with minor to moderate complaints |
| **Urgent Queue** | Emergencies (urgency level ≥ 4) — chest pain, seizures, difficulty breathing |
| **Correction Lane** | Patients with incomplete documentation — missing health forms, insurance cards, or referral letters |

Patients in the correction lane who have resolved their documents can rejoin from the **front** of the lane rather than the back, reflecting fair real-world practice.

---

## Data Structures Used

| Structure | Implementation | Role |
|---|---|---|
| **Circular Queue** | Custom array-based (`CircularQueue.java`) | Normal patient waiting line |
| **Priority Queue** | Java's built-in `PriorityQueue` with custom comparator | Urgent patient queue — higher score served first |
| **Deque** | Custom array-based (`CorrectionDeque.java`) | Correction lane — add/remove from both ends |
| **Stack** | Custom array-based (`ActionStack.java`) | Action history for undo support |

All custom structures use **modulo arithmetic** to wrap pointers circularly within a fixed array, achieving O(1) time for all core operations.

---

## Personalised Parameters

Parameters are derived from Student ID **------36**:

| Parameter | Derivation | Value |
|---|---|---|
| Normal queue capacity | 5 + last digit (6) | **11** |
| Correction deque capacity | 4 + second-last digit (3) | **7** |
| Urgency bonus | last digit mod 3 = 6 mod 3 | **0** |
| Service steps | 20 + second-last digit (3) | **23** |
| Trace table length | 10 + (last digit mod 5) = 10 + 1 | **11 events** |

---

## Project Structure

```
Starter_Code/
├── src/
│   ├── GhanaServiceSimulator.java   # Entry point — loads CSV, runs simulation
│   ├── GhanaServiceCentre.java      # Core logic — routing, serving, undo, report
│   ├── CircularQueue.java           # Custom circular queue (normal patient line)
│   ├── ActionStack.java             # Custom stack (undo history)
│   ├── CorrectionDeque.java         # Custom deque (correction lane)
│   ├── Request.java                 # Data model — one patient request
│   └── ActionRecord.java            # Data model — one action (ADMIT or SERVE)
└── data/
    └── requests.csv                 # 35 anonymised UG Clinic triage records
```

---

## Prerequisites

- **Java Development Kit (JDK) 8 or higher**
- A terminal or command prompt
- No external libraries or build tools required

To verify Java is installed:
```bash
java -version
javac -version
```

---

## How to Compile and Run

**Step 1 — Navigate to the src folder:**
```bash
cd Starter_Code/src
```

**Step 2 — Compile all Java files:**
```bash
javac *.java
```

**Step 3 — Run the simulator with the CSV dataset:**
```bash
java GhanaServiceSimulator ../data/requests.csv
```

**To clean compiled files and recompile fresh (recommended after any edits):**

On Windows (PowerShell):
```powershell
Remove-Item *.class; javac *.java; java GhanaServiceSimulator ../data/requests.csv
```

On macOS/Linux:
```bash
rm *.class && javac *.java && java GhanaServiceSimulator ../data/requests.csv
```

---

## Understanding the Output

The simulator produces two sections of output:

### Serving Log
```
--- SERVING REQUESTS ---
Step 1: served REQ002[CLINIC_TRIAGE, U=5, T=20]
Step 2: served REQ005[CLINIC_TRIAGE, U=4, T=15]
...
```
Each line shows the step number, request ID, service type, urgency level (U), and estimated minutes (T).

Serving order is always:
1. Urgent queue (highest priority score first)
2. Correction deque (front first)
3. Normal circular queue (FIFO)

### Final Report
```
--- FINAL REPORT ---
Served total: 23
Urgent served: 11
Correction served: 6
Normal served: 6
Overflow count: 7
Remaining urgent: 0
Remaining correction: 0
Remaining normal: 5
Average estimated minutes served: 14.87
```

| Field | Meaning |
|---|---|
| Served total | Total patients served across all 23 steps |
| Urgent / Correction / Normal served | Breakdown by lane |
| Overflow count | Patients turned away because a bounded structure was full |
| Remaining | Patients still waiting after all service steps are complete |
| Average estimated minutes | Total estimated minutes ÷ total served |

---

## CSV Dataset Format

The dataset file (`data/requests.csv`) must have the following header and columns:

```
requestId,arrivalOrder,location,serviceType,urgencyLevel,estimatedMinutes,needsCorrection,notes
```

| Column | Type | Description |
|---|---|---|
| `requestId` | String | Unique anonymised ID e.g. REQ001 |
| `arrivalOrder` | int | Sequential arrival number (1, 2, 3 ...) |
| `location` | String | Service location name |
| `serviceType` | String | Type of service requested |
| `urgencyLevel` | int | 1 = low, 2 = mild, 3 = moderate, 4 = urgent, 5 = emergency |
| `estimatedMinutes` | int | Estimated service time in minutes |
| `needsCorrection` | boolean | true if documentation is incomplete |
| `notes` | String | Brief description of the case |

### Dataset Requirements
- Minimum **35 records**
- At least **8 records** with urgencyLevel ≥ 4
- At least **6 records** with needsCorrection = true
- All records must be fully anonymised — no real names, phone numbers, or ID numbers

### Example rows:
```
REQ001,1,UG Clinic,CLINIC_TRIAGE,2,10,false,Mild headache complaint
REQ002,2,UG Clinic,CLINIC_TRIAGE,5,20,false,Severe chest pain emergency
REQ006,6,UG Clinic,CLINIC_TRIAGE,2,9,true,Missing student health form
```

---

## Routing Logic

When `admitRequest()` is called for each patient:

```
IF needsCorrection == true
    → Add to CorrectionDeque (rear)
    → If full: overflowCount++

ELSE IF urgencyLevel >= 4
    → Add to urgent PriorityQueue
    → Never full (unbounded)

ELSE
    → Add to normal CircularQueue
    → If full: overflowCount++

On success → Push ActionRecord onto ActionStack
```

### Priority Score Formula
Used by the urgent PriorityQueue comparator to rank patients:
```
priority = (urgencyLevel × 10) + urgencyBonus - estimatedMinutes + (100 - arrivalOrder)
```
- `urgencyLevel × 10` — ensures urgency dominates
- `- estimatedMinutes` — quicker cases get a slight boost
- `(100 - arrivalOrder)` — earlier arrivals are rewarded for fairness

---

## Class Descriptions

### `GhanaServiceSimulator.java`
Entry point. Reads personalised parameters, loads the CSV using `loadRequests()`, admits all requests, runs the serving loop for `serviceSteps` iterations, and calls `printReport()`.

### `GhanaServiceCentre.java`
The brain of the simulation. Contains:
- `admitRequest()` — routes each patient to the correct structure
- `serveNextRequest()` — serves the next patient in priority order
- `computePriority()` — calculates the priority score for urgent patients
- `undoLastAction()` — reverses the last SERVE action by re-admitting the patient
- `printReport()` — prints the final summary report

### `CircularQueue.java`
Fixed-capacity circular queue using modulo arithmetic. Used for the normal patient waiting line. Core operations: `enqueue()`, `dequeue()`, `peek()`, `isFull()`, `isEmpty()`.

### `ActionStack.java`
Fixed-capacity stack (LIFO). Used to record every ADMIT and SERVE action for undo support. Core operations: `push()`, `pop()`, `peek()`, `isEmpty()`.

### `CorrectionDeque.java`
Fixed-capacity double-ended queue using modulo arithmetic. Used for the correction lane. Core operations: `addRear()`, `addFront()`, `removeFront()`, `removeRear()`, `isFull()`, `isEmpty()`.

### `Request.java`
Immutable data model for a single patient request. Parses CSV rows via `fromCsv()`. Fields: `requestId`, `arrivalOrder`, `location`, `serviceType`, `urgencyLevel`, `estimatedMinutes`, `needsCorrection`, `notes`.

### `ActionRecord.java`
Immutable data model for a recorded action. Fields: `actionType` (one of `ADMIT_NORMAL`, `ADMIT_URGENT`, `ADMIT_CORRECTION`, `SERVE`) and `request`.

---

## Author

| Field | Detail |
|---|---|
| Name | Abdul Hakeem |
| Student ID | 22046736 |
| Programme | BSc Computer Science |
| Level | 300 |
| Course | DCIT 308 — Data Structures and Algorithms II |
| Institution | University of Ghana, Legon |
| Semester | 2025/2026 Academic Year |
