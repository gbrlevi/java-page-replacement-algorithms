package Algorithms;

import Util.IPageReplacementAlgorithm;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Implementação do algoritmo FIFO (First-In, First-Out).
 * Substitui a página que está na memória há mais tempo.
 */
public class Fifo implements IPageReplacementAlgorithm {

    @Override
    public int simulate(int[] pages, int numFrames) {
        int pageFaults = 0;
        Queue<Integer> frames = new LinkedList<>();
        Set<Integer> currentFrames = new HashSet<>();

        for (int page : pages) {
            if (!currentFrames.contains(page)) {
                pageFaults++;

                if (frames.size() == numFrames) {
                    int removedPage = frames.poll();
                    currentFrames.remove(removedPage);
                }

                frames.add(page);
                currentFrames.add(page);
            }
        }
        return pageFaults;
    }

    @Override
    public String getName() {
        return "FIFO";
    }
}
