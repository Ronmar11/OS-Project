package pagereplacement;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 *
 * @author Ronmar abalos
 */
public class LRUPageReplacement { // Changed class name to LRUPageReplacement

    private final List<Integer> pages;
    private final int frames;

    public LRUPageReplacement(List<Integer> pages, int frames) {
        this.pages = pages;
        this.frames = frames;
    }

    /**
     * Runs the LRU Page Replacement Algorithm simulation and prints all data horizontally.
     * @return A formatted string containing the simulation results.
     */
    public String runAlgorithm() {
        Set<Integer> memoryPages = new HashSet<>(frames);
        
        // --- LRU Specific Data Structure ---
        // LinkedList to maintain the order of usage. Head is LRU, Tail is MRU.
        LinkedList<Integer> lruList = new LinkedList<>(); 
        
        List<Integer> frameState = new ArrayList<>(frames);
        for(int i = 0; i < frames; i++) {
            frameState.add(-1); 
        }

        int pageFaults = 0;
        int hitCount = 0;
        
        // Data structure to store the history of frame states for horizontal printing
        List<List<Integer>> frameHistory = new ArrayList<>();
        List<String> actionHistory = new ArrayList<>();
        
        // --- Simulation Logic ---
        for (int page : pages) {
            String action;
            List<Integer> currentFrameState = new ArrayList<>(frameState); // Copy current state
            
            // 1. Page Hit Check
            if (memoryPages.contains(page)) {
                hitCount++;
                action = "H"; // H for Hit
                
                // --- LRU Update on Hit ---
                // Move the page to the end of the list (Most Recently Used)
                lruList.remove((Integer)page); // Remove the existing entry
                lruList.add(page);            // Add it to the tail
            } 
            // 2. Page Fault
            else {
                pageFaults++;
                
                // a) Memory is NOT full (still empty frames)
                if (memoryPages.size() < frames) {
                    memoryPages.add(page);
                    lruList.add(page); // Add new page to the tail (MRU)
                    
                    int emptyIndex = currentFrameState.indexOf(-1);
                    if (emptyIndex != -1) {
                         currentFrameState.set(emptyIndex, page);
                    }
                    action = "F"; // F for Fault
                } 
                // b) Memory is full (replacement needed)
                else {
                    // --- LRU Replacement Logic ---
                    // The page at the head of the list is the LRU page
                    int lruPage = lruList.removeFirst(); 
                    memoryPages.remove(lruPage);
                    
                    // Find the location of the LRU page and replace it with the new page
                    int replaceIndex = currentFrameState.indexOf(lruPage);
                    currentFrameState.set(replaceIndex, page);
                    
                    // Add the new page to memory and to the tail of the LRU list (MRU)
                    memoryPages.add(page);
                    lruList.add(page);
                    
                    action = "F"; // F for Fault
                }
            }
            
            // Update the main frameState and store history
            frameState = currentFrameState;
            frameHistory.add(new ArrayList<>(frameState));
            actionHistory.add(action);
        }
        
        // --- Horizontal Output Formatting (Modified Header) ---
        StringBuilder output = new StringBuilder();
        output.append(" LRU Algorithm Simulation \n"); // Changed header
        output.append("Number of Frames: ").append(frames).append("\n\n");
        
        // Row 1: Reference String
        output.append("Reference String: \n");
        output.append("                       ");
        for (int page : pages) {
            output.append(String.format("%-4d", page));
        }
        output.append("\n");
        
        output.append("\n");

        // Rows 2 to N+1: Frame Contents (Transposed)
        // Iterate through each frame (row)
        for (int f = 0; f < frames; f++) {
            output.append(String.format("Frame %d:        ", f + 1));
            // Iterate through each time step (column)
            for (int t = 0; t < frameHistory.size(); t++) {
                int pageInFrame = frameHistory.get(t).get(f);
                String display = (pageInFrame == -1) ? "-" : String.valueOf(pageInFrame);
                output.append(String.format("%-4s", display));
            }
            output.append("\n");
        }
        
        output.append("\n");
        
        // Final Row: Action (Hit/Fault)
        output.append("Hit/Fault:        ");
        for (String action : actionHistory) {
            output.append(String.format("%-4s", action));
        }
        output.append("\n");
        
        // --- Final Results ---
        output.append("\nTotal Page Requests: ").append(pages.size()).append("\n");
        output.append("Total Page Faults: ").append(pageFaults).append("\n");
        output.append("Total Page Hits: ").append(hitCount).append("\n");
        
        return output.toString();
    }
}