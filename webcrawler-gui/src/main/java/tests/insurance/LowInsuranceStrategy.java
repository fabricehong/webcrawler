package tests.insurance;

public class LowInsuranceStrategy extends InsuranceStrategy {

    public LowInsuranceStrategy() {
    }

    double lowInsurancePrice(double income) {
        return income * 0.365;
    }

    @Override
    public int getConstant() {
        return 0;
    }

    @Override
    public double getFactor() {
        return 0.365;
    }

    @Override
    protected int getAdjustment() {
        return 0;
    }
}