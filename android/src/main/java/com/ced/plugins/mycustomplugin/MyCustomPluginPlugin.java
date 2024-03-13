package com.ced.plugins.mycustomplugin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.TextureView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import java.util.concurrent.ExecutorService;

//import io.github.capacitor.community.camerapreview.CameraPreview; // Import camera-preview plugin
//import com.getcapacitor.community.camerapreview.CameraPreview;




@CapacitorPlugin(name = "MyCustomPlugin", permissions = {})
public class MyCustomPluginPlugin extends Plugin {

    private MyCustomPlugin implementation = new MyCustomPlugin();
    //private static final int CAMERA_REQUEST_CODE = 101;
    private static final int CAMERA_REQUEST = 1888;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 1001;


    private ExecutorService cameraExecutor;
    private FrameLayout cameraContainer;
    private PluginCall call;

    private Camera camera;
    //private Executor cameraExecutor;

    private TextureView viewFinder;
    private Preview.SurfaceProvider surfaceProvider;

    private ProcessCameraProvider cameraProvider;


    //private static final String TAG = "MPlug";

    private static final String TAG = "DocumentScannerPlugin";
    private static final int CAMERA_REQUEST_CODE = 100;

    //private CameraPreview cameraPreview;
    private boolean isDocumentDetected = false;


    Button bntpicture;
    ImageView imageview;

