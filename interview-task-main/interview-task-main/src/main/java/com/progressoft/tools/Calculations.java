package com.progressoft.tools;

import java.math.BigDecimal;
import java.util.List;
import java.util.Collections;
import static java.math.RoundingMode.HALF_EVEN;
import java.math.RoundingMode;
public class Calculations{

    public BigDecimal calculateMean(List<BigDecimal> listData) {
        BigDecimal sum = BigDecimal.valueOf(0);
        if (listData.isEmpty())
            throw new IllegalArgumentException();  //throw new ArithmeticException();
        for(int i=0;i<listData.size();i++)
        {
            sum = sum.add(listData.get(i));
        }
        return sum.divide(BigDecimal.valueOf(listData.size()), HALF_EVEN).setScale(2);
    }

    public BigDecimal calculateVariance(List<BigDecimal> listData) {
        if (listData.isEmpty())
            throw new IllegalArgumentException();
        BigDecimal mean = calculateMean(listData);

        BigDecimal sum = BigDecimal.valueOf(0);

        for(int i=0;i<listData.size();i++)
        {
            sum = sum.add(listData.get(i).subtract(mean).pow(2));
        }
        BigDecimal result = sum.divide(new BigDecimal(listData.size()), HALF_EVEN);
        return result.setScale(2);


    }

    public BigDecimal calculateStandardDeviation(List<BigDecimal> listData) {
        if (listData.isEmpty())
            throw new IllegalArgumentException();
        BigDecimal variance = calculateVariance(listData);

        BigDecimal result = new BigDecimal(Math.sqrt(variance.doubleValue()));

            return result.setScale(2,HALF_EVEN);
    }

    public BigDecimal calculateMedian(List<BigDecimal> listData) {

        if (listData.isEmpty())
            throw new IllegalArgumentException();
        int n = listData.size();
        Collections.sort(listData);
        int mid = (n - 1) / 2;
        if (n % 2 == 0) {
            BigDecimal result=listData.get(mid).add(listData.get(mid + 1));
            BigDecimal divide=new BigDecimal(2);
            return result.divide(divide).setScale(2, HALF_EVEN);
        }
        return listData.get(mid).setScale(2, HALF_EVEN);

    }

    public BigDecimal calculateMin(List<BigDecimal> listData) {
        if (listData.isEmpty())
            throw new IllegalArgumentException();
        BigDecimal min = listData.get(0);
        for (int i = 1; i < listData.size(); i++) {
            min = listData.get(i).min(min);
        }
        return min.setScale(2, HALF_EVEN);
    }

    public BigDecimal calculateMax(List<BigDecimal> listData) {
        if (listData.isEmpty())
            throw new IllegalArgumentException();
        BigDecimal max = listData.get(0);
        for (int i = 1; i < listData.size(); i++) {
            max = listData.get(i).max(max);
        }
        return max.setScale(2, HALF_EVEN);
    }

    public BigDecimal calculateZscore(BigDecimal el, BigDecimal mean, BigDecimal sd) {
        BigDecimal difference = new BigDecimal(String.valueOf(el.subtract(mean)));
        return difference.divide(sd, 2, HALF_EVEN);
    }

    public BigDecimal calculateMinMaxScale(BigDecimal el, BigDecimal min, BigDecimal max) {
        return el.subtract(min).divide((max.subtract(min)), 2, BigDecimal.ROUND_HALF_EVEN);
    }
}
