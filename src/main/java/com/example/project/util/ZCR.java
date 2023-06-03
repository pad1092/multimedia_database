package com.example.project.util;

import org.springframework.stereotype.Component;

@Component
public class ZCR {
    public double calZCR(double[] audioData){
        int numZeroCrossings = 0;
        for (int i = 1; i < audioData.length; i++) {
            double thisSample = audioData[i];
            double nextSample = audioData[i-1];
            double signThisSample = calSignal(thisSample);
            double signNextSample = calSignal(nextSample);
            numZeroCrossings += Math.abs(signNextSample - signThisSample);
        }
        return (double)numZeroCrossings / (audioData.length * 2);
    }

    private double calSignal(double signal){
        if (signal == 0)
            return 0;
        if (signal > 0)
            return 1;
        return -1;
    }
}