    private boolean isOpenCvInitialized = false;
    private CameraView cameraView;



    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }

    @PluginMethod
    public void testPluginMethod (PluginCall call) {
        String value= call.getString("msg");
        JSObject ret = new JSObject();
        ret.put("value", value);
        call.resolve(ret);
    }



    /*  @PluginMethod
    public void takePhoto(PluginCall call) {
        Camera.getPhoto(call, options -> {
            JSObject result = new JSObject();
            result.put("success", true);
            result.put("path", options.getString("path"));
            call.resolve(result);
        }, error -> {
            call.reject("Failed to take photo", error);
        }, "uri"); // Utilisation du type "uri" pour spécifier le résultat comme une URI
    }*/

    

    

    /*@PluginMethod
    public void takePhoto(PluginCall call) {
        // Vérifiez que l'activité actuelle est une activité Android
        if (getContext() instanceof Activity) {
            // Créez une intention pour capturer une photo
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            // Vérifiez si une application de caméra est disponible pour gérer cette intention
            if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
                // Lancez l'activité pour capturer la photo
                startActivityForResult(call, takePictureIntent, "onActivityResultCallback");
            } else {
                // Gérez le cas où aucune application de caméra n'est disponible
                call.reject("No camera app available");
            }
        } else {
            // Gérez le cas où l'activité actuelle n'est pas une activité Android
            call.reject("No valid activity found");
        }
    }*/

    // Méthode pour gérer le résultat de l'activité de la caméra
    /*protected void onActivityResultCallback(PluginCall call, int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // La photo a été capturée avec succès, vous pouvez gérer le résultat ici
                JSObject result = new JSObject();
                result.put("success", true);
                result.put("message", "Photo captured successfully");
                call.resolve(result);
            } else {
                // Gérez le cas où la capture de la photo a échoué
                call.reject("Photo capture failed");
            }
        }
    }*/

    /*public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("openCamera")) {
            openCamera(callbackContext);
            return true;
        }
        return false;
    }
 
    private void openCamera(CallbackContext callbackContext) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cordova.getActivity().startActivityForResult(cameraIntent, CAMERA_REQUEST);
        callbackContext.success("Camera opened successfully");
    }*/
    /*@Override
    protected void handleOnResume() {
        super.handleOnResume();
        openCamera();
    }*/



    /*@PluginMethod
    public void openCamera(PluginCall call) {
        saveCall(call);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(call, cameraIntent, "cameraResult");
    }*/
    //********************************

    /*@PluginMethod
    public void openCamera(PluginCall call) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    private void startActivityForResult(Intent cameraIntent, int cameraRequest) {
    }

    @Override
    public void OnActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode== CAMERA_REQUEST && resultCode== RESULT_OK) {
            Bitmap photo =(Bitmap) data.getExtras().get("data");
            imageview.setImageBitmap(photo);

        } else {
            super.OnActivityResult(requestCode,resultCode,data);
        }
    }*/

    //*************************** */


    /*@PluginMethod
    public void openCamera(PluginCall call) {
        saveCall(call);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(call, takePictureIntent, REQUEST_IMAGE_CAPTURE);

        } else {

            Log.d(getLogTag(), "Unable to open camera");
            call.reject("Unable to open camera");
        }
    }

    @Override
    protected void handleOnActivityResult(int requestCode, int resultCode, Intent data) {
        super.handleOnActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            JSObject result = new JSObject();
            // Here you can handle the captured image data and return it to TypeScript
            // For simplicity, let's just return success for now
            result.put("success", true);
            notifyListeners("cameraResult", result);
        }
    }*/

    //**************************** */

   //***********it works with white page**************** */
    /*@RequiresApi(api = Build.VERSION_CODES.M)
    @PluginMethod
    public void openCamera(PluginCall call) {
        if (getContext().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
        } else {
            dispatchTakePictureIntent();
        }
    }

    private void requestPermissions(String[] strings, int requestImageCapture) {
    }

    @Override
    protected void handleRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.handleRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                call.error("Permission denied");
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(call, takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } else {
            call.error("No camera app found");
        }
    }

    @Override
    protected void handleOnActivityResult(int requestCode, int resultCode, Intent data) {
        super.handleOnActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            JSObject ret = new JSObject();
            ret.put("imageUri", imageUri.toString());
            call.success(ret);
        } else {
            call.error("Image capture failed");
        }
    }*/ //************************** */

    /***@RequiresApi(api = Build.VERSION_CODES.M)
    @PluginMethod
    public void openCamera(PluginCall call) {
        Log.d(TAG, "openCamera() called");
        if (getContext().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(call, new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
        } else {
            dispatchTakePictureIntent(call);
            Log.d(getLogTag(), "Unable to open camera");
        }
    }

    @Override
    protected void handleRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.handleRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                PluginCall savedCall = getSavedCall();
                if (savedCall != null) {
                    dispatchTakePictureIntent(savedCall);
                }
            } else {
                call.reject("Permission denied");
            }
        }
    }

    private void dispatchTakePictureIntent(PluginCall call) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(call, takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void handleOnActivityResult(int requestCode, int resultCode, Intent data) {
        super.handleOnActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult() called with requestCode: " + requestCode + ", resultCode: " + resultCode);

        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == getActivity().RESULT_OK) {
                Uri imageUri = data.getData();
                JSObject ret = new JSObject();
                ret.put("imageUri", imageUri.toString());
                call.resolve(ret);
            } else {
                call.reject("Image capture failed");
            }
        }
    }***/
    //********************************* */


    //************************************ */
    //************************************ */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @PluginMethod
    public void openCamera(PluginCall call) {
        if (getContext().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionForCamera(call);
        } else {
            launchCamera(call);
        }
    }

    private void requestPermissionForCamera(PluginCall call) {
        askForPermission(Manifest.permission.CAMERA, REQUEST_IMAGE_CAPTURE, call);
    }

    private void askForPermission(String permission, int requestCode, PluginCall call) {
        if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
        } else {
            // Permission already granted
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                launchCamera(call);
            }
        }
    }

    private void launchCamera(PluginCall call) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(call, takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } else {
            Log.e(TAG, "Unable to open camera: no activity found to handle ACTION_IMAGE_CAPTURE intent");
            call.reject("Unable to open camera");
        }
    }

    @Override
    protected void handleOnActivityResult(int requestCode, int resultCode, Intent data) {
        super.handleOnActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == getActivity().RESULT_OK) {
                Uri imageUri = data.getData();
                JSObject ret = new JSObject();
                ret.put("imageUri", imageUri.toString());
                call.resolve(ret);
            } else {
                Log.e(TAG, "Image capture failed");
                call.reject("Image capture failed");
            }
        }
    }

    @Override
    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionResult(requestCode, permissions, grantResults);
        PluginCall savedCall = getSavedCall();
        if (savedCall == null) {
            return;
        }

        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                savedCall.reject("Permission denied");
                return;
            }
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            launchCamera(savedCall);
        }
    }

    //ùùùùùùùùùùùùùùùùùùùùùùùùùùùùùùùùùùùùùùùùùù

    /*@Override
    public void load() {
        Log.d(TAG, "Initializing DocumentScannerPlugin");
    }

    @CapacitorPlugin(name = "echo")
    public void echo(PluginCall call) {
        String value = call.getString("value");
        call.resolve(value);
    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    @CapacitorPlugin(name = "startScanning", permissions = Manifest.permission.CAMERA)
    @PluginMethod
    public <CallbackInterface> void startScanning(PluginCall call, CallbackInterface callback) {
        if  (getContext().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)  {
            requestPermissions(call, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
            return;
        }

        cameraPreview = new CameraPreview(getContext());

        // Optional: Set camera options (refer to camera-preview plugin documentation)
        // cameraPreview.setCameraOptions(...);

        cameraPreview.setCameraFrameListener((frame -> {
            // Apply image processing for document detection (using frame.getData())
            boolean isDocument = detectDocument(frame.getData());

            if (isDocument && !isDocumentDetected) {
                isDocumentDetected = true;
                // Trigger user validation (e.g., display a confirmation prompt)

                // Here, you can display an alert or use a different UI element for confirmation
                // based on your preference (replace with your UI implementation):
                getActivity().runOnUiThread(() -> {
                    new androidx.appcompat.app.AlertDialog.Builder(getActivity())
                            .setTitle("Document Detected")
                            .setMessage("Do you want to capture this document?")
                            .setPositiveButton("Yes", (dialog, which) -> {
                                // Capture the frame on confirmation
                                byte[] capturedFrame = cameraPreview.captureFrame();
                                // ... (Process the captured frame for further actions)
                                callback.success("Document captured!");
                                isDocumentDetected = false; // Reset for continuous scanning
                            })
                            .setNegativeButton("No", (dialog, which) -> {
                                // Reset document detection flag for continuous scanning
                                isDocumentDetected = false;
                            })
                            .setCancelable(false) // Prevent dismissal without confirmation
                            .show();
                });
            }
        }));

        cameraPreview.startPreview();

        callback.success("Scanning started");
    }

    @Override
    public void handleRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.handleRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with scanning
                startScanning(getCurrentRequest(), getCurrentCallback());
            } else {
                // Permission denied, inform user
                PluginCall call = getCurrentRequest();
                CallbackInterface callback = getCurrentCallback();
                callback.error("Camera permission is required for document scanning");
            }
        }
    }



    // Document detection method
    private boolean detectDocument(byte[] frameData) {
           // Convert byte[] to Mat
    Mat frame = new Mat(frameData.length / 4, 1, CvType.CV_8UC4);
    frame.put(0, 0, frameData);

    // Apply grayscale conversion for simplified processing
    Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);

    // Apply adaptive thresholding to enhance document edges
    Imgproc.adaptiveThreshold(frame, frame, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C,
                              Imgproc.THRESH_BINARY, 15, 2);

    // Apply morphological operations (optional) to refine edges and remove noise
    // Consider using erode and dilate operations for smoother results

    // Contour detection to find potential document shapes
    List<MatOfPoint> contours = new ArrayList<>();
    Imgproc.findContours(frame, contours, new Mat(), Imgproc.RETR_EXTERNAL,
                          Imgproc.CHAIN_APPROX_SIMPLE);

    // Iterate through contours and apply filtering criteria based on image logic
    for (MatOfPoint contour : contours) {
        Rect boundingRect = Imgproc.boundingRect(contour);

        // Approximate contour to a quadrilateral (potential document shape)
        double[] approx = new double[8];
        MatOfPoint2f approxCurve = new MatOfPoint2f(contour.toArray());
        Imgproc.approxPolyDP(approxCurve, approxCurve, 0.02 * Imgproc.arcLength(approxCurve, true), true);

        // Check criteria based on image logic (replace with your conditions):
        if (approxCurve.toArray().length == 4 &&
            boundingRect.width > 100 && boundingRect.height > 100 &&
            // ... Other conditions for aspect ratio, area, etc.
        ) {
            return true; // Document detected!
        }
    }

    return false; // No document detected
}
*/





    //ùùùùùùùùùùùùùùùùùùùùùùùùùùùùùùùùùù

    /*@RequiresApi(api = Build.VERSION_CODES.M)
    @PluginMethod
    public void openCamera(PluginCall call) {
        if (!hasRequiredPermissions()) {
            requestPermissionForCamera(call);
        } else {
            openLiveView(call);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean hasRequiredPermissions() {
        return getContext().checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissionForCamera(PluginCall call) {
        pluginRequestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        saveCall(call);
    }

    @Override
    protected void handleRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.handleRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            PluginCall savedCall = getSavedCall();
            if (savedCall == null) {
                Log.w(TAG, "No stored plugin call for camera permission request");
                return;
            }

            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_DENIED) {
                    savedCall.reject("Permission denied");
                    return;
                }
            }

            openLiveView(savedCall);
        }
    }

    
    
        

    @PluginMethod
    public void openLiveView(PluginCall call) {
        PreviewView previewView = new PreviewView(getContext());
        getActivity().addContentView(previewView, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        Executor executor = Executors.newSingleThreadExecutor();

        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(getContext());
        cameraProviderFuture.addListener(() -> {
            try {
                cameraProvider = cameraProviderFuture.get();

                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build();

                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle((LifecycleOwner) getContext(), cameraSelector, preview);

            } catch (ExecutionException | InterruptedException e) {
                Log.e(TAG, "Failed to get camera provider", e);
                call.reject("Failed to get camera provider");
            }
        }, executor);
    }

    @Override
    protected void handleOnDestroy() {
        super.handleOnDestroy();
        // Shut down the camera executor
        if (cameraExecutor != null) {
            cameraExecutor.shutdown();
        }
    }*/




    //****************************** */
    //$$$$$$$$$$$$$$$$$$$$$$

    /*@Override
    protected void handleOnStop() {
        super.handleOnStop();
        stopCamera();
    }

    private void initializeViewFinder() {
        // Créer un nouveau TextureView
        viewFinder = new TextureView(getContext());

        // Ajouter viewFinder à la hiérarchie des vues
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        getActivity().addContentView(viewFinder, params);

        // Assurez-vous que viewFinder est visible
        viewFinder.setVisibility(View.VISIBLE);
    }





    @RequiresApi(api = Build.VERSION_CODES.M)
    @PluginMethod
    public void openCamera(PluginCall call) {
        if (getContext().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionForCamera(call);
        } else {
            startCamera(call);
        }
    }

    private void requestPermissionForCamera(PluginCall call) {
        askForPermission(Manifest.permission.CAMERA, REQUEST_IMAGE_CAPTURE, call);
    }

    private void askForPermission(String permission, int requestCode, PluginCall call) {
        if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
        } else {
            // Permission already granted
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                startCamera(call);
            }
        }
    }

    private void startCamera(PluginCall call) {
        // Initialiser l'exécuteur de la caméra
        cameraExecutor = Executors.newSingleThreadExecutor();
    
        // Configurer les futures de CameraX
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(getContext());
    
        // Ajouter un écouteur pour les futures de CameraX
        cameraProviderFuture.addListener(() -> {
            try {
                // Obtenir le fournisseur de caméra à partir du futur
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
    
                // Créer un aperçu
                Preview preview = new Preview.Builder().build();
    
                // Configurer l'aperçu pour utiliser le surfaceProvider
                preview.setSurfaceProvider(surfaceProvider);
    
                // Créer un sélecteur de caméra par défaut (arrière)
                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build();
    
                // Lier la caméra au cycle de vie de l'activité et à l'aperçu
                cameraProvider.bindToLifecycle((LifecycleOwner) getActivity(), cameraSelector, preview);
    
                // Ajouter viewFinder à votre layout sur le thread principal
                getActivity().runOnUiThread(() -> {
                    initializeViewFinder(); // Méthode pour ajouter viewFinder à votre layout
                });
    
                // Résoudre l'appel si tout s'est bien passé
                call.resolve();
            } catch (ExecutionException | InterruptedException e) {
                Log.e(TAG, "Error starting camera", e);
                call.reject("Error starting camera");
            }
        }, ContextCompat.getMainExecutor(getContext()));
    }
    
    
    private void stopCamera() {
        if (cameraExecutor != null) {
            cameraExecutor.shutdown();
            cameraExecutor = null;
        }
    }

    @Override
    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionResult(requestCode, permissions, grantResults);
        PluginCall savedCall = getSavedCall();
        if (savedCall == null) {
            return;
        }

        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                savedCall.reject("Permission denied");
                return;
            }
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            startCamera(savedCall);
        }
    }*/

    //********************************** */


    //************************************************************************************* */

    /*@RequiresApi(api = Build.VERSION_CODES.M)
    @PluginMethod
    public void openCamera(PluginCall call) {
        if (getContext().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionForCamera(call);
        } else {
            startCameraPreview(call);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @PluginMethod
    private void startCameraPreview(PluginCall call) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Initialiser et démarrer la prévisualisation de la caméra ici
                // Assurez-vous de gérer les autorisations nécessaires

                        // Vérifier si la permission de la caméra est accordée
                if (getContext().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // Demander la permission si elle n'est pas accordée
                    requestPermissionForCamera(call);
                    return;
                }
                
                // Initialiser la caméra et la surface de prévisualisation
                CameraManager cameraManager = (CameraManager) getContext().getSystemService(Context.CAMERA_SERVICE);
                try {
                    String cameraId = cameraManager.getCameraIdList()[0]; // Utilisez la première caméra disponible
                    Camera camera = Camera.open(Integer.parseInt(cameraId));
            
                    // Créer une surface de prévisualisation pour la caméra
                    SurfaceView previewSurface = new SurfaceView(getContext());
            
                    // Ajouter la surface de prévisualisation à la vue parente
                    getBridge().getActivity().addContentView(previewSurface, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            
                    // Démarrer la prévisualisation de la caméra
                    camera.setPreviewDisplay(previewSurface.getHolder());
                    camera.startPreview();
            
                } catch (CameraAccessException | NumberFormatException | IOException e) {
                    e.printStackTrace();
                    // Gérer les erreurs d'accès à la caméra ou de démarrage de la prévisualisation
                    call.reject("Failed to start camera preview: " + e.getMessage());
                }
                    }
                });
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPermissionForCamera(PluginCall call) {
        askForPermission(Manifest.permission.CAMERA, REQUEST_IMAGE_CAPTURE, call);
    }
    
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void askForPermission(String permission, int requestCode, PluginCall call) {
        if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
        } else {
            // La permission est déjà accordée
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                startCameraPreview(call);
            }
        }
    }
    
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionResult(requestCode, permissions, grantResults);
        PluginCall savedCall = getSavedCall();
        if (savedCall == null) {
            return;
        }
    
        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                savedCall.reject("Permission denied");
                return;
            }
        }
    
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            startCameraPreview(savedCall);
        }
    }*/



    //*************************************** */





    



   

    




    




   /* @Override
    protected void handleOnActivityResult(int requestCode, int resultCode, Intent data) {
        super.handleOnActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST) {
            PluginCall call = null;
            if (resultCode == getActivity().RESULT_OK && data != null) {
                // Photo captured successfully
                JSObject result = new JSObject();
                result.put("success", true);
                result.put("imageUri", data.getData().toString());
                call.resolve(result);
            } else {
                // Failed to capture photo
                call.reject("Failed to capture photo");
            }
        }
    }*/


    





    

    //@PluginMethod
    //public void captureImage(PluginCall call) {
        //String imagePath = // Your image capture logic here
        //JSObject result = new JSObject();
      //  result.put("imagePath", imagePath);
       // call.resolve(result);

       // @PluginMethod
        //public void captureImage(PluginCall call) {
            // Implement image capture logic using Android Camera API
            // For simplicity, returning a mock image path
           // String imagePath = "/path/to/captured/image.jpg";
    
            //JSObject result = new JSObject();
            //result.put("imagePath", imagePath);
            //call.resolve(result);
        //}
        
    //}


    /*@PluginMethod()
        public void captureImage(PluginCall call) {
        call.resolve();
    }

    @PluginMethod()
    public void detectEdges(PluginCall call) {
        call.resolve();
    }

    @PluginMethod()
    public void convertToPDF(PluginCall call) {
        call.resolve();
    }

    @PluginMethod()
    public void uploadDocument(PluginCall call) {
        call.resolve();
    }*/


}
