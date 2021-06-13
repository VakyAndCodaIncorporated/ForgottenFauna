package coda.forgottenfauna.common.world;

public class ResurrectionEventHandler {
    private int ticks;

    public void start(int ticks) {
        if (this.ticks < ticks) {
            this.ticks = ticks;
        }
    }

    public boolean isActive() {
        return ticks > 0;
    }

    public void tick() {
        if (isActive()) {
            --ticks;
        }
    }

    int getTicks() {
        return ticks;
    }

    void setTicks(int ticks) {
        this.ticks = ticks;
    }
}
