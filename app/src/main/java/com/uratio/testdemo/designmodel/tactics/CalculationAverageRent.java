package com.uratio.testdemo.designmodel.tactics;

/**
 * @author lang
 * @data 2021/3/2
 */
public class CalculationAverageRent {
    private Calculation calculation;

    public void setCalculation(Calculation calculation) {
        this.calculation = calculation;
    }

    public int getAverageRent() {
        return calculation.calculationAverageRent();
    }
}
