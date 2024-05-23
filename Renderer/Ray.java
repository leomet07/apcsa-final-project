import processing.core.*;

public class Ray {
    public Point start;
    public PVector direction;
    public PVector unitDirection;

    public Ray(Point start, PVector direction) {
        this.start = start;
        this.direction = direction;
        this.unitDirection = direction.normalize(null);
    }

    public PVector at(float t) {
        return PVector.add(this.start, PVector.mult(direction, t));
    }
}
