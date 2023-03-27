package tangram;

import java.awt.Shape;
import java.awt.geom.Area;
import java.io.Serializable;

public class Contour extends Area implements Serializable{

    public Contour() {
        super();
    }

    public Contour(Shape s) {
        super(s);
    }
    
}
