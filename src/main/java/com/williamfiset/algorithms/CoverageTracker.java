package com.williamfiset.algorithms;

import java.util.HashMap;
import java.util.Map;

public class CoverageTracker {
    private static Map<Integer, Boolean> branchCoverage = new HashMap<>();

    // Method to set a flag for a branch when it's reached
    public static void setBranchReached(int branchID) {
        branchCoverage.put(branchID, true);
    }

    public static void clearBranches() {
        branchCoverage = new HashMap<>();
    }

    public static void setTotalBranches(int branches) {
        for(int i = 0; i < branches; i++) {
            branchCoverage.put(i, false);
        }
    }

    // Method to get coverage information
    public static Map<Integer, Boolean> getBranchCoverage() {
        return branchCoverage;
    }

    // Method to write branch coverage information to console
    public static void writeCoverageToConsole() {
        System.out.println("Branch Coverage Information:");
        for (Map.Entry<Integer, Boolean> entry : branchCoverage.entrySet()) {
            System.out.println("Branch ID: " + entry.getKey() + ", Covered: " + entry.getValue());
        }
    }
}
