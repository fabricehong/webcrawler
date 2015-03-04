package tests.insurance;

public class HighInsuranceStrategy extends InsuranceStrategy {

    public HighInsuranceStrategy() {
    }

    @Override
    int getConstant() {
        return 5657;
    }

    @Override
    double getFactor() {
        return 0.1;
    }

    @Override
    protected  int getAdjustment() {
        return 30000;
    }
}