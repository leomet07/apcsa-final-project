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
        // Thanks https://stackoverflow.com/a/42752998
        PVector E1 = PVector.sub(p2, p1);
        PVector E2 = PVector.sub(p3, p1);
        PVector N = PVector.cross(E1, E2, null);
        float det = -PVector.dot(ray.direction, N);
        float invdet = (float) 1.0 / det;
        PVector AO = PVector.sub(ray.start, p1);
        PVector DAO = PVector.cross(AO, ray.direction, null);
        float u = PVector.dot(E2, DAO) * invdet;
        float v = -PVector.dot(E1, DAO) * invdet;
        float t = PVector.dot(AO, N) * invdet;
        return (det >= 1e-6 && t >= 0.0 && u >= 0.0 && v >= 0.0 && (u + v) <= 1.0);

        // return false;
    }

    @Override
    public boolean hit(Ray r, double tmin, double tmax, Hit rec) {
        boolean didHitHappen = didRayIntersect(r);
        if (!didHitHappen) {
            return false;
        }
        rec.t = 5;
        rec.location = r.at(5);
        rec.set_face_normal(r, new PVector(1, 1, 1));

        return true;
    }
}