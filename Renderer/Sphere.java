import processing.core.PVector;

public class Sphere {
    PVector center;
    float radius;

    public Sphere(PVector center, float radius) {
        this.center = center;
        this.radius = radius;
    }

    public double hit_sphere(Ray r) {
        PVector oc = PVector.sub(this.center, r.start);
        float a = (float) Math.pow(r.direction.mag(), 2);
        float h = PVector.dot(r.direction, oc);
        float c = (float) Math.pow(oc.mag(), 2) - (radius * radius);
        float discriminant = h * h - a * c;

        if (discriminant < 0) {
            return -1.0;
        } else {
            // discriminant of 0 or more means at least one collision
            return (h - Math.sqrt(discriminant)) / (a);
        }
    }
}
