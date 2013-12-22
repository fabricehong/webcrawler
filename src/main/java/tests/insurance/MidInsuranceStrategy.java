package tests.insurance;

public class MidInsuranceStrategy extends InsuranceStrategy {

    public MidInsuranceStrategy() {
    }

    @Override
    int getConstant() {
        return 3560;
    }

    @Override
    double getFactor() {
        return 0.2;
    }

    @Override
    int getAdjustment() {
        return 10000;
    }
}