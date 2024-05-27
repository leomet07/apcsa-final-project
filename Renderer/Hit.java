import processing.core.PVector;

public class Hit {
    PVector location;
    PVector normal;
    double t;
    boolean front_face;
    boolean hitHappened;

    public Hit() {
        this.hitHappened = false;
    }

    public Hit(PVector location, PVector normal, double t) {
        this.hitHappened = true;
        this.location = location;
        this.normal = normal;
        this.t = t;
    }
    public Hit(PVector location, PVector normal, double t, boolean front_face) {
        this.hitHappened = true;
        this.location = location;
        this.normal = normal;
        this.t = t;
        this.front_face = front_face;
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
        return new Hit(location, normal, t, front_face);
    }
}
