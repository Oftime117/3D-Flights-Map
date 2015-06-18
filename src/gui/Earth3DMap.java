package gui;

import jme3tools.optimize.GeometryBatchFactory;
import applicationPart.Airport;
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

public class Earth3DMap extends SimpleApplication {

	private boolean deleted = false;

	private static final float TEXTURE_LAT_OFFSET = -0.2f;
	private static final float TEXTURE_LON_OFFSET = 2.8f;

	private Node earthNode = new Node();
	private Node airportsNode = new Node();
	private Node arcNode = new Node();
	private Node overlayNode = new Node();
	
	private float airportRadius = 0.002f;
//	private ColorRGBA airportColor = ColorRGBA.Yellow;
	
	private float scale = 2.0f;
	private int taille = 200;
	private Vector3f[] vertices = new Vector3f[taille];
	
	Spatial earth_geom;
	@Override
	public void simpleInitApp()
	{

		assetManager.registerLocator("earth.zip", ZipLocator.class);
		earth_geom = assetManager.loadModel("Sphere.mesh.xml");
		
		overlayNode.attachChild(airportsNode);
		overlayNode.attachChild(arcNode);
		
		earthNode.attachChild(earth_geom);
		earthNode.attachChild(overlayNode);
		earthNode.scale(scale);
		
		Route buff = Route.getRoutesList().get(0);
		
//		drawRoute(buff.getSrcAirport().getLatitude(), buff.getSrcAirport().getLongitude(), buff.getDstAirport().getLatitude(), buff.getDstAirport().getLongitude());
		
		
		rootNode.attachChild(arcNode);
		rootNode.attachChild(earthNode);
		camInit();
		
		displayAllAirports(ColorRGBA.Red);
		GeometryBatchFactory.optimize(earthNode);
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
	
	public void displayAirport(float latitude, float longitude, String name, ColorRGBA airportColor)
	{
		Sphere sphere = new Sphere(16, 8, airportRadius);
		Geometry airpGeom = new Geometry(name, sphere);
		Material airpMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		airpMat.setColor("Color", airportColor);
		airpGeom.setMaterial(airpMat);
		airpGeom.setLocalTranslation(geoCoordTo3dCoord(latitude, longitude));
		if (!airportsNode.hasChild(airpGeom)) airportsNode.attachChild(airpGeom);
	}
	
	private void displayAllAirports(ColorRGBA airportColor) {
		Airport.getairportsMap()
		.values()
		//.stream().limit(1000)
		.forEach(airport -> {
			displayAirport(airport.getLatitude(), airport.getLongitude(), airport.getName() + airport.getCity().getName(), airportColor);
		});
	}
	
	private void camInit() {
		/*---------- Gestion de cam�ra ----------*/
		flyCam.setEnabled(false);
		
		ChaseCamera chaseCam = new ChaseCamera(cam, earthNode, inputManager);
		chaseCam.setDownRotateOnCloseViewOnly(true);
		chaseCam.setDragToRotate(true);
		chaseCam.setHideCursorOnRotate(true);
		chaseCam.setSmoothMotion(true);
		chaseCam.setTrailingEnabled(true);
		//chaseCam.setToggleRotationTrigger(triggers);
		// param�tres 
		
		chaseCam.setInvertVerticalAxis(true);
		chaseCam.setRotationSpeed(4.0f);
//		chaseCam.setRotationSensitivity(500.0f);
		chaseCam.setMinVerticalRotation((float) -(Math.PI/2 - 0.0001f));
		chaseCam.setMaxVerticalRotation((float) Math.PI/2);
		chaseCam.setMinDistance(1.5f * scale);
		chaseCam.setMaxDistance(4 * scale);
		chaseCam.setZoomSensitivity(1.2f);
		
		inputManager.addMapping("Rotate Left", new KeyTrigger(KeyInput.KEY_LEFT));
		inputManager.addMapping("Rotate Right", new KeyTrigger(KeyInput.KEY_RIGHT));
		inputManager.addMapping("Rotate Up", new KeyTrigger(KeyInput.KEY_UP));
		inputManager.addMapping("Rotate Down", new KeyTrigger(KeyInput.KEY_DOWN));
		
		AnalogListener analogListener = new AnalogListener() {
			
			@Override
			public void onAnalog(String name, float value, float tpf) {
				if(name.equals("Rotate Left")) rootNode.rotate(0, value*2.0f, 0);
				if(name.equals("Rotate Right")) rootNode.rotate(0, -value*2.0f, 0);
//				if(name.equals("Rotate Up")) earthNode.rotate(-value*3.0f,0,value*2.0f);
//				if(name.equals("Rotate Down")) earthNode.rotate(value*3.0f,0,-value*2.0f);
			}
		};
		
		inputManager.addListener(analogListener, "Rotate Left", "Rotate Right", "Rotate Up", "Rotate Down");
	}
	
	private void drawRoute(float srcLat, float srcLon, float dstLat, float dstLon) {
		
		Vector3f srcCoord = geoCoordTo3dCoord(srcLat, srcLon);
		Vector3f dstCoord = geoCoordTo3dCoord(dstLat, dstLon);
		System.out.println(earth_geom.getWorldScale());

		
		
		Mesh lineMesh = new Mesh();
		lineMesh.setMode(Mesh.Mode.LineStrip);
		lineMesh.setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
		lineMesh.updateBound();
		lineMesh.setLineWidth(2.0f);
		
		Geometry lineGeo = new Geometry("lineGeo", lineMesh);
		Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.Green);
		lineGeo.setMaterial(mat);
		lineGeo.scale(dstCoord.distance(srcCoord));
//		lineGeo.setLocalTranslation(dstCoord);
		
		arcNode.attachChild(lineGeo);
		
	}
	
	@Override
	public void simpleUpdate(float tpf) {
		if(deleted != true)
		{
			// changement du background
			viewPort.setBackgroundColor(new ColorRGBA(0.1f,0.1f,0.1f,1.0f));
			
//			 Param�tre de flyCam pour passer en azerty
			inputManager.deleteMapping("FLYCAM_Forward");
			inputManager.deleteMapping("FLYCAM_Backward");
			inputManager.deleteMapping("FLYCAM_Lower");
			inputManager.deleteMapping("FLYCAM_StrafeLeft");
			inputManager.deleteMapping("FLYCAM_StrafeRight");
			inputManager.deleteMapping("FLYCAM_Rise");
			inputManager.addMapping("FLYCAM_Forward", new KeyTrigger(KeyInput.KEY_Z));
			inputManager.addMapping("FLYCAM_Backward", new KeyTrigger(KeyInput.KEY_S));
			inputManager.addMapping("FLYCAM_StrafeLeft", new KeyTrigger(KeyInput.KEY_Q));
			inputManager.addMapping("FLYCAM_StrafeRight", new KeyTrigger(KeyInput.KEY_D));
			inputManager.addMapping("FLYCAM_Rise", new KeyTrigger(KeyInput.KEY_SPACE));
			inputManager.addMapping("FLYCAM_Lower", new KeyTrigger(KeyInput.KEY_LCONTROL));
			inputManager.addListener(flyCam, new String[] {"FLYCAM_Forward", "FLYCAM_Backward", "FLYCAM_Lower", "FLYCAM_StrafeLeft", "FLYCAM_StrafeRight", "FLYCAM_Rise"});
			flyCam.setMoveSpeed(10f);
			flyCam.setZoomSpeed(20.0f);
			deleted = true;		
		}
	}
}
