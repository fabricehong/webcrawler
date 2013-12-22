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
    int getAdjustment() {
        return 30000;
    }
}