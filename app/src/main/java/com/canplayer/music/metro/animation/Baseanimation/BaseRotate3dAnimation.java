package com.canplayer.music.metro.animation.Baseanimation;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

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
    float scale = 1;    // 像素密度

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
        scale =context.getResources().getDisplayMetrics().density;
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
        matrix.preTranslate(-centerX, -centerY); // 使用pre将旋转中心移动到和Camera位置相同
        matrix.postTranslate(centerX, centerY);  // 移动并修正位置
    }
}
