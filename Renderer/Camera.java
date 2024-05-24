import processing.core.*;

public class Camera {
    PApplet pa;
    public float aspect_ratio = 1 / 1;
    public int image_width = 500;

    // Calculate the image height, and ensure that it's at least 1.
    int image_height = (int) (image_width / aspect_ratio);

    // Camera
    public Point eye = new Point(0, 0, 0);
    public Point lookat = new Point(0, 0, -1); // Point camera is looking at
    public PVector vup = new PVector(0, 1, 0); // Camera-relative "up" direction

    public Camera(PApplet pa) {
        this.pa = pa;
        this.image_height = (int) (image_width / aspect_ratio);
        this.image_height = (image_height < 1) ? 1 : image_height;
    }

    public void see() {
        Sphere mySphere = new Sphere(new PVector(0, 0, -1), (float) 0.5);
        Sphere mySecondSphere = new Sphere(new PVector(0, -2, -3), (float) 0.5);
        Sphere myGround = new Sphere(new PVector(0, (float) -100.5, -1), (float) 100);

        HittableList world = new HittableList();
        world.add(mySphere);
        world.add(mySecondSphere);
        world.add(myGround);

        // Camera stuffs
        float focal_length = PVector.sub(eye, lookat).mag();
        double fov = 75; // 90 degrees FOV
        double theta = fov * Math.PI / 180.0; // radians total of FOV
        float h = (float) Math.tan(theta / 2); // from center of viewport to top/bottom
        float viewport_height = 2 * h * focal_length;
        float viewport_width = viewport_height * (image_width / image_height);

        // // Calculate the u,v,w unit basis vectors for the camera coordinate frame.
        PVector w = PVector.sub(eye, lookat).normalize(null);
        PVector u = PVector.cross(vup, w, null).normalize(null);
        PVector v = PVector.cross(w, u, null);

        // // Calculate the vectors across the horizontal and down the vertical viewport
        // // edges.
        // vec3 viewport_u = viewport_width * u; // Vector across viewport horizontal
        // edge
        PVector viewport_u = PVector.mult(u, viewport_width);
        // vec3 viewport_v = viewport_height * -v; // Vector down viewport vertical edge
        PVector viewport_v = PVector.mult(v, -viewport_height);

        // Calculate the horizontal and vertical delta vectors from pixel to pixel.
        // PVector viewport_u = new PVector(viewport_width, 0, 0);
        // PVector viewport_v = new PVector(0, -viewport_height, 0);

        // Calculate the horizontal and vertical delta vectors from pixel to pixel.
        PVector pixel_delta_u = PVector.div(viewport_u, image_width);
        PVector pixel_delta_v = PVector.div(viewport_v, image_height);

        // Calculate the location of the upper left pixel.
        // PVector viewport_upper_left = PVector.sub(
        // PVector.sub(PVector.sub(eye, PVector.mult(w, focal_length)),
        // PVector.div(viewport_u, 2)),
        // PVector.div(viewport_v, 2)); // to always LOOK at the "lookat" point (rotate
        // towards it as needed)
        PVector viewport_upper_left = PVector.sub(
                PVector.sub(PVector.sub(eye, new PVector(0, 0, focal_length)), PVector.div(viewport_u, 2)),
                PVector.div(viewport_v, 2)); // to always LOOK forwards

        PVector pixel00_loc = PVector.add(viewport_upper_left,
                PVector.mult(PVector.add(pixel_delta_u, pixel_delta_v), (float) 0.5));

        // System.out.println("P3\n" + image_width + " " + image_height + "\n255\n");
        this.pa.loadPixels();
        for (int j = 0; j < image_height; j++) {
            if (j % 25 == 0 || j == image_height - 1) {
                // System.out.println("\rScanlines remaining: " + (image_height - j) + "
                // \n\n\n");
            }
            for (int i = 0; i < image_width; i++) {
                PVector pixel_center = PVector.add(PVector.add(pixel00_loc, PVector.mult(pixel_delta_u, i)),
                        PVector.mult(pixel_delta_v, j));
                PVector ray_direction = PVector.sub(pixel_center, this.eye);
                Ray rayToPixel = new Ray(this.eye, ray_direction);
                int rayColor = getRayColor(rayToPixel, world);
                int x = i;
                int y = j;
                this.pa.pixels[y * this.image_width + x] = rayColor;

            }
        }
        this.pa.updatePixels();
    }

    public int getRayColor(Ray r, HittableList world) {
        // TODO: RENDER THE INDIVIDUAL NORMALS ON A HITTABLELIST
        Hit rec = new Hit();
        if (world.hit(r, 0, Double.MAX_VALUE, rec)) {
            PVector N = PVector.sub(r.at((float) rec.t), new PVector(0, 0, -1));
            return pa.color((N.x + 1) * (float) 125, (N.y + 1) * (float) 125, (N.z + 1) * (float) 125);
        }
        return pa.color(0, 0, ((r.unitDirection.y) * 100));
    }
}