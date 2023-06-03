package com.example.project.util;

import org.springframework.stereotype.Component;


@Component
public class FundamentalFrequency {

    public double calculateFundamentalFrequency(double[] transformedArray, float sampleRate) {

        // Tính tần số cơ bản (tần số tương ứng với đỉnh lớn nhất trong phổ)
        double maxFrequency = -1;
        int maxIndex = -1;
        for (int i = 0; i < transformedArray.length / 2; i += 2) {
            double re = transformedArray[i];
            double im = transformedArray[i + 1];
            double magnitude = Math.sqrt(re * re + im * im);
            if (magnitude > maxFrequency) {
                maxFrequency = magnitude;
                maxIndex = i;
            }
        }
        // Chuyển đổi chỉ số tần số thành tần số cơ bản
        double fundamentalFrequency = maxIndex * sampleRate / transformedArray.length;

        return fundamentalFrequency;
    }

}
