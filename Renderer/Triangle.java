import processing.core.PVector;

public class Triangle extends Hittable {
    PVector p1;
    PVector p2;
    PVector p3;
    String type = "triangle";

    public Triangle(PVector p1, PVector p2, PVector p3, PVector color) {
        super(color);
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    @Override
    public boolean hit(Ray r, double tmin, double tmax, Hit rec) {
        // Thanks https://stackoverflow.com/a/42752998
        PVector E1 = PVector.sub(p2, p1);
        PVector E2 = PVector.sub(p3, p1);
        PVector N = PVector.cross(E1, E2, null);
        float det = -PVector.dot(r.direction, N);
        float invdet = (float) 1.0 / det;
        PVector AO = PVector.sub(r.start, p1);
        PVector DAO = PVector.cross(AO, r.direction, null);
        float u = PVector.dot(E2, DAO) * invdet;
        float v = -PVector.dot(E1, DAO) * invdet;
        float t = PVector.dot(AO, N) * invdet;
        boolean didHitHappen = (det >= 1e-6 && t >= 0.0 && u >= 0.0 && v >= 0.0 && (u + v) <= 1.0);
        if (!didHitHappen) {
            return false;
        }
        rec.t = t;
        rec.location = r.at(t);

        // Triangle centroid
        // PVector centroid = PVector.div(PVector.add(PVector.add(p1, p2), p3), 3);
        // PVector triangle_outward_normal = PVector.sub(rec.location, centroid);
        // triangle_outward_normal.normalize();
        // rec.set_face_normal(r, triangle_outward_normal);
        rec.normal = PVector.mult(r.direction, -1);
        rec.hitHappened = true;
        rec.color = this.color;

        return true;
    }
}