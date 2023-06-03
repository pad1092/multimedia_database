package com.example.project.util;

import com.example.project.Entity;
import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

@Component
public class MainProcess {
    @Autowired
    FundamentalFrequency fundFreqComp;
    @Autowired
    ZCR zcrComp;
    public Entity attributeProcess(File file){
        Entity entity = new Entity();
        try {
//            // Đọc file WAV
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
//
            // Lấy thông tin về âm thanh
            AudioFormat audioFormat = audioInputStream.getFormat();
            float sampleRate = audioFormat.getSampleRate();
            // Đọc dữ liệu từ file WAV
            byte[] audioData = new byte[(int) audioInputStream.getFrameLength() * audioFormat.getFrameSize()];
            audioInputStream.read(audioData);

            // Chuyển đổi dữ liệu âm thanh thành mảng các giá trị âmplitude

            int[] amplitudes = new int[audioData.length / (audioFormat.getSampleSizeInBits() / 8)];
            int index = 0;
            for (int i = 0; i < audioData.length; i += audioFormat.getSampleSizeInBits() / 8) {
                if (audioFormat.getSampleSizeInBits() == 16) {
                    // Xử lý dữ liệu âm thanh 16-bit
                    int amplitude = (audioData[i + 1] << 8) | audioData[i];
                    amplitudes[index] = amplitude;
                } else {
                    // Xử lý dữ liệu âm thanh 8-bit
                    int amplitude = audioData[i];
                    amplitudes[index] = amplitude;
                }
                index++;
            }

            // Chuyển đổi mảng amplitudes thành mảng doubles
            double[] doubleAmplitudes = new double[amplitudes.length];
            for (int i = 0; i < amplitudes.length; i++) {
                doubleAmplitudes[i] = amplitudes[i];
            }
            // Áp dụng phép biến đổi Fourier bằng jTransforms
            DoubleFFT_1D fft = new DoubleFFT_1D(doubleAmplitudes.length);
            double[] transformedArray = new double[doubleAmplitudes.length * 2];
            System.arraycopy(doubleAmplitudes, 0, transformedArray, 0, doubleAmplitudes.length);
            fft.realForwardFull(transformedArray);


            // Tính tần số cơ bản
            double fundamentalFrequency = fundFreqComp.calculateFundamentalFrequency(transformedArray, sampleRate);
            double zcr = zcrComp.calZCR(doubleAmplitudes);

            entity.setFundFreq(fundamentalFrequency);
            entity.setZcr(zcr);
            return entity;


        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        return entity;
    }

}
