package com.canplayer.music.metro.animation.Baseanimation;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;

import com.canplayer.music.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * 一个动画，它将Y轴上的视图在两个指定角度之间旋转。 此动画还在Z轴（深度）上添加了平移以改善效果
 */
public class BaseRotate3dAnimation extends Animation {
    private final float mFromDegreesX;
    private final float mToDegreesX;
    private final float mFromDegreesY;
    private final float mToDegreesY;
    private final float mFromDegreesZ;
    private final float mToDegreesZ;
    private final float mCenterX;
    private final float mCenterY;
    private final float mDepthZ;
    private final boolean mReverse;
    private Camera mCamera;
    private float scale = 1;    // 像素密度

    /**
     * 创建一个绕 y 轴旋转的3D动画效果，旋转过程中具有深度调节，可以指定旋转中心。
     *
     * @param context     上下文,用于获取像素密度
     * @param fromDegrees 起始时角度
     * @param toDegrees   结束时角度
     * @param centerX     旋转中心x坐标
     * @param centerY     旋转中心y坐标
     * @param depthZ      最远到达的z轴坐标
     * @param reverse     true 表示由从0到depthZ，false相反
     */
//    public BaseRotate3dAnimation(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        TypedArray a = context.obtainStyledAttributes(attrs, com.canplayer.music.R.styleable.BaseRotate3dAnimation);
//
//        mFromDegreesX = a.getFloat(R.styleable.BaseRotate3dAnimation_fromDegreesX, 0.0f);
//        mFromDegreesY = a.getFloat(R.styleable.BaseRotate3dAnimation_fromDegreesY, 0.0f);
//        mFromDegreesZ = a.getFloat(R.styleable.BaseRotate3dAnimation_fromDegreesZ, 0.0f);
//        mToDegreesX = a.getFloat(R.styleable.BaseRotate3dAnimation_toDegreesX, 0.0f);
//        mToDegreesY = a.getFloat(R.styleable.BaseRotate3dAnimation_toDegreesY, 0.0f);
//        mToDegreesZ = a.getFloat(R.styleable.BaseRotate3dAnimation_toDegreesZ, 0.0f);
//        mCenterX = a.getFloat(R.styleable.BaseRotate3dAnimation_centerX, 0.0f);
//        mCenterY = a.getFloat(R.styleable.BaseRotate3dAnimation_centerY, 0.0f);
//        mDepthZ = a.getFloat(R.styleable.BaseRotate3dAnimation_depthZ, 0.0f);
//        mReverse = a.getBoolean(R.styleable.BaseRotate3dAnimation_reverse, false);
//
//        a.recycle();
//    }
//



    @Deprecated
    public BaseRotate3dAnimation(float[] fromDegrees, float[] toDegrees,
                                 float centerX, float centerY, float depthZ, boolean reverse, Context context) {
        mFromDegreesX = fromDegrees[0];
        mToDegreesX = toDegrees[0];
        mFromDegreesY = fromDegrees[1];
        mToDegreesY = toDegrees[1];
        mFromDegreesZ = fromDegrees[0];
        mToDegreesZ = toDegrees[0];
        mCenterX = centerX;
        mCenterY = centerY;
        mDepthZ = depthZ;
        mReverse = reverse;
        // 获取手机像素密度 （即dp与px的比例）
        scale = context.getResources().getDisplayMetrics().density*2.8f;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float degreesX = mFromDegreesX + ((mToDegreesX - mFromDegreesX) * interpolatedTime);
        float degreesY = mFromDegreesY + ((mToDegreesY - mFromDegreesY) * interpolatedTime);
        float degreesZ = mFromDegreesZ + ((mToDegreesZ - mFromDegreesZ) * interpolatedTime);

        final float centerX = mCenterX;
        final float centerY = mCenterY;
        final Camera camera = mCamera;
        final Matrix matrix = t.getMatrix();

        camera.save();
        // 摄像机
        if (mReverse) {
            camera.translate(0.0f, 0.0f, mDepthZ * interpolatedTime);
        } else {
            camera.translate(0.0f, 0.0f, mDepthZ * (1.0f - interpolatedTime));
        }
        // 绕y轴旋转
        camera.rotate(degreesX,degreesY,degreesZ);
        camera.getMatrix(matrix);
        camera.restore();

        // 修正失真
        float[] mValues = new float[9];
        matrix.getValues(mValues);                //获取数值
        mValues[6] = mValues[6] / scale;            //数值修正
        mValues[7] = mValues[7] / scale;            //数值修正
        matrix.setValues(mValues);                //重新赋值

        // 调节中心点，旋转中心默认是坐标原点，对于图片来说就是左上角位置。
        //matrix.preTranslate(-centerX, -centerY); // 移动Camera位置（会导致画面偏移）
        //matrix.postTranslate(centerX, centerY);  // 修正移动Camera产生的位移

        matrix.preTranslate(-centerX, -centerY); // 移动Camera位置（会导致画面偏移）
        matrix.postTranslate(centerX, centerY);  // 修正移动Camera产生的位移
    }







//    public static Animation loadAnimation(Context context, int id) throws Resources.NotFoundException {
//
//        XmlResourceParser parser = null;
//        try {
//            parser = context.getResources().getAnimation(id);
//            return createAnimationFromXml(context, parser, null,
//                    Xml.asAttributeSet(parser));
//        } catch (XmlPullParserException ex) {
//            Resources.NotFoundException rnf = new Resources.NotFoundException(
//                    "Can't load animation resource ID #0x"
//                            + Integer.toHexString(id));
//            rnf.initCause(ex);
//            throw rnf;
//        } catch (IOException ex) {
//            Resources.NotFoundException rnf = new Resources.NotFoundException(
//                    "Can't load animation resource ID #0x"
//                            + Integer.toHexString(id));
//            rnf.initCause(ex);
//            throw rnf;
//        } finally {
//            if (parser != null)
//                parser.close();
//        }
//    }
//
//    private static Animation createAnimationFromXml(Context c, XmlPullParser parser, AnimationSet parent, AttributeSet attrs) throws XmlPullParserException, IOException {
//        Animation anim = null;
//        // Make sure we are on a start tag.
//        int type;
//        int depth = parser.getDepth();
//        while (((type = parser.next()) != XmlPullParser.END_TAG || parser
//                .getDepth() > depth) && type != XmlPullParser.END_DOCUMENT) {
//            if (type != XmlPullParser.START_TAG) {
//                continue;
//            }
//            String name = parser.getName();
//            if (name.equals("set")) {
//                anim = new AnimationSet(c, attrs);
//                createAnimationFromXml(c, parser, (AnimationSet) anim, attrs);
//            } else if (name.equals("alpha")) {
//                anim = new AlphaAnimation(c, attrs);
//            } else if (name.equals("scale")) {
//                anim = new ScaleAnimation(c, attrs);
//            } else if (name.equals("rotate")) {
//                anim = new RotateAnimation(c, attrs);
//            } else if (name.equals("translate")) {
//                anim = new TranslateAnimation(c, attrs);
//            } else if (name.equals("cube")) {
//                anim = new BaseRotate3dAnimation(c, attrs);
//            } else {
//                throw new RuntimeException(
//                        "not a cubeanimation animation name: "
//                                + parser.getName());
//            }
//        }
//        if (parent != null) {
//            parent.addAnimation(anim);
//        }
//
//        return anim;
//
//    }










}
