package tests.insurance;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 16.04.13
 * Time: 16:04
 * To change this template use File | Settings | File Templates.
 */
public class InsuranceCostComputer {


    private final InsuranceStrategy insuranceStrategy;

    public InsuranceCostComputer(InsuranceStrategy insuranceStrategy) {
        this.insuranceStrategy = insuranceStrategy;
    }

    public static void main(String[] args) {
        InsuranceStrategy insuranceStrategy=null;

        insuranceStrategy.computeInsuranceCost(200);

    }

    public void computeInsuranceCost(double income) {
        double result;

        result = insuranceStrategy.computeInsuranceCost(income);
        if (income >= 10000) {
            result = insuranceStrategy.computeInsuranceCost(income);
        } else if (income <=30000) {
            result = insuranceStrategy.computeInsuranceCost(income);
        } else if (income <=60000) {
            result = insuranceStrategy.computeInsuranceCost(income);
        } else {
            result = insuranceStrategy.computeInsuranceCost(income);
        }

        System.out.println(result);

    }

}
