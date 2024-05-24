import processing.core.PVector;

public class Hit {
    PVector location;
    PVector normal;
    double t;
    boolean front_face;

    public Hit() {

    }

    public Hit(PVector location, PVector normal, double t) {
        this.location = location;
        this.normal = normal;
        this.t = t;
    }

    void set_face_normal(Ray r, PVector outward_normal) {
        // Sets the hit record normal vector.
        // NOTE: the parameter `outward_normal` is assumed to have unit length.

        front_face = PVector.dot(r.direction, outward_normal) < 0;
        normal = front_face ? outward_normal : PVector.mult(outward_normal, -1);
        // determine whether front face or not here
    }
}
