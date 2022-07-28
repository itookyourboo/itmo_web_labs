package web_lab2.beans;

import java.io.Serializable;

public class Entry implements Serializable {
    private double x, y, r;
    private String currentTime, executionTime;
    private boolean isHit;

    public Entry() {
        this(0, 0, 0, "", "", false);
    }

    public Entry(double x, double y, double r, String currentTime, String executionTime, boolean isHit) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.currentTime = currentTime;
        this.executionTime = executionTime;
        this.isHit = isHit;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(String executionTime) {
        this.executionTime = executionTime;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entry entry = (Entry) o;

        if (Double.compare(entry.x, x) != 0) return false;
        if (Double.compare(entry.y, y) != 0) return false;
        if (Double.compare(entry.r, r) != 0) return false;
        if (isHit != entry.isHit) return false;
        if (currentTime != null ? !currentTime.equals(entry.currentTime) : entry.currentTime != null) return false;
        return executionTime != null ? executionTime.equals(entry.executionTime) : entry.executionTime == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(r);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (currentTime != null ? currentTime.hashCode() : 0);
        result = 31 * result + (executionTime != null ? executionTime.hashCode() : 0);
        result = 31 * result + (isHit ? 1 : 0);
        return result;
    }
}
