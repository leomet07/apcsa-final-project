import processing.core.*;

public class Ray {
    public Point start;
    public PVector direction;
    public Ray(Point start, PVector direction){
        this.start = start;
        System.out.println("Direction maginitude: " + direction.mag());
        if (Math.abs(direction.mag() - 1) > 0.001){
            throw new Error("Direction magnitude must be a unit vector");
        }
        this.direction = direction;

    }
    public PVector at(float t){
        return PVector.add(this.start, PVector.mult(direction, t));
    }
}
