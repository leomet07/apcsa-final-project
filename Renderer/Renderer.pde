import processing.core.PApplet;
import processing.core.PVector;

public class Renderer extends PApplet {

    Camera cam = new Camera(this);
    public static void main(String[] args) {
        PApplet.main("Renderer");
        
    }

    @Override
    public void settings() {
        size(500, 500);
    }

    @Override
    public void setup() {
        // fill(120,50,240);
    }
    
    @Override
    public void draw(){
        if (keyPressed) {
            if (key == 'w' || key == 'W') {
                cam.eye.add(new PVector(0, 0, 0.02));
            }
            if (key == 's' || key == 'S') {
                cam.eye.add(new PVector(0, 0, -0.02));
            }
        }
        cam.see();
        // ellipse(width/2,height/2,second(),second());
    }


}