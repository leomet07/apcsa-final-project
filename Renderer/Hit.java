import processing.core.PVector;

public class Hit {
    PVector location;
    PVector normal;
    double t;
    boolean front_face;
    boolean hitHappened;
    PVector color;

    public Hit() {
        this.hitHappened = false;
        this.color = new PVector(0, 0, 0);
    }

    public Hit(PVector location, PVector normal, double t, boolean front_face, PVector color) {
        this.hitHappened = true;
        this.location = location;
        this.normal = normal;
        this.t = t;
        this.front_face = front_face;
        this.color = color;
    }

    void set_face_normal(Ray r, PVector outward_normal) {
        this.hitHappened = true;
        // Sets the hit record normal vector.
        // NOTE: the parameter `outward_normal` is assumed to have unit length.

        front_face = PVector.dot(r.direction, outward_normal) < 0;
        normal = front_face ? outward_normal : PVector.mult(outward_normal, -1);
        // determine whether front face or not here
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Hit(location, normal, t, front_face, color);
    }
}
