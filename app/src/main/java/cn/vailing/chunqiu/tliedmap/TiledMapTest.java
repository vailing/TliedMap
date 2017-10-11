package cn.vailing.chunqiu.tliedmap;


import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXProperties;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.extension.tmx.TMXTileProperty;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by dream on 2017/7/13.
 */

public class TiledMapTest extends SimpleBaseGameActivity {
    private static int CAMERA_WIDTH = 480;
    private static int CAMERA_HEIGHT = 640;
    private Camera mCamera;
    private TMXTiledMap mWAVTMXMap;
    private TMXLayer tmxLayer;
    private TMXTile tmxTile;
    private List<Integer> idList;
    private List<Integer> indexList;
    private int[][] location;
    private int count;
    public synchronized void onResumeGame() {
        if (this.mEngine != null)
            super.onResumeGame();
    }
    @Override
    public EngineOptions onCreateEngineOptions() {
        ScaleHelper.getInstance().init(this);
        idList = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            idList.add(i + 1);
        }
        indexList = new ArrayList<>();
        indexList.add(0);
        indexList.add(4);
        indexList.add(8);
        indexList.add(12);
        indexList.add(1);
        indexList.add(5);
        indexList.add(9);
        indexList.add(13);
        indexList.add(2);
        indexList.add(6);
        indexList.add(10);
        indexList.add(14);
        indexList.add(3);
        indexList.add(7);
        indexList.add(11);
        indexList.add(15);
        CAMERA_WIDTH = ScaleHelper.getInstance().getAppWidth();
        CAMERA_HEIGHT = ScaleHelper.getInstance().getAppHeight();
        location = new int[32][18];
        mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,
                new FillResolutionPolicy(), mCamera);
        return engineOptions;

    }

    @Override
    public void onCreateResources() {

    }

    @Override
    public Scene onCreateScene() {
        mEngine.registerUpdateHandler(new FPSLogger());
        Scene scene = new Scene();
        scene.setBackground(new Background(1f, 1f, 1f));
        try {
            TMXLoader tmxLoader = new TMXLoader(this.getAssets(), this.getTextureManager(), TextureOptions.BILINEAR_PREMULTIPLYALPHA, this.getVertexBufferObjectManager(), new TMXLoader.ITMXTilePropertiesListener() {
                @Override
                public void onTMXTileWithPropertiesCreated(final TMXTiledMap pTMXTiledMap, final TMXLayer pTMXLayer, final TMXTile pTMXTile, final TMXProperties<TMXTileProperty> pTMXTileProperties) {
                }
            });
            mWAVTMXMap = tmxLoader.loadFromAsset("dark.tmx");

        } catch (TMXLoadException tmxle) {
            tmxle.printStackTrace();
        }
        tmxLayer = mWAVTMXMap.getTMXLayers().get(0);
        tmxLayer.setX(ScaleHelper.getInstance().getXLocation(0, 1920));
        tmxLayer.setY(ScaleHelper.getInstance().getYLocation(0, 1080));
        tmxLayer.setScale(ScaleHelper.getInstance().getWidthScale(), ScaleHelper.getInstance().getHeightScale());
        scene.attachChild(tmxLayer);
        scene.setOnSceneTouchListener(new IOnSceneTouchListener() {
            @Override
            public boolean onSceneTouchEvent(Scene scene, TouchEvent touchEvent) {
//                (奇数，奇数)（i,j) (i+1,j) (i,j+1) (i+1,j+1)
//                (奇数，偶数) (i,j+1) (i+1,j+1)
//                (偶数，偶数) (i+1,j+1)
//                (偶数，奇数) (i+1,j) (i+1,j+1)
                int column = ((int) ((touchEvent.getX()) / (120 * ScaleHelper.getInstance().getWidthScale())));
                int row = ((int) ((touchEvent.getY() ) / (120 * ScaleHelper.getInstance().getHeightScale())));
                int columnIndex = column * 2;
                int rowIndex = row * 2;
                if (columnIndex + 1 < 32 && rowIndex + 1 < 18) {
                    location[columnIndex + 1][rowIndex + 1] = 4;
                }
                if (columnIndex + 2 < 32 && rowIndex + 2 < 18) {
                    location[columnIndex + 2][rowIndex + 2] = 2;
                }
                if (columnIndex + 2 < 32 && rowIndex + 1 < 18) {
                    location[columnIndex + 2][rowIndex + 1] = 8;
                }
                if (columnIndex + 1 < 32 && rowIndex + 2 < 18) {
                    location[columnIndex + 1][rowIndex + 2] = 1;
                }
                if (column < 16 && row < 9) {
                    tmxTile = tmxLayer.getTMXTile(column, row);
                    count = MathUtil.addAllRect(location, 1, columnIndex, rowIndex);
                    if (count > 16)
                        count = 15;
                    tmxTile.setGlobalTileID(mWAVTMXMap, idList.get(indexList.indexOf(count)));
                }
                if (column + 1 < 16&& row < 9) {
                    tmxTile = tmxLayer.getTMXTile(column + 1, row);
                    count = MathUtil.addAllRect(location, 2, columnIndex, rowIndex);
                    if (count > 16)
                        count = 15;
                    tmxTile.setGlobalTileID(mWAVTMXMap, idList.get(indexList.indexOf(count)));
                }
                if (column < 16 && row + 1 < 9) {
                    count = MathUtil.addAllRect(location, 3, columnIndex, rowIndex);
                    tmxTile = tmxLayer.getTMXTile(column, row + 1);
                    if (count > 16)
                        count = 15;
                    tmxTile.setGlobalTileID(mWAVTMXMap, idList.get(indexList.indexOf(count)));
                }
                if (column + 1 < 16 && row + 1 < 9) {
                    count = MathUtil.addAllRect(location, 4, columnIndex, rowIndex);
                    tmxTile = tmxLayer.getTMXTile(column + 1, row + 1);
                    if (count > 16)
                        count = 15;
                    tmxTile.setGlobalTileID(mWAVTMXMap, idList.get(indexList.indexOf(count)));
                }
                return true;
            }
        });
        return scene;
    }
}
