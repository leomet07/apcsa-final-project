import processing.core.PVector;

public class Sphere {
    PVector center;
    float radius;

    public Sphere(PVector center, float radius) {
        this.center = center;
        this.radius = radius;
        System.out.println("RADIUS: " + radius);
    }

    public boolean hit_sphere(Ray r) {
        PVector oc = PVector.sub(this.center, r.start);
        float a = PVector.dot(r.direction, r.direction);
        float b = -2 * PVector.dot(r.direction, oc);
        float c = PVector.dot(oc, oc) - (radius * radius);
        float discriminant = b * b - 4 * a * c;
        // System.out.println("a: " + a);
        // System.out.println("b: " + b);
        // System.out.println("c: " + c);
        // System.out.println("Discriminant: " + discriminant);
        if (discriminant >= 0) {
        }
        return (discriminant >= 0);
    }
}
