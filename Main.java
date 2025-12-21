// Main.java — Students version
import java.io.*;
import java.util.*;
import java.nio.file.Paths;

public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January","February","March","April","May","June",
                              "July","August","September","October","November","December"};
    static int[][][] profit;

    // ======== REQUIRED METHOD LOAD DATA (Students fill this) ========
    public static void loadData() {

        profit = new int[MONTHS][DAYS][COMMS];

        for(int m = 0; m < MONTHS; m++){
            String filePath = "Data_Files/" + months[m] + ".txt";

            try{
                Scanner reader = new Scanner(Paths.get(filePath));
                if(reader.hasNextLine()) reader.nextLine();

                while(reader.hasNextLine()){
                    String line = reader.nextLine();
                    String[] piece = line.split(",");

                    int dayIndex = Integer.parseInt(piece[0]) - 1;

                    int commodityIndex = -1;
                    for(int c = 0; c < COMMS; c++){
                        if(commodities[c].equals(piece[1])){
                            commodityIndex = c;
                            break;
                        }
                    }

                    int profitValue = Integer.parseInt(piece[2]);

                    profit[m][dayIndex][commodityIndex] = profitValue;
                }
                reader.close();
            }catch(Exception e){
            }
        }
    }

    // ======== 10 REQUIRED METHODS (Students fill these) ========

    public static String mostProfitableCommodityInMonth(int month) {
        if(month < 0 || month > 11){
            return "INVALID_MONTH";
        }
        int[] totals = new int[COMMS]; //0-Gold 1-Oil 2-Silver 3-Wheat 4-Copper
        for(int d = 0; d < DAYS; d++){
            for(int c = 0; c < COMMS; c++){
                totals[c] += profit[month][d][c];
            }
        }
        int mostProfit = Integer.MIN_VALUE;
        int mostProfitableCommodity = 0;
        for(int p = 0; p < totals.length; p++){
            if(totals[p] > mostProfit) {
                mostProfit = totals[p];
                mostProfitableCommodity = p;
            }
        }
        return commodities[mostProfitableCommodity] + " " +mostProfit;
    }

    public static int totalProfitOnDay(int month, int day) {
        if(month < 0 || month > 11 || day < 1 || day > 28){
            return -99999;
        }
        int totalProfit = 0;
        int dayIndex = day - 1;
        for(int c = 0; c < COMMS; c++){
            totalProfit += profit[month][dayIndex][c];
        }
        return totalProfit;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        if (from < 1 || to > 28 || from > to){
            return -99999;
        }
        int commodityIndex = -1;
        for(int c = 0; c < COMMS; c++){
            if(commodities[c].equals(commodity)){
                commodityIndex = c;
            }
        }
        if(commodityIndex == -1){
            return -99999;
        }
        int totalProfit = 0;
        for(int m = 0; m < MONTHS; m++){
            for(int d = from; d <= to; d++){
                totalProfit += profit[m][d-1][commodityIndex];
            }
        }
        return totalProfit;

    }

    public static int bestDayOfMonth(int month) {
        if(month < 0 || month > 11){
            return -1;
        }
        int bestDayProfit = Integer.MIN_VALUE;
        int bestDay = 0;
        int totalOfDay = 0;
        for(int d = 0; d < DAYS; d++){
            for(int c = 0; c < COMMS; c++){
                totalOfDay += profit[month][d][c];
            }
            if(totalOfDay > bestDayProfit){
                bestDayProfit = totalOfDay;
                bestDay = d + 1 ;
            }
            totalOfDay = 0;
        }
        return bestDay;
    }
    
    public static String bestMonthForCommodity(String comm) {
        int commodityIndex = -1;
        for(int c = 0; c < COMMS; c++){
            if(commodities[c].equals(comm)){
                commodityIndex = c;
            }
        }
        if(commodityIndex == -1){
            return "INVALID_COMMODITY";
        }
        int bestMonthProfit = Integer.MIN_VALUE;
        int bestMonth = 0;
        for(int m = 0; m < MONTHS; m++){
            int totalProfit = 0;
            for(int d = 0; d < DAYS; d++){
                totalProfit += profit[m][d][commodityIndex];
            }
            if(totalProfit > bestMonthProfit){
                bestMonthProfit = totalProfit;
                bestMonth = m;
            }
        }
        return months[bestMonth];
    }

    public static int consecutiveLossDays(String comm) {
        int commodityIndex = -1;
        for(int c = 0; c < COMMS; c++){
            if(commodities[c].equals(comm)){
                commodityIndex = c;
            }
        }
        if(commodityIndex == -1) {
            return -1;
        }
        int maxStreakDay = 0;
        for(int m = 0; m < MONTHS; m++){
            int currentStreakDay = 0;
            for(int d = 0; d < DAYS; d++){
                if(profit[m][d][commodityIndex] < 0){
                    currentStreakDay += 1;
                }
                else{
                    currentStreakDay = 0;
                }
                if(currentStreakDay > maxStreakDay){
                    maxStreakDay = currentStreakDay;
                }
            }
        }
        return maxStreakDay;
    }
    
    public static int daysAboveThreshold(String comm, int threshold) {
        int commodityIndex = -1;
        for(int c = 0; c < COMMS; c++) {
            if (commodities[c].equals(comm)) {
                commodityIndex = c;
            }
        }
        if(commodityIndex == -1) {
            return -1;
        }
        int count = 0;
        for(int m = 0; m < MONTHS; m++){
            for(int d = 0; d < DAYS; d++){
                if(profit[m][d][commodityIndex] > threshold){
                    count += 1;
                }
            }
        }
        return count;
    }

    public static int biggestDailySwing(int month) {
        if(month < 0 || month > 11) {
            return -99999;
        }
        int maxSwing = Integer.MIN_VALUE;
        int previousDayTotal = 0;
        int difference = Integer.MIN_VALUE;
        for(int c = 0; c < COMMS; c++){
            previousDayTotal += profit[month][0][c];
            }
        for(int d = 1; d < DAYS; d++){
            int currentDayTotal = 0;
            for(int c = 0; c < COMMS; c++){
                currentDayTotal += profit[month][d][c];
            }
            difference = Math.abs(previousDayTotal - currentDayTotal);
            if(difference > maxSwing){
                maxSwing = difference;
        }
            previousDayTotal = currentDayTotal;
        }
        return maxSwing;
    }

    
    public static String compareTwoCommodities(String c1, String c2) {
        int index1 = -1;
        int index2 = -1;
        for(int c = 0; c < COMMS; c++) {
            if (commodities[c].equals(c1)) {
                index1 = c;
            }
            if (commodities[c].equals(c2)) {
                index2 = c;
            }
        }
        if(index1 == -1 || index2 == -1){
            return "INVALID_COMMODITY";
        }
        int total1 = 0;
        int total2 = 0;
        int difference = 0;
        for(int m = 0; m < MONTHS; m++){
            for(int d = 0; d < DAYS; d++){
                total1 += profit[m][d][index1];
                total2 += profit[m][d][index2];
            }
        }
        if(total1 > total2){
            difference = total1 - total2;
            return commodities[index1] + " is better by " + difference;
        }
        else if(total2 > total1){
            difference = total2 - total1;
            return commodities[index2] + " is better by " +difference;
        }
        else{
            return "Equal";
        }
    }
    
    public static String bestWeekOfMonth(int month) {
        if (month < 0 || month > 11) {
            return "INVALID_MONTH";
        }
        int bestWeekProfit = Integer.MIN_VALUE;
        int bestWeek = -1;
        for(int w = 1; w < 5; w++){
            int weekTotal = 0;
            for(int start = (w - 1) * 7 + 1; start <= w * 7; start++){
               for(int c = 0; c < COMMS; c++){
                   weekTotal += profit[month][start - 1][c];
               }
            }
            if(weekTotal > bestWeekProfit){
               bestWeekProfit = weekTotal;
               bestWeek = w;
            }
        }
        return "Week " + bestWeek;
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}