import processing.core.PVector;

public class Triangle implements Hittable {
    PVector p1;
    PVector p2;
    PVector p3;
    String type = "triangle";

    public Triangle(PVector p1, PVector p2, PVector p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    boolean didRayIntersect(Ray ray) {
        PVector nearPoint = ray.at((float) 0.0001);
        PVector farPoint = ray.at(69420);

        // PVector triangleNormal =

        return false;
    }

    @Override
    public boolean hit(Ray r, double tmin, double tmax, Hit rec) {
        PVector nearPoint = r.at((float) tmin);
        PVector farPoint = r.at((float) tmax);

        PVector triangleNormal = PVector.cross(PVector.sub(p2, p1), PVector.sub(p3, p1), null);
        triangleNormal.normalize();

        // Find distance from LP1 and LP2 to the plane defined by the triangle
        // float Dist1 = (R1-P1).dot( Normal );
        // float Dist2 = (R2-P1).dot( Normal );
        float dist1 = PVector.dot(PVector.sub(nearPoint, p1), triangleNormal);
        float dist2 = PVector.dot(PVector.sub(farPoint, p1), triangleNormal);

        if ((dist1 * dist2) >= ((float) 0)) {
            // no cross
            return false;
        }

        rec.t = 5;
        rec.location = r.at(5);
        rec.set_face_normal(r, new PVector(1, 1, 1));

        return true;
    }
}