package org.example.main_page;

import org.example.database.DatabaseHelper;
import org.example.model.PointEntry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ManagedBean
@SessionScoped
public class PointBean implements Serializable {
    private static final long serialVersionUID = 0xDEAD;

    private DatabaseHelper databaseHelper;
    private List<PointEntry> points;

    private Double x;
    private Double y;
    private Integer r;

    public void addPointIfCorrect() {
        PointEntry pointEntry = new PointEntry(x, y, r, new Date().toString(), false);
        pointEntry.setHit(pointEntry.checkHit());
        databaseHelper.save(pointEntry);
        points.add(0, pointEntry);
    }

    public PointBean() {
        databaseHelper = new DatabaseHelper();
        points = databaseHelper.getAll();
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Integer getR() {
        return r;
    }

    public void setR(Integer r) {
        this.r = r;
    }

    public List<PointEntry> getPoints() {
        return points;
    }

    public void setPoints(List<PointEntry> points) {
        this.points = points;
    }
}
