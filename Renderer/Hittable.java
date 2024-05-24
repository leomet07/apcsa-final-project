public interface Hittable {
    public boolean hit(Ray r, double tmin, double tmax, Hit rec);
}