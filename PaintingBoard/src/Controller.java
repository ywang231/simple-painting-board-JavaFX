
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Controller implements Initializable {

	public static Scene loadScene(Class<? extends FXMain> cls, String fxml) {
		try {
			FXMLLoader loader = new FXMLLoader(cls.getResource(fxml));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			return scene;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@FXML // fx:id="_colorCtr"
	private ChoiceBox<String> _colorCtr;

	@FXML // fx:id="_coordinates_show"
	private Label _coordinates_show;

	@FXML // fx:id="_filledBox"
	private CheckBox _filledBox;

	@FXML // fx:id="_separateLine"
	private Line _separateLine;

	@FXML // fx:id="_shapeCtr"
	private ChoiceBox<String> _shapeCtr;

	@FXML // fx:id="_canvas"
	private Canvas _canvas;

	@FXML // fx:id="_root"
	private AnchorPane _root;

	@FXML
	void undoClicked(ActionEvent event) {
		_drawInfo.pop();
		this.drawCanvas(_drawInfo);
	}

	@FXML
	void clearClicked(ActionEvent event) {
		_drawInfo.clear();
		this.drawCanvas(_drawInfo);
	}

	private final static Map<String, Color> colors_map = new HashMap<>();
	private final static Map<String, DShapeType> shapes_map = new HashMap<>();

	// The width of line when drawing shapes
	private final static double LineWidth = 2.0;

	static {
		// Color maps
		colors_map.put("Black", Color.BLACK);
		colors_map.put("Blue", Color.BLUE);
		colors_map.put("Red", Color.RED);
		colors_map.put("Yellow", Color.YELLOW);
		colors_map.put("Magenta", Color.MAGENTA);
		colors_map.put("Green", Color.GREEN);

		// Shape maps
		shapes_map.put("Line", DShapeType.ELine);
		shapes_map.put("Rect", DShapeType.ERectangle);
		shapes_map.put("Oval", DShapeType.EOval);
	}

	// Shape Data for undo and clear action
	protected ShapeData _drawInfo = null;

	private void updateCoordinates(double x, double y) {
		_coordinates_show.setText(String.format("%.0fx%.0f", x, y));
	}

	protected void setupCanvas() {
		_canvas.setOnMousePressed(e -> {
			_drawInfo.push(shapes_map.get(_shapeCtr.getValue()), e.getX(), e.getY(), e.getX(), e.getY(),
					colors_map.get(_colorCtr.getValue()), _filledBox.isSelected());
		});

		_canvas.setOnMouseDragged(e -> {

			double x = e.getX();
			x = x < 0 ? 0 : x;
			x = x > _canvas.getWidth() ? _canvas.getWidth() : x;

			double y = e.getY();
			y = y < 0 ? 0 : y;
			y = y > _canvas.getHeight() ? _canvas.getHeight() : y;
			_drawInfo.updateTop(x, y);
			updateCoordinates(e.getX(), e.getY());
			drawCanvas(this._drawInfo);
		});

		_canvas.setOnMouseMoved(e -> {
			updateCoordinates(e.getX(), e.getY());
		});

		_canvas.setOnMouseEntered(e -> {
			_coordinates_show.setVisible(true);
		});
		_canvas.setOnMouseExited(e -> {
			System.out.print("setOnMouseExited\n");
			_coordinates_show.setVisible(false);
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		_drawInfo = new ShapeData();

		_colorCtr.getItems().addAll((String[]) colors_map.keySet().toArray(new String[0]));
		_colorCtr.setValue(_colorCtr.getItems().get(0));

		_shapeCtr.getItems().addAll((String[]) shapes_map.keySet().toArray(new String[0]));
		_shapeCtr.setValue(_shapeCtr.getItems().get(0));

		setupCanvas();

		_root.widthProperty().addListener((obs, oldVal, newVal) -> {
			System.out.println("Canvas Width: " + newVal);
			_canvas.setWidth((double) newVal);
			_separateLine.setEndX(_separateLine.getStartX() + (double) newVal);
			drawCanvas(_drawInfo);
		});
		_root.heightProperty().addListener((obs, oldVal, newVal) -> {
			System.out.println("Canvas Height: " + newVal);
			_canvas.setHeight((double) newVal - _canvas.getLayoutY());
			drawCanvas(_drawInfo);
		});
	}

	// Clear all the shapes drawn on the canvas
	private void clearCanvas() {
		if (_canvas == null)
			return;
		_canvas.getGraphicsContext2D().clearRect(0, 0, _canvas.getWidth(), _canvas.getHeight());
	}

	// Draw all the shapes based on a list of shapes stored in ShapeData
	public void drawCanvas(ShapeData d) {
		if (null == d)
			return;
		clearCanvas();
		_canvas.getGraphicsContext2D().setLineWidth(LineWidth);
		for (int i = 0; i < d.getShapeStack().size(); ++i) {
			DShape s = d.getShapeStack().get(i);
			if (s.toShape() == DShapeType.ELine) {
				drawLine((DLine) s);
			} else if (s.toShape() == DShapeType.ERectangle) {
				drawRectangle((DRectangle) s);
			} else if (s.toShape() == DShapeType.EOval) {
				drawOval((DOval) s);
			} else {
			}
		}
	}

	// -------------Draw different shapes--------------------
	private void drawLine(DLine l) {
		GraphicsContext gc = _canvas.getGraphicsContext2D();
		gc.setStroke(l.color);
		gc.strokeLine(l.start_x, l.start_y, l.end_x, l.end_y);
	}

	private void drawOval(DOval o) {
		GraphicsContext gc = _canvas.getGraphicsContext2D();
		gc.setStroke(o.color);
		if (o.filled) {
			gc.setFill(o.color);
			gc.fillOval(Math.min(o.start_x, o.end_x), Math.min(o.start_y, o.end_y), o.getWidth(), o.getHeight());
		} else {
			gc.strokeOval(Math.min(o.start_x, o.end_x), Math.min(o.start_y, o.end_y), o.getWidth(), o.getHeight());
		}
	}

	private void drawRectangle(DRectangle r) {
		GraphicsContext gc = _canvas.getGraphicsContext2D();
		gc.setStroke(r.color);
		if (r.filled) {
			gc.setFill(r.color);
			gc.fillRect(Math.min(r.start_x, r.end_x), Math.min(r.start_y, r.end_y), r.getWidth(), r.getHeight());
		} else {
			gc.strokeRect(Math.min(r.start_x, r.end_x), Math.min(r.start_y, r.end_y), r.getWidth(), r.getHeight());
		}
	}
	// -------------Draw different shapes-------------------

}
