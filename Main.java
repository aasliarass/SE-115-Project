// Main.java — Students version
import java.io.*;
import java.util.*;

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

    }

    // ======== 10 REQUIRED METHODS (Students fill these) ========

    public static String mostProfitableCommodityInMonth(int month) {
        if(month < 0 || month > 11){
            return "INVALID_MONTH";
        }
        int[] totals = new int[COMMS]; //0-Gold 1-Oil 2-Silver 3-Wheat 4-Copper
        for(int i = 0; i < DAYS; i++){
            for(int j = 0; j < COMMS; j++){
                totals[j] += profit[month][i][j];
            }
        }
        int mostProfit = Integer.MIN_VALUE;
        int mostProfitableCommodity = 0;
        for(int i = 0; i < totals.length; i++){
            if(totals[i] > mostProfit) {
                mostProfit = totals[i];
                mostProfitableCommodity = i;
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
        for(int i = 0; i < COMMS; i++){
            totalProfit += profit[month][dayIndex][i];
        }
        return totalProfit;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        return 1234;
    }

    public static int bestDayOfMonth(int month) {
        if(month < 0 || month > 11){
            return -1;
        }
        int bestDayProfit = Integer.MIN_VALUE;
        int bestDay = 0;
        int totalOfDay = 0;
        for(int i = 0; i < DAYS; i++){
            for(int j = 0; j < COMMS; j++){
                totalOfDay += profit[month][i][j];
            }
            if(totalOfDay > bestDayProfit){
                bestDayProfit = totalOfDay;
                bestDay = i + 1 ;
            }
            totalOfDay = 0;
        }
        return bestDay;
    }
    
    public static String bestMonthForCommodity(String comm) { 
        return "DUMMY"; 
    }

    public static int consecutiveLossDays(String comm) { 
        return 1234; 
    }
    
    public static int daysAboveThreshold(String comm, int threshold) { 
        return 1234; 
    }

    public static int biggestDailySwing(int month) {
        if(month < 0 || month > 11) {
            return -99999;
        }
        int maxSwing = Integer.MIN_VALUE;
        int previousDayTotal = 0;
        int difference = Integer.MIN_VALUE;
        for(int i = 0; i < COMMS; i++){
            previousDayTotal += profit[month][0][i];
            }
        for(int m = 1; m < DAYS; m++){
            int currentDayTotal = 0;
            for(int n = 0; n < COMMS; n++){
                currentDayTotal += profit[month][m][n];
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
        return "DUMMY is better by 1234"; 
    }
    
    public static String bestWeekOfMonth(int month) { 
        return "DUMMY"; 
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}