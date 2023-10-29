package com.fftime.ffmob.aggregation.model;

public class AdLoadResult {
    private boolean completed;
    private boolean loaded;

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public void reset() {
        this.completed = false;
        this.loaded = false;
    }

    public void await() {
        while (!isCompleted()) {
        }
    }

    @Override
    public String toString() {
        return "AdLoadResult{" +
                "completed=" + completed +
                ", loaded=" + loaded +
                '}';
    }
}
