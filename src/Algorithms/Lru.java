package Algorithms;

import Util.IPageReplacementAlgorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementação do algoritmo LRU (Least Recently Used).
 * Substitui a página usada menos recentemente.
 */
public class Lru implements IPageReplacementAlgorithm {

    @Override
    public int simulate(int[] pages, int numFrames) {
        int pageFaults = 0;
        List<Integer> frames = new ArrayList<>();

        for (int page : pages) {
            if (!frames.contains(page)) {
                pageFaults++;

                if (frames.size() == numFrames) {
                    frames.remove(0);
                }

                frames.add(page);
            } else {
                frames.remove((Integer) page);
                frames.add(page);
            }
        }
        return pageFaults;
    }

    @Override
    public String getName() {
        return "LRU";
    }
}
