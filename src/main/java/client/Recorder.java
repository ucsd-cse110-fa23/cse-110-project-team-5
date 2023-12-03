package client;

import java.io.File;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

public class Recorder {
    private TargetDataLine targetLine; // Target data line for audio recording
    private File outputFile; // File to store the recorded audio
    private String whisperResponse; // Response from the 'Whisper' API

    private boolean isRecording;

    public Recorder() {
        isRecording = false;
    }

    // Toggle audio recording on/off
    public void toggleRecord() {
        if (isRecording) {
            stopAudioRecording();
        } else {
            startAudioRecording();
        }
        isRecording = !isRecording;
    }

    // Initialize and start the audio recording process
    public void startAudioRecording() {
        try {
            // Define the audio format with sample rate of 44100 Hz, 16 bits per sample,
            // mono, signed audio with little-endian byte order
            AudioFormat format = new AudioFormat(44100, 16, 1, true, false);
            // Set up the DataLine information with the specified audio format
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            // Get and open the target data line for capturing audio
            targetLine = (TargetDataLine) AudioSystem.getLine(info);
            targetLine.open(format);
            targetLine.start();
            // Create the output file where the audio data will be saved
            String filePath = "voiceinstructions.wav";
            // "src" + File.separator + "main" + File.separator + "java" + File.separator +
            outputFile = new File(filePath);

            // New thread to write the audio input stream to the output file in WAVE file format
            Thread recordThread = new Thread(() -> {
                try {
                    AudioSystem.write(new AudioInputStream(targetLine), AudioFileFormat.Type.WAVE, outputFile);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            });

            // Set the recording thread as a daemon so it does not prevent the application from exiting
            recordThread.setDaemon(true);
            recordThread.start();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    // Stop audio recording
    public void stopAudioRecording() {
        if (targetLine != null) {
            targetLine.stop();
            targetLine.close();
        }
    }

    // Platform.runLater(() -> {});
    // Retrieve the response from the 'Whisper' API based on user voice command
    public String retrieveVoiceCommandResponse(String fileString) {
        Model model = new Model();
        String mod = "";
        try {
            // Perform a GET request to the 'whisper' endpoint with the audio file
            whisperResponse = model.performRequest("GET", "whisper", fileString);
            // Replace spaces with underscores for subsequent API request formatting
            mod = whisperResponse.replaceAll(" ", "_");
        } catch (Exception e) {
            System.err.println("No input detected");
        }
        System.out.println(mod);
        return mod;
    }

    public boolean isRecording() {
        return isRecording;
    }
}