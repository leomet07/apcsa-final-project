import processing.core.PVector;

public class Sphere implements Hittable {
    PVector center;
    float radius;

    public Sphere(PVector center, float radius) {
        this.center = center;
        this.radius = radius;
    }

    public boolean hit(Ray r, double ray_tmin, double ray_tmax, Hit rec) {
        PVector oc = PVector.sub(this.center, r.start);
        float a = (float) Math.pow(r.direction.mag(), 2);
        float h = PVector.dot(r.direction, oc);
        float c = (float) Math.pow(oc.mag(), 2) - (radius * radius);
        float discriminant = h * h - a * c;

        if (discriminant < 0) {
            return false;

        }

        double sqrtd = Math.sqrt(discriminant);
        double root = (h - sqrtd) / a;
        if (root <= ray_tmin || ray_tmax <= root) {
            root = (h + sqrtd) / a;
            if (root <= ray_tmin || ray_tmax <= root)
                return false;
        }
        rec.t = root;
        rec.location = r.at((float) rec.t);
        PVector outward_normal = PVector.div(PVector.sub(rec.location, this.center),
                radius);
        rec.set_face_normal(r, outward_normal);
        // System.out.println("After calling helper func: " + rec.normal);

        return true;
    }
}
