package gui;

import java.util.ArrayList;

import jme3tools.optimize.GeometryBatchFactory;
import applicationPart.Airport;
import applicationPart.Filter;
import applicationPart.Route;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.ZipLocator;
import com.jme3.input.ChaseCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.shape.Sphere;
import com.jme3.util.BufferUtils;


/**
 * @author Amirali Ghazi
 * @author Enzo Hamelin
 *
 */

public class Earth3DMap extends SimpleApplication {


	private static final float TEXTURE_LAT_OFFSET = -0.2f;
	private static final float TEXTURE_LON_OFFSET = 2.8f;

	private static final float LINE_RESOLUTION_VALUE = 0.001f;
	private static final float LINE_ALTITUDE_FACTOR = 0.1f;
	private static final float LINE_ALT_VS_DIST_FACTOR = 5f;

	/* Mise en mémoire des valeurs précèdentes */
	private static ColorRGBA srcPrevColor = ColorRGBA.Red, dstPrevColor = ColorRGBA.Blue, genePrevColor = ColorRGBA.Yellow, arcPrevColor = ColorRGBA.Orange;

	/* les noeuds de notre graphe de scène */
	private Node earthNode = new Node();
	private Node geneAirportsNode = new Node();
	private Node srcAirportsNode = new Node();
	private Node dstAirportsNode = new Node();
	private Node arcNode = new Node();
	private Node overlayNode = new Node();

	private float airportRadius = 0.002f;
	private float arcWidth = 1.5f;

	private ArrayList<ArrayList<Route>> buffRouteArray;

	
	@Override
	public void simpleInitApp()
	{
		Spatial earth_geom;

		assetManager.registerLocator("earth.zip", ZipLocator.class);
		earth_geom = assetManager.loadModel("Sphere.mesh.xml");
		/* On attache les noeuds ensembles */
		overlayNode.attachChild(geneAirportsNode);
		overlayNode.attachChild(srcAirportsNode);
		overlayNode.attachChild(dstAirportsNode);
		overlayNode.attachChild(arcNode);
		earthNode.attachChild(earth_geom);
		earthNode.attachChild(overlayNode);
		rootNode.attachChild(earthNode);


		ArrayList<ArrayList<Route>> buff = Filter.filterRoutesFromAirport2("Charles de Gaulle");

		/* Initialisation de la caméra */
		camInit();

		GeometryBatchFactory.optimize(geneAirportsNode);
	}

	private static Vector3f geoCoordTo3dCoord(float lat, float lon)
	{
		float lat_cor = lat + TEXTURE_LAT_OFFSET;
		float lon_cor = lon + TEXTURE_LON_OFFSET;

		return new Vector3f(- FastMath.sin(lon_cor * FastMath.DEG_TO_RAD)
				* FastMath.cos(lat_cor * FastMath.DEG_TO_RAD),
				FastMath.sin(lat_cor * FastMath.DEG_TO_RAD),
				- FastMath.cos(lon_cor * FastMath.DEG_TO_RAD)
				* FastMath.cos(lat_cor * FastMath.DEG_TO_RAD));
	}

	public void displayAirport(float latitude, float longitude, String name, ColorRGBA color)
	{
		Sphere sphere = new Sphere(16, 8, airportRadius);
		Geometry airpGeom = new Geometry(name, sphere);
		Material airpMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		airpMat.setColor("Color", color);
		airpGeom.setMaterial(airpMat);
		airpGeom.setLocalTranslation(geoCoordTo3dCoord(latitude, longitude));
		/* Suivant le type voulu, on le range dans un noeud différent */
		if (name.equals("General Airport") && !geneAirportsNode.hasChild(airpGeom)) geneAirportsNode.attachChild(airpGeom);
		if (name.equals("srcAirport") && !srcAirportsNode.hasChild(airpGeom)) srcAirportsNode.attachChild(airpGeom);
		if (name.equals("dstAirport") && !dstAirportsNode.hasChild(airpGeom)) dstAirportsNode.attachChild(airpGeom);
	}

