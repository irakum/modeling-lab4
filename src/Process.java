public class Process extends Element {
    private int queue, maxqueue, failure;
    private double meanLoad;
    private double meanQueue;

    private int maxTransitions = 3000;
    private int currentTransitions = 0;

    public Process(double delay) {
        super(delay);
        this.queue = 0;
        this.maxqueue = Integer.MAX_VALUE;
        this.meanQueue = 0.0;
    }

    public Process(String nameOfElement, double delay) {
        super(nameOfElement, delay);
        this.queue = 0;
        this.maxqueue = Integer.MAX_VALUE;
        this.meanQueue = 0.0;
    }

    @Override
    public void inAct() {
        if (currentTransitions >= maxTransitions) {
            failure++;
            return;
        }
        currentTransitions++;

        if (getState() == 0) {
            setState(1);
            setTnext(super.getTcurr() + super.getDelay());
        } else {
            if (getQueue() < getMaxqueue()) {
                setQueue(getQueue() + 1);
            } else {
                failure++;
            }
        }
    }

    @Override
    public void outAct() {
        super.outAct();
        setState(0);

        if (queue > 0) {
            queue--;
            setState(1);
            super.setTnext(super.getTcurr() + super.getDelay());
        } else {
            super.setTnext(Double.MAX_VALUE);
        }

        if (!super.nextElements.isEmpty()) {
            Element next = selectNextElement();
          //  System.out.println(getName() + " sends request to " + next.getName());
            if (next != null) {
                next.inAct();
            }
        }
    }

    public int getFailure() {
        return failure;
    }
    public int getQueue() {
        return queue;
    }
    public void setQueue(int queue) {
        this.queue = queue;
    }
    public int getMaxqueue() {
        return maxqueue;
    }
    public void setMaxqueue(int maxqueue) {
        this.maxqueue = maxqueue;
    }
    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("failure = " + this.getFailure());
    }
    @Override
    public void doStatistics(double delta) {
        meanQueue = getMeanQueue() + queue * delta;
    }
    public double getMeanQueue() {
        return meanQueue;
    }
    public double getMeanLoad() {
        return this.meanLoad;
    }
}
