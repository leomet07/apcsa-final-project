import processing.core.PVector;

public class Camera {
    public Point eye;

    public Camera(){
        this.eye = new Point(0, 0, 0);
    }

    public void see(){
        PVector dir = new PVector(0, 0, 1);
        Ray rayToP = new Ray(this.eye, dir);
        System.out.println(rayToP.at(2));
    }
}