	public void displayAllGeneralAirports() {
		Airport.getairportsMap()
		.values()
		.forEach(airport -> {
			displayAirport(airport.getLatitude(), airport.getLongitude(), "General Airport", genePrevColor);
		});
	}

	/**@brief Fonction qui va afficher la liste voulu sur la carte 3d 
	 * @param routeArray la liste à afficher
	 */
	public void displayRoutes(ArrayList<ArrayList<Route>> routeArray) {
		buffRouteArray = routeArray;
		routeArray.stream().distinct().filter(array -> !array.isEmpty())
		.forEach(array -> array.stream().distinct()
				.forEach(route -> displayArc(route
						.getSrcAirport()
						.getLatitude(), 
						route.getSrcAirport().getLongitude(),  
						route.getDstAirport().getLatitude(), 
						route.getDstAirport().getLongitude())));
	}

	/* Retire les trajet de la scène */
	public void hideAllArcs() {
		arcNode.detachAllChildren();
	}
	/* Retire tous les aéroports globaux de la scène. */
	public void hideAllGeneralAirports() {
		geneAirportsNode.detachAllChildren();
	}

	/* Supprime tous les ajout graphique fait sur la map */
	public void resetOverlay() {
		overlayNode.detachAllChildren();
		geneAirportsNode.detachAllChildren();
		srcAirportsNode.detachAllChildren();
		dstAirportsNode.detachAllChildren();
		arcNode.detachAllChildren();
		overlayNode.attachChild(geneAirportsNode);
		overlayNode.attachChild(srcAirportsNode);
		overlayNode.attachChild(dstAirportsNode);
		overlayNode.attachChild(arcNode);
	}


	/**@brief Fonction qui change la couleur du type voulu (src, dst, etc)
	 * @param color
	 * @param type
	 */
	public void changeColor(ColorRGBA color, String type) {
		Material newMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		newMat.setColor("Color", color);

		if (type.equals("General Airport")) {
			genePrevColor = color;
			geneAirportsNode.getChildren().forEach(spatial -> spatial.setMaterial(newMat));
		}
		else if (type.equals("srcAirport")) {
			srcPrevColor = color;
			srcAirportsNode.getChildren().forEach(spatial  -> spatial.setMaterial(newMat));
		}
		else if (type.equals("dstAirport")) {
			dstPrevColor = color;
			dstAirportsNode.getChildren().forEach(spatial  -> spatial.setMaterial(newMat));
		}
		else if (type.equals("arc")) {
			arcPrevColor = color;
			arcNode.getChildren().forEach(spatial -> spatial.setMaterial(newMat));
		}
	}

	
	/**Brief: Fonction qui va changer la taille du type voulu
	 * @param size
	 * @param type
	 */
	public void changeSize(float size, String type) {
		Sphere sphere = new Sphere(16, 8, size/500);

		if(type.equals("General Airport")) {
			geneAirportsNode.getChildren().forEach(spatial -> {
				Geometry buff = (Geometry) spatial;
				buff.setMesh(sphere);
			});
		}
		else if(type.equals("srcAirport")) {
			srcAirportsNode.getChildren().forEach(spatial -> {
				Geometry buff = (Geometry) spatial;
				buff.setMesh(sphere);
			});
		}
		else if(type.equals("dstAirport")) {

			dstAirportsNode.getChildren().forEach(spatial -> {
				Geometry buff = (Geometry) spatial;
				buff.setMesh(sphere);
			});
		}
		else if(type.equals("arc")) {
			arcNode.getChildren().forEach(spatial -> {
				Geometry buff = (Geometry) spatial;
				buff.getMesh().setLineWidth(size);
				spatial = buff;
			});
		}
		else System.out.println(type);
	}

