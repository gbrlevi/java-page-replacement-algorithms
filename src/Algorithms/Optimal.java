package Algorithms;

import Util.IPageReplacementAlgorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementação do algoritmo Ótimo.
 * Substitui a página que será usada mais tarde no futuro.
 */
public class Optimal implements IPageReplacementAlgorithm {

    @Override
    public int simulate(int[] pages, int numFrames) {
        int pageFaults = 0;
        List<Integer> frames = new ArrayList<>();

        for (int i = 0; i < pages.length; i++) {
            int page = pages[i];

            if (!frames.contains(page)) {
                pageFaults++;

                if (frames.size() < numFrames) {
                    frames.add(page);
                } else {
                    int pageToReplace = -1;
                    int farthestUse = -1;

                    for (int framePage : frames) {
                        int nextUse = Integer.MAX_VALUE;
                        for (int j = i + 1; j < pages.length; j++) {
                            if (pages[j] == framePage) {
                                nextUse = j;
                                break;
                            }
                        }

                        if (nextUse == Integer.MAX_VALUE) {
                            pageToReplace = framePage;
                            break;
                        }

                        if (nextUse > farthestUse) {
                            farthestUse = nextUse;
                            pageToReplace = framePage;
                        }
                    }

                    frames.remove((Integer) pageToReplace);
                    frames.add(page);
                }
            }
        }
        return pageFaults;
    }

    @Override
    public String getName() {
        return "Ótimo";
    }
}
