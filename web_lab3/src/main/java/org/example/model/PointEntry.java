package org.example.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "point_result")
public class PointEntry implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "x")
    private double x;

    @Column(name = "y")
    private double y;

    @Column(name = "r")
    private double r;

    @Column(name = "time")
    private String currentTime;

    @Column(name = "hit")
    private boolean isHit;

    public PointEntry() {
        this(0, 0, 0, "", false);
    }

    public PointEntry(double x, double y, double r, String currentTime, boolean isHit) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.currentTime = currentTime;
        this.isHit = isHit;
    }

    public boolean checkHit() {
        return isTriangleHit() || isCircleHit() || isRectangleHit();
    }

    private boolean isRectangleHit() {
        return x <= 0 && y <= 0 && x >= -r / 2 && y >= -r;
    }

    private boolean isCircleHit() {
        return x >= 0 && y <= 0 && (x * x + y * y <= r * r / 4);
    }

    private boolean isTriangleHit() {
        return x >= 0 && y >= 0 && y <= -x + r / 2;
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

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointEntry that = (PointEntry) o;
        return Double.compare(that.x, x) == 0 &&
                Double.compare(that.y, y) == 0 &&
                Double.compare(that.r, r) == 0 &&
                isHit == that.isHit &&
                Objects.equals(id, that.id) &&
                Objects.equals(currentTime, that.currentTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, x, y, r, currentTime, isHit);
    }
}