package tests.insurance;

public class VeryHighInsuranceStrategy extends InsuranceStrategy {

    public VeryHighInsuranceStrategy() {
    }

    double highInsurancePrice(double income) {
        return (income - 30000) * 0.1 + 5657;
    }

    double midInsurancePrice(double income) {
        return (income - 10000) * 0.2 + 3560;
    }

    double lowInsurancePrice(double income) {
        return income * 0.365;
    }

    @Override
    int getConstant() {
        return 23423;
    }

    @Override
    double getFactor() {
        return 0.02;
    }

    @Override
    protected int getAdjustment() {
        return 60000;
    }
}