import processing.core.PVector;

public abstract class Hittable {
    PVector color;
    public Hittable(PVector color){
        this.color = color;
    }
    public abstract boolean hit(Ray r, double tmin, double tmax, Hit rec);
}