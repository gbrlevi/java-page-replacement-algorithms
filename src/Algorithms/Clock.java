package Algorithms;

import Util.IPageReplacementAlgorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementação do algoritmo Relógio (Segunda Chance).
 * Simula o LRU com um "bit de uso".
 */
public class Clock implements IPageReplacementAlgorithm {

    @Override
    public int simulate(int[] pages, int numFrames) {
        int pageFaults = 0;

        int[][] frames = new int[numFrames][2];
        for (int i = 0; i < numFrames; i++) {
            frames[i][0] = -1;
            frames[i][1] = 0;
        }

        Map<Integer, Integer> pageMap = new HashMap<>();

        int clockPointer = 0;

        for (int page : pages) {
            if (!pageMap.containsKey(page)) {
                pageFaults++;

                boolean placed = false;
                while (!placed) {
                    if (frames[clockPointer][0] == -1) {
                        frames[clockPointer][0] = page;
                        frames[clockPointer][1] = 0;
                        pageMap.put(page, clockPointer);
                        placed = true;
                    }
                    else if (frames[clockPointer][1] == 0) {
                        pageMap.remove(frames[clockPointer][0]);

                        frames[clockPointer][0] = page;
                        frames[clockPointer][1] = 0;
                        pageMap.put(page, clockPointer);
                        placed = true;
                    }
                    else {
                        frames[clockPointer][1] = 0;
                    }

                    clockPointer = (clockPointer + 1) % numFrames;
                }
            } else {
                int frameIndex = pageMap.get(page);
                frames[frameIndex][1] = 1;
            }
        }
        return pageFaults;
    }

    @Override
    public String getName() {
        return "Relógio";
    }
}
