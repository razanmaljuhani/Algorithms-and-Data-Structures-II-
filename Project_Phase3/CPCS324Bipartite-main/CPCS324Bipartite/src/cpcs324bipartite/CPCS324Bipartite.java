/* 
Group 5 - Dimah Alolayan - Majd Gezan - Razan Aljuhani 
CPCS324 Project - implementation of the maximum bipartite matching algorithm
 */
package cpcs324bipartite;

import java.util.*;
import java.lang.*;
import java.io.*;

public class CPCS324Bipartite {

    static final int applicants = 6; // number of applicants that are able to attend hospital
    static final int hospitals = 6; // number of hospitals
    static int matchR[] = new int[hospitals];

    public static void main(String[] args) throws java.lang.Exception {
        System.out.println("\tCPCS324 - Project(Phase three) - Maximum Bipartite Matching Algorithm");
        System.out.println("\t---------------------------------------------------------------------");
        //creating graph of the applicants and their hospitals
        boolean bpGraph[][] = new boolean[][]{
            {true, true, false, false, false, false},
            {false, false, false, false, false, true},
            {true, false, false, true, false, false},
            {false, false, true, false, false, false},
            {false, false, false, true, true, false},
            {false, false, false, false, false, true}};
        String[] applicants = {"Ahmed", "Mahmoud", "Eman", "Fatimah", "Kamel", "Nojood"};
        String[] hospital = {"King Abdelaziz University", "King Fahd", "East Jeddah", "King Fahad Armed Forces", "King Faisal Specialist", "Ministry of National Guard"};

        //array used to assign applicant to hospital
        boolean assign[] = new boolean[hospitals];
        for (int k = 0; k < assign.length; k++) {
            assign[k] = false; //as a starting point, set all hospitals to false
        }
        int result = maxMatching(bpGraph);
        for (int i = 0; i < hospitals; i++) {
            if (matchR[i] > -1) {
                System.out.println(applicants[matchR[i]] + " ➜ " + hospital[i] + " Hospital");
                assign[matchR[i]] = true;
            }
        }

        System.out.println("");
        System.out.println("Maximum number of applicants that can get a position in hospital are: " + result);

    }

    // method return the available hospitals for the applicants
    static int maxMatching(boolean bpGraph[][]) {
        for (int i = 0; i < hospitals; ++i) {
            matchR[i] = -1;
        }

        // Count of hospital appointed applicants
        int result = 0;
        for (int u = 0; u < hospitals; u++) {
            // Mark all hospitals as not seen for next applicant.
            boolean seen[] = new boolean[hospitals];
            for (int i = 0; i < hospitals; ++i) {
                seen[i] = false;
            }

            // Find if the applicant 'u' can appoint 
            if (bipartiteMatching(bpGraph, u, seen, matchR)) {
                result++;
            }
        }
        return result;
    }

    static boolean bipartiteMatching(boolean bpGraph[][], int u, boolean visited[], int matchR[]) {
        // Try every hospital one by one
        for (int v = 0; v < hospitals; v++) {
            //check if applicant can attend specific hospital
            //if the applicant has not considered a given hospital --> visited[hospital]==false
            if (bpGraph[u][v] && !visited[v]) {
                // Mark v as visited
                visited[v] = true;
                //check if hospital has not been assigned previously then it can be assigned to applicant
                if (matchR[v] < 0 || bipartiteMatching(bpGraph, matchR[v], visited, matchR)) {
                    matchR[v] = u;
                    return true;
                }
            }
        }
        return false;
    }

}
