package com.example.project;

import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;
import org.junit.jupiter.api.Test;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.CategorySeries;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class AudioTest {
    @Test
    void test() {
        try {
            // Đọc tệp WAV từ đường dẫn cụ thể
            String filePath = "src/main/resources/static/wav/women/women_0.wav";
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            // Lấy thông tin về định dạng âm thanh của tệp WAV
            AudioFormat format = audioStream.getFormat();
            int numChannels = format.getChannels();
            int sampleSize = format.getSampleSizeInBits();
            boolean isBigEndian = format.isBigEndian();

            // Tính toán kích thước mảng double biên độ
            long numFrames = audioStream.getFrameLength();
            int frameSize = format.getFrameSize();
            int bufferSize = (int) numFrames * frameSize;
            int numSamples = bufferSize / (sampleSize / 8);

            // Đọc dữ liệu âm thanh và chuyển đổi thành mảng double biên độ
            byte[] audioData = new byte[bufferSize];
            audioStream.read(audioData);

            double[] amplitudes = new double[numSamples];

            int sampleIndex = 0;
            for (int i = 0; i < bufferSize; i += frameSize) {
                int amplitude = 0;

                if (sampleSize == 8) {
                    amplitude = audioData[i];
                } else if (sampleSize == 16) {
                    if (isBigEndian) {
                        amplitude = ((audioData[i] & 0xff) << 8) | (audioData[i + 1] & 0xff);
                    } else {
                        amplitude = (audioData[i] & 0xff) | ((audioData[i + 1] & 0xff) << 8);
                    }
                }

                for (int j = 0; j < numChannels; j++) {
                    amplitudes[sampleIndex++] = (double) amplitude / ((1 << sampleSize - 1) - 1);
                }
            }

            // Phát lại âm thanh từ mảng double biên độ
            double[] sound = new double[amplitudes.length];
            for (int i=0; i<amplitudes.length; i++){
                sound[i] = amplitudes[i];
                if (amplitudes[i]>1)
                    System.out.println("true");
            }
            playAudio(sound, format);

            // Đóng AudioInputStream
            audioStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void playAudio(double[] amplitudes, AudioFormat format) throws LineUnavailableException {
        SourceDataLine line = AudioSystem.getSourceDataLine(format);
        line.open(format);
        line.start();

        byte[] audioData = new byte[amplitudes.length * format.getSampleSizeInBits() / 8];
        int dataIndex = 0;

        for (int i = 0; i < amplitudes.length; i++) {
            double amplitude = amplitudes[i];
            int sampleValue = (int) (amplitude * ((1 << format.getSampleSizeInBits() - 1) - 1));

            if (format.getSampleSizeInBits() == 8) {
                audioData[dataIndex++] = (byte) sampleValue;
            } else if (format.getSampleSizeInBits() == 16) {
                audioData[dataIndex++] = (byte) (sampleValue & 0xFF);
                audioData[dataIndex++] = (byte) ((sampleValue >> 8) & 0xFF);
            }
        }

        line.write(audioData, 0, audioData.length);
        line.drain();
        line.stop();
        line.close();
    }



    @Test
    void test3(){
        String file1 = "src/main/resources/static/wav/women/women_0.wav";
        String file2 = "D:/Study/4-2/CSDL-DPT/dataset/women/women_0.wav";
        double[] file1Chan = getAmpArray(file1);
        double[] file2Chan = getAmpArray(file2);
        System.out.println(file1Chan.length + " " + file2Chan.length);

    }

    private double[] getAmpArray(String filePath){
        File audioFile = new File(filePath);
        AudioInputStream audioStream = null;
        try {
            audioStream = AudioSystem.getAudioInputStream(audioFile);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Lấy thông tin về định dạng âm thanh của tệp WAV
        AudioFormat format = audioStream.getFormat();
        int numChannels = format.getChannels();
        int sampleSize = format.getSampleSizeInBits();
        boolean isBigEndian = format.isBigEndian();

        // Tính toán kích thước mảng double biên độ
        long numFrames = audioStream.getFrameLength();
        int frameSize = format.getFrameSize();
        int bufferSize = (int) numFrames * frameSize;
        int numSamples = bufferSize / (sampleSize / 8);

        // Đọc dữ liệu âm thanh và chuyển đổi thành mảng double biên độ
        byte[] audioData = new byte[bufferSize];
        try {
            audioStream.read(audioData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        double[] amplitudes = new double[numSamples];

        int sampleIndex = 0;
        for (int i = 0; i < bufferSize; i += frameSize) {
            int amplitude = 0;

            if (sampleSize == 8) {
                amplitude = audioData[i];
            } else if (sampleSize == 16) {
                if (isBigEndian) {
                    amplitude = ((audioData[i] & 0xff) << 8) | (audioData[i + 1] & 0xff);
                } else {
                    amplitude = (audioData[i] & 0xff) | ((audioData[i + 1] & 0xff) << 8);
                }
            }

            for (int j = 0; j < numChannels; j++) {
                amplitudes[sampleIndex++] = (double) amplitude / ((1 << sampleSize - 1) - 1);
            }
        }
        return amplitudes;
    }

    @Test
    void test4(){
        String file1 = "src/main/resources/static/wav/women/women_0.wav";
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(file1));

            AudioFormat audioFormat = audioInputStream.getFormat();
            int sampleSize = audioFormat.getSampleSizeInBits() / 8;
            int bufferSize = (int) audioInputStream.getFrameLength() * sampleSize;
            byte[] audioBytes = new byte[bufferSize];

            int bytesRead = audioInputStream.read(audioBytes);
            double[] audioData = new double[bytesRead / sampleSize];

            // Convert audio data from byte to double
            for (int i = 0; i < bytesRead / sampleSize; i++) {
                int sampleIndex = i * sampleSize;
                if (audioFormat.isBigEndian()) {
                    for (int j = 0; j < sampleSize; j++) {
                        int shift = (sampleSize - 1 - j) * 8;
                        audioData[i] += (double) (audioBytes[sampleIndex + j] & 0xFF) * Math.pow(2, shift);
                    }
                } else {
                    for (int j = 0; j < sampleSize; j++) {
                        int shift = j * 8;
                        audioData[i] += (double) (audioBytes[sampleIndex + j] & 0xFF) * Math.pow(2, shift);
                    }
                }
            }

            // Perform DFT
            DoubleFFT_1D fft = new DoubleFFT_1D(audioData.length);
            double[] transformedArray = new double[audioData.length * 2];
            System.arraycopy(audioData, 0, transformedArray, 0, audioData.length);

            fft.realForwardFull(transformedArray);
            fft.realForward(audioData);
            System.out.println("audiodata size: " + audioData.length + " " + transformedArray.length);

            // Compute frequencies corresponding to each amplitude
            double sampleRate = audioFormat.getSampleRate();
            double[] frequencies = new double[audioData.length / 2];
            for (int i = 0; i < frequencies.length; i++) {
                double frequency = (double) i * sampleRate / audioData.length;
                frequencies[i] = frequency;
            }

            // Print the results
            double a = 0;
            double b = 0;
            for (int i = 0; i < frequencies.length; i++) {
                a += audioData[i] * frequencies[i];
                b += audioData[i];
                System.out.println("Amplitude: " + audioData[i] + ", Frequency: " + frequencies[i] + " Hz");
            }
            System.out.println(a/b);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
