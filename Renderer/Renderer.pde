
// void setup(){
//     size(500, 500);
//     cam.see();
// }

// void draw(){
//     rect(50, 50, 50, 50);
//     fill(255, 0, 0);

// }


import processing.core.PApplet;

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
        cam.see();
    }

    @Override
    public void draw(){
        // ellipse(width/2,height/2,second(),second());
    }
}