package com.canplayer.music.metro.animation.Baseanimation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * 一个动画，它将Y轴上的视图在两个指定角度之间旋转。 此动画还在Z轴（深度）上添加了平移以改善效果
 */
public class Rotate3dAnimation extends Animation {
    private final float mFromDegrees;/*起始角度*/
    private final float mToDegrees;/*最终角度*/
    private final float mCenterX;/*旋转中心X*/
    private final float mCenterY;
    private final float mDepthZ;/*视差度*/
    private final boolean mReverse;/*反向运动*/
    private Camera mCamera;

    /**
     * 在Y轴上创建新的3D旋转。 旋转程度由其起始角度和终止角度定义。
     * 两个角度均以度为单位。 旋转是围绕2D空间上的中心点进行的。
     * 该中心点由一对名为centerX和centerY的X和Y坐标定义。
     * 动画开始时，将执行Z轴（深度）上的平移。 可以指定变换的长度。
     * 以及是否应及时撤消转换。
     * @param fromDegrees 旋转的起始角度
     * @param toDegrees   旋转的结束角度
     * @param centerX     旋转的X轴中心
     * @param centerY     旋转的Y中心
     * @param reverse     如果正向变换则为true，否则为false
     */
    public Rotate3dAnimation(float fromDegrees, float toDegrees, float centerX, float centerY, float depthZ, boolean reverse) {
        mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;
        mCenterX = centerX;
        mCenterY = centerY;
        mDepthZ = depthZ;
        mReverse = reverse;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final float fromDegrees = mFromDegrees;
        float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);
        final float centerX = mCenterX;
        final float centerY = mCenterY;
        final Camera camera = mCamera;
        final Matrix matrix = t.getMatrix();
        // 当前摄像头位置保存，以便完成后恢复原本的位置
        camera.save();
        // camera.translate，这个方法接受3个参数，分别是x,y,z三个轴的偏移量，我们这里只将z轴进行了偏移，
        if (mReverse) camera.translate(0.0f, 0.0f, mDepthZ * interpolatedTime);// z的偏移会越来越大。这就会形成这样一个效果，view从近到远
        else camera.translate(0.0f, 0.0f, mDepthZ * (1.0f - interpolatedTime));// z的偏移会越来越小。这就会形成这样一个效果，我们的View从一个很远的地方向我们移过来，越来越近，最终移到了我们的窗口上面～
        // 是给我们的View加上旋转效果，在移动的过程中，视图还会移Y轴为中心进行旋转。
        camera.rotateY(degrees);
        // 是给我们的View加上旋转效果，在移动的过程中，视图还会移X轴为中心进行旋转。
        // camera.rotateX(degrees);

        // 这个是将我们刚才定义的一系列变换应用到变换矩阵上面，调用完这句之后，我们就可以将camera的位置恢复了，以便下一次再使用。
        camera.getMatrix(matrix);
        // camera位置恢复
        camera.restore();
        // 以View的中心点为旋转中心,如果不加这两句，就是以（0,0）点为旋转中
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }
}
