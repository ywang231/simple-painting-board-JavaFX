import java.util.ArrayList;
import javafx.scene.paint.Color;

//--------------------------------------------------------------------

enum DShapeType {
	ELine, ERectangle, EOval, EUnknow
}

// Base class for all shape info
class DShape {
	public double start_x;
	public double start_y;
	public double end_x;
	public double end_y;
	public Color color = Color.WHITE;
	public boolean filled = false;

	public DShapeType toShape() {
		return DShapeType.EUnknow;
	}

	public double getWidth() {
		return Math.abs(end_x - start_x);
	}

	public double getHeight() {
		return Math.abs(end_y - start_y);
	}
}

// Line
class DLine extends DShape {
	public DShapeType toShape() {
		return DShapeType.ELine;
	}
}

//Rectangle
class DRectangle extends DShape {

	public DShapeType toShape() {
		return DShapeType.ERectangle;
	}
}

//Oval
class DOval extends DShape {
	public DShapeType toShape() {
		return DShapeType.EOval;
	}
}

public class ShapeData {
	protected ArrayList<DShape> _shapeStack = null;

	public ArrayList<DShape> getShapeStack() {
		if (_shapeStack == null) {
			_shapeStack = new ArrayList<DShape>();
		}
		return _shapeStack;
	}

	public void clear() {
		getShapeStack().clear();
	}

	public void push(DShapeType s, double sx, double sy, double ex, double ey, Color c, boolean filled) {
		DShape newShape = null;
		if (DShapeType.ELine == s) {
			newShape = new DLine();

		} else if (DShapeType.EOval == s) {
			newShape = new DOval();
		} else {
			newShape = new DRectangle();
		}
		newShape.start_x = sx;
		newShape.start_y = sy;
		newShape.end_x = ex;
		newShape.end_y = ey;
		newShape.color = c;
		newShape.filled = filled;
		getShapeStack().add(newShape);
	}

	// Update the data on the top of the data stack
	public void updateTop(double ux, double uy) {
		if (getShapeStack().size() <= 0)
			return;
		DShape top_shape = getShapeStack().get(getShapeStack().size() - 1);
		top_shape.end_x = ux;
		top_shape.end_y = uy;
	}

	// Remove the top data of the data stack
	public void pop() {
		if (getShapeStack().size() <= 0)
			return;
		getShapeStack().remove(getShapeStack().size() - 1);
	}
}