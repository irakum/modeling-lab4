public class Dispose extends Element {

    public Dispose() {
        super();
    }

    @Override
    public void inAct() {
        super.outAct();
    }

    @Override
    public void outAct() {

    }

    @Override
    public void printResult() {
        System.out.println(getName() + " quantity = " + getQuantity());
    }
}
