package neuhub.properties;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AsrEncode {
    private int channel;
    private String format;
    private int sample_rate;
    private int post_process;

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public AsrEncode(int channel, String format, int sample_rate) {
        this.channel = channel;
        this.format = format;
        this.sample_rate = sample_rate;
    }

    public AsrEncode(int channel, String format, int sample_rate, int post_process) {
        this.channel = channel;
        this.format = format;
        this.sample_rate = sample_rate;
        this.post_process = post_process;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getSample_rate() {
        return sample_rate;
    }

    public void setSample_rate(int sample_rate) {
        this.sample_rate = sample_rate;
    }

    public int getPost_process() {
        return post_process;
    }

    public void setPost_process(int post_process) {
        this.post_process = post_process;
    }
}