	public void resetAllRoutes() {
		hideAllArcs();
		displayRoutes(buffRouteArray);
	}
	
	
	/**
	 * @Brief Fonction d'initialisation de la caméra
	 */
	private void camInit() {
		/*---------- Gestion de caméra ----------*/
		flyCam.setEnabled(false);
		viewPort.setBackgroundColor(new ColorRGBA(0.1f,0.1f,0.1f,1.0f));
		ChaseCamera chaseCam = new ChaseCamera(cam, earthNode, inputManager);
		chaseCam.setDownRotateOnCloseViewOnly(true);
		chaseCam.setDragToRotate(true);
		chaseCam.setHideCursorOnRotate(true);
		chaseCam.setSmoothMotion(true);
		chaseCam.setTrailingEnabled(true);


		chaseCam.setInvertVerticalAxis(true);
		chaseCam.setRotationSpeed(4.0f);
		chaseCam.setMinVerticalRotation((float) -(Math.PI/2 - 0.0001f));
		chaseCam.setMaxVerticalRotation((float) Math.PI/2);
		chaseCam.setMinDistance(2.0f);
		chaseCam.setMaxDistance(10);
		chaseCam.setZoomSensitivity(0.8f);

		inputManager.addMapping("Rotate Left", new KeyTrigger(KeyInput.KEY_LEFT));
		inputManager.addMapping("Rotate Right", new KeyTrigger(KeyInput.KEY_RIGHT));
		inputManager.addMapping("Rotate Up", new KeyTrigger(KeyInput.KEY_UP));
		inputManager.addMapping("Rotate Down", new KeyTrigger(KeyInput.KEY_DOWN));

		AnalogListener analogListener = new AnalogListener() {

			@Override
			public void onAnalog(String name, float value, float tpf) {
				if(name.equals("Rotate Left")) rootNode.rotate(0, value*2.0f, 0);
				if(name.equals("Rotate Right")) rootNode.rotate(0, -value*2.0f, 0);
			}
		};

		inputManager.addListener(analogListener, "Rotate Left", "Rotate Right", "Rotate Up", "Rotate Down");
	}



	

	public void displayArc(float lat1, float lon1, float lat2, float lon2) {

		displayAirport(lat1, lon1, "srcAirport", srcPrevColor);
		displayAirport(lat2, lon2, "dstAirport", dstPrevColor);

		Vector3f[] vertices;
		float dist, step_dist, current_dist, para_coef_a, para_coef_c, inc_coef = 1;

		Vector3f pos1 = geoCoordTo3dCoord(lat1, lon1);
		Vector3f pos2 = geoCoordTo3dCoord(lat2, lon2);

		dist = FastMath.sqrt(FastMath.sqr(pos2.x - pos1.x)
				+ FastMath.sqr(pos2.y-pos1.y)
				+ FastMath.sqr(pos2.z - pos1.z));

		int nb_step = (int) (dist/LINE_RESOLUTION_VALUE);
		vertices = new Vector3f[(nb_step + 2)];
		Vector3f v1to2 = pos2.subtract(pos1).divide(nb_step);
		step_dist = FastMath.sqrt(FastMath.sqr(v1to2.x)
				+ FastMath.sqr(v1to2.y)
				+ FastMath.sqr(v1to2.z));

		vertices[0]= pos1.clone();
		vertices[nb_step+1] = pos2.clone();
		Vector3f current_pos = pos1;

		current_dist = - dist /2;


		para_coef_c = LINE_ALTITUDE_FACTOR * (LINE_ALT_VS_DIST_FACTOR+dist) / LINE_ALT_VS_DIST_FACTOR;
		para_coef_a = -para_coef_c / FastMath.sqr(dist/2);


		for(int i=1; i<=nb_step; i++){
			current_pos = current_pos.add(v1to2);
			current_dist = current_dist + step_dist;

			inc_coef = 1 + (para_coef_a * FastMath.sqr(current_dist) + para_coef_c);


			Vector3f new_pos_n = current_pos.normalize().mult(inc_coef);
			vertices[i] = new_pos_n;
		}


		Mesh lineMesh = new Mesh();
		lineMesh.setMode(Mesh.Mode.LineStrip);
		lineMesh.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
		lineMesh.updateBound();
		lineMesh.setLineWidth(arcWidth);

		Geometry lineGeo = new Geometry("arc", lineMesh);
		Material mat = new Material (assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", arcPrevColor);
		lineGeo.setMaterial(mat);
		arcNode.attachChild(lineGeo);

	}
}
