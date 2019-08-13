public class Safeguards implements Comparable<Safeguards>{

    private int start;
    private int end;

    Safeguards(int start, int end) {
        this.start = start;
        this.end = end;
    }

    // Sort the data according to the ascending order of the start time
    @Override
    public int compareTo(Safeguards o) {
        return Integer.compare(this.start, o.start);
    }

    int getStart() {
        return start;
    }

    void setStart(int start) {
        this.start = start;
    }

    int getEnd() {
        return end;
    }

    void setEnd(int end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Safeguards{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
