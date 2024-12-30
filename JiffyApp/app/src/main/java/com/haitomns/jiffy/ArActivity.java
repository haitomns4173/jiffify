package com.haitomns.jiffy;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.io.IOException;
import java.io.InputStream;

public class ArActivity extends AppCompatActivity {

    private ArFragment arFragment;
    private TextView foodDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);

        arFragment = (ArFragment) ((Object)getSupportFragmentManager().findFragmentById(R.id.arFragment));
        foodDetails = findViewById(R.id.food_details);
        foodDetails.setText("Burger\nPrice: $5.99");

        if (isFileExists(this, "burger.glb")) {
            Toast.makeText(this, "File found: burger.glb", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "File not found: burger.glb", Toast.LENGTH_SHORT).show();
        }

        arFragment.setOnTapArPlaneListener((HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
            Anchor anchor = hitResult.createAnchor();
            placeObject(arFragment, anchor, Uri.parse("burger.glb"));
            Toast.makeText(this, "Object placed", Toast.LENGTH_SHORT).show();
        });
    }

    private void placeObject(ArFragment arFragment, Anchor anchor, Uri modelUri) {
        ModelRenderable.builder()
                .setSource(arFragment.getContext(), modelUri)
                .build()
                .thenAccept(modelRenderable -> addNodeToScene(arFragment, anchor, modelRenderable));
    }

    private void addNodeToScene(ArFragment arFragment, Anchor anchor, ModelRenderable modelRenderable) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        TransformableNode node = new TransformableNode(arFragment.getTransformationSystem());
        node.setParent(anchorNode);
        node.setRenderable(modelRenderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        node.select();
    }

    // Utility method to check if a file exists in the assets directory
    private boolean isFileExists(Context context, String fileName) {
        try (InputStream inputStream = context.getAssets().open(fileName)) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}