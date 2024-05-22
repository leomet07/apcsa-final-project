import processing.core.*;

public class Camera {
    PApplet pa;
    public float aspect_ratio = 1 / 1;
    public int image_width = 500;

    // Calculate the image height, and ensure that it's at least 1.
    int image_height = (int) (image_width / aspect_ratio);

    // Camera
    float focal_length = 1;
    float viewport_height = 2;
    float viewport_width = viewport_height * (image_width / image_height);
    public Point eye = new Point(0, 0, 0);

    // Calculate the horizontal and vertical delta vectors from pixel to pixel.
    public PVector viewport_u = new PVector(viewport_width, 0, 0);
    public PVector viewport_v = new PVector(0, -viewport_height, 0);

    // Calculate the horizontal and vertical delta vectors from pixel to pixel.
    public PVector pixel_delta_u = PVector.div(viewport_u, image_width);
    public PVector pixel_delta_v = PVector.div(viewport_v, image_height);

    // Calculate the location of the upper left pixel.
    public PVector viewport_upper_left = PVector.sub(
            PVector.sub(PVector.sub(eye, new PVector(0, 0, focal_length)), PVector.mult(viewport_u, 2)),
            PVector.mult(viewport_v, 2));
    // public PVector viewport_upper_left = camera_center - vec3(0, 0, focal_length)
    // - viewport_u/2 - viewport_v/2;
    public PVector pixel00_loc = PVector.add(viewport_upper_left,
            PVector.mult(PVector.add(pixel_delta_u, pixel_delta_v), (float) 0.5));
    // public PVector pixel00_loc = viewport_upper_left + 0.5 * (pixel_delta_u +
    // pixel_delta_v);

    public Camera(PApplet pa) {
        this.pa = pa;
        this.image_height = (int) (image_width / aspect_ratio);
        this.image_height = (image_height < 1) ? 1 : image_height;
    }

    // public void see(){
    // PVector dir = new PVector(0, (float) (Math.sqrt(2) / 2), (float)
    // (Math.sqrt(2) / 2));
    // Ray rayToP = new Ray(this.eye, dir);
    // System.out.println(rayToP.at(2));
    // }

    public void see() {
        System.out.println("P3\n" + image_width + " " + image_height + "\n255\n");

        for (int j = 0; j < image_height; j++) {
            if (j % 25 == 0 || j == image_height - 1) {
                System.out.println("\rScanlines remaining: " + (image_height - j) + " \n\n\n");
            }
            for (int i = 0; i < image_width; i++) {
                PVector pixel_center = PVector.add(pixel00_loc, PVector.mult(pixel_delta_u, i),
                        PVector.mult(pixel_delta_v, j));
                PVector ray_direction = PVector.sub(pixel_center, this.eye);
                Ray rayToPixel = new Ray(this.eye, ray_direction);
                // ray r(camera_center, ray_direction);

                // color pixel_color = ray_color(r);
                // write_color(std::cout, pixel_color);
                float rayColor = getRayColor(rayToPixel);
                // System.out.print(rayColor + " ");
                int x = i;
                int y = j;
                this.pa.loadPixels();
                this.pa.pixels[y * this.image_width + x] = this.pa.color(rayColor);
                this.pa.updatePixels();

            }
        }
    }

    public float getRayColor(Ray r) {
        // System.out.println(r.direction.y);
        return (r.direction.y - (float) 0.7) * 400;
    }
}