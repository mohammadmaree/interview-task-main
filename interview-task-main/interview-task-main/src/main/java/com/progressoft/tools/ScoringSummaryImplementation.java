package com.progressoft.tools;

import java.math.BigDecimal;

public class ScoringSummaryImplementation implements ScoringSummary {

    private BigDecimal mean;
    private BigDecimal variance;
    private BigDecimal standardDeviation;
    private BigDecimal median;
    private BigDecimal min;
    private BigDecimal max;

    ScoringSummaryImplementation(BigDecimal mean, BigDecimal variance, BigDecimal standardDeviation, BigDecimal median, BigDecimal min, BigDecimal max){

        this.mean = mean;
        this.variance = variance;
        this.standardDeviation = standardDeviation;
        this.median = median;
        this.min = min;
        this.max = max;
    }

    @Override
    public BigDecimal mean() {
        return mean;
    }

    @Override
    public BigDecimal variance() {
        return variance;
    }

    @Override
    public BigDecimal standardDeviation() {
        return standardDeviation;
    }

    @Override
    public BigDecimal median() {
        return median;
    }

    @Override
    public BigDecimal min() {
        return min;
    }

    @Override
    public BigDecimal max() {
        return max;
    }
}
