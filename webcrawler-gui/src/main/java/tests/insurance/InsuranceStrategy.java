package tests.insurance;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 16.04.13
 * Time: 16:19
 * To change this template use File | Settings | File Templates.
 */
public abstract class InsuranceStrategy {
    double computeInsuranceCost(double income) {
        return (income - getAdjustment()) * getFactor() + getConstant();
    }

    abstract int getConstant();

    abstract double getFactor();

    protected abstract int getAdjustment();
}
