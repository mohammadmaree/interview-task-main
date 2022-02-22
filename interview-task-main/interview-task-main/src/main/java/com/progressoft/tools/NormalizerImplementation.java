package com.progressoft.tools;

import java.nio.file.Path;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NormalizerImplementation implements Normalizer{

    private final FileRead fileRead;
    private final Calculations calculator;
    private final FileWrite fileWrite;

    public NormalizerImplementation(FileRead fileRead, Calculations calculator, FileWrite fileWrite) {
        this.fileRead = fileRead;
        this.calculator = calculator;
        this.fileWrite = fileWrite;
    }

    @Override
    public ScoringSummary zscore(Path csvPath, Path destPath, String colToStandardize) {

        if (csvPath == null || !csvPath.toFile().exists())
            throw new IllegalArgumentException("source file not found");
        if (destPath == null)
            throw new IllegalArgumentException("dest path not found");
        if (colToStandardize == null)
            throw new IllegalArgumentException("colToStandardize is null");
        List<String[]> parseList = fileRead.parse(csvPath);

        if (!Arrays.asList(parseList.get(0)).contains(colToStandardize)) {
            throw new IllegalArgumentException("column " + colToStandardize + " not found");
        }
        int index = Arrays.asList(parseList.get(0)).indexOf(colToStandardize);

        List<BigDecimal> dataCol = new ArrayList<>();
        for (int i = 1; i < parseList.size(); i++) {
            dataCol.add(new BigDecimal(parseList.get(i)[index]));
        }

        ScoringSummary scoringSummary = generateScoringSummary(dataCol);

        List<BigDecimal> zScores = new ArrayList<>();
        List<String> records = new ArrayList<>(Arrays.asList(parseList.get(0)));
        String element = colToStandardize + "_z";

        records.add(index + 1, element);
        fileWrite.exportRecord(records, destPath);

        for (BigDecimal bigDecimal : dataCol) {
            zScores.add(calculator.calculateZscore(bigDecimal, scoringSummary.mean(), scoringSummary.standardDeviation()));
        }

        for (int i = 1; i < parseList.size(); i++) {
            List<String> list = new ArrayList<>(Arrays.asList(parseList.get(i)));
            list.add(index + 1, String.valueOf(zScores.get(i - 1)));
            fileWrite.exportRecord(list, destPath);
        }
        return scoringSummary;
    }

    @Override
    public ScoringSummary minMaxScaling(Path csvPath, Path destPath, String colToNormalize) {

        if(csvPath == null || !csvPath.toFile().exists())
            throw new IllegalArgumentException("file not found");
        if (destPath == null)
            throw new IllegalArgumentException("path not found");
        if (colToNormalize == null)
            throw new IllegalArgumentException("colToNormalize is null");
        List<String[]> parseList = fileRead.parse(csvPath);

        if (!Arrays.asList(parseList.get(0)).contains(colToNormalize)) {
            throw new IllegalArgumentException("column " + colToNormalize + " not found");
        }
        int index = Arrays.asList(parseList.get(0)).indexOf(colToNormalize);

        List<BigDecimal> dataCol = new ArrayList<>();
        for (int i = 1; i < parseList.size(); i++) {
            dataCol.add(new BigDecimal(parseList.get(i)[index]));
        }

        ScoringSummary scoringSummary = generateScoringSummary(dataCol);

        List<BigDecimal> minMax = new ArrayList<>();
        List<String> records = new ArrayList<>(Arrays.asList(parseList.get(0)));
        String element = colToNormalize + "_mm";
        records.add(index + 1, element);
        fileWrite.exportRecord(records, destPath);

        for (BigDecimal bigDecimal : dataCol) {
            minMax.add(calculator.calculateMinMaxScale(bigDecimal, scoringSummary.min(), scoringSummary.max()));
        }

        for (int i = 1; i < parseList.size(); i++) {
            List<String> list = new ArrayList<>(Arrays.asList(parseList.get(i)));
            list.add(index + 1, String.valueOf(minMax.get(i - 1)));
            fileWrite.exportRecord(list, destPath);
        }

        return scoringSummary;
    }

    private ScoringSummary generateScoringSummary(List<BigDecimal> dataCol) {
        BigDecimal mean = calculator.calculateMean(dataCol);
        BigDecimal min = calculator.calculateMin(dataCol);
        BigDecimal max = calculator.calculateMax(dataCol);
        BigDecimal standardDeviation = calculator.calculateStandardDeviation(dataCol);
        BigDecimal median = calculator.calculateMedian(dataCol);
        BigDecimal variance = calculator.calculateVariance(dataCol);
        return new ScoringSummaryImplementation(mean, standardDeviation, variance, median, min, max);

    }
}
