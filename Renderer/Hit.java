import processing.core.PVector;

public class Hit {
    PVector location;
    PVector normal;
    double t;

    public Hit() {

    }

    public Hit(PVector location, PVector normal, double t) {
        this.location = location;
        this.normal = normal;
        this.t = t;
    }
}
