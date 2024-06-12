import processing.core.PApplet;
import processing.core.PVector;
import java.util.Arrays;

public class Renderer extends PApplet {

    Camera cam = new Camera(this);
    public static void main(String[] args) {
        if (args.length == 0){
            System.out.println("Please specify RENDER or DEBUG mode for this ray tracing engine.");
            System.out.println("Example: ./run.sh DEBUG");
            System.out.println("Example: ./run.sh RENDER");
            return;
        }

        String mode = args[0].toLowerCase();

        if (mode.equals("debug")){
            Camera.max_depth = 10;
            Camera.runs_to_average = 3;
        }

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
                cam.eye.add(new PVector(0, 0, 0.1));
            }
            if (key == 's' || key == 'S') {
                cam.eye.add(new PVector(0, 0, -0.1));
            }
            if (key == 'a' || key == 'A') {
                cam.eye.add(new PVector(-0.1, 0, 0));
            }
            if (key == 'd' || key == 'D') {
                cam.eye.add(new PVector(0.1, 0, 0));
            }
        }
        cam.see();
        textSize(40);
        String text = "" + cam.eye.x + ", " + cam.eye.y + ", " + cam.eye.z;
        text(text, 40, 60);
        fill(255, 0, 0);
    }


}