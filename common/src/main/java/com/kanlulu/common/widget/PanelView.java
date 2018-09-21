package com.kanlulu.common.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.kanlulu.common.R;
import com.kanlulu.common.tools.CommonDataManager;
import com.kanlulu.common.utils.PixelUtils;

/**
 * 首页显示信用额度的仪表盘
 * Created by lingxiaoming on 2017/7/31 0031.
 */

public class PanelView extends View {
    private float angle = 240;//仪表盘的幅度角
    private Paint mPaint;//画笔
    private int segmentLenth;//刻度线段长度
    private int segmentPadding;//刻度线段距离外边圆圈的间距
    private int drawalePadding;//左右上 的留边距，供指标显示
    private int paddingLeftRight;//整体的左右间距
    private int radius;//半径

    private float progress;//当前显示进度(76%)
    private Bitmap drawableBitmap;
    private int maxSegment = 5000;//最大刻度值
    //    private int colorGray = 0xff95a5b8;//灰色
//    private int colorBlue = 0xff4a90e2;//蓝色
//    private int colorRed = 0xfff9542d;//红色
    private int[] centerPoint = new int[2];
    private boolean isErrorProgress = false;
    private int canUseNumber = -1;


    public PanelView(Context context) {
        super(context);
        init();
    }

    public PanelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PanelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        segmentLenth = PixelUtils.dp2px(getContext(), 10);
        segmentPadding = PixelUtils.dp2px(getContext(), 10);
        drawalePadding = PixelUtils.dp2px(getContext(), 10);

        progress = 0.0f;

        drawableBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.icon_progress);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setDither(true);//抖动
        mPaint.setStrokeWidth(4);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        radius = (getHeight() - drawalePadding * 2) * 2 / 3;//半径根据高度计算，但是我们必须保证高度是小于宽度的
        paddingLeftRight = getWidth() / 2 - radius - drawalePadding;

        centerPoint[0] = getWidth() / 2;
        centerPoint[1] = radius + drawalePadding;

        paintCircle(canvas);//绘制外圆背景
        paintProgress(canvas);//绘制外圆进度和图标进度显示

        paintSurroundLine(canvas);//绘制刻度线段

        paintSegmentTextView(canvas);//绘制刻度文字

        if (progress > 0 && progress < 1 && canUseNumber != -1) {
            drawProgressText(canvas);//绘制动态进度文字
        }

//        if (progress > 0 && progress <= 1 && canUseNumber != -1) {
//            paintCanUseLine(canvas);
//        }

    }

    /**
     * 绘制动态进度文字
     *
     * @param canvas
     */
    private void drawProgressText(Canvas canvas) {
        mPaint.setColor(0xfff9542d);
        float textSize = PixelUtils.dp2px(getContext(), 12);
        mPaint.setTextSize(textSize);
        mPaint.setTypeface(Typeface.DEFAULT);

        if (CommonDataManager.getUser() != null) {
            canvas.save();
            float criAngle = 150 + (progress * angle);
            int[] point = getPointFromAngleAndRadius((int) criAngle, radius - drawalePadding - segmentPadding - PixelUtils.dp2px(getContext(), 8));
            canvas.rotate(criAngle + 90, point[0], point[1]);
            float test = mPaint.measureText(canUseNumber + "");
            canvas.drawText(canUseNumber + "", point[0] - test / 2, point[1] + PixelUtils.dp2px(getContext(), 6), mPaint);
            canvas.restore();
        }
    }

    /**
     * 画刻度 可使用信用额度的进度
     *
     * @param canvas
     */
    private void paintCanUseLine(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth() / 2, radius + drawalePadding);
        canvas.rotate(270 - angle / 2 - 0);

        mPaint.setColor(0xff459ACA);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(2);

        for (int i = 0; i <= (angle / 2) * progress; i++) {
            if (i <= angle / 2) {
                canvas.drawLine(radius - segmentPadding - segmentLenth, 0, radius - segmentPadding, 0, mPaint);
                canvas.rotate(2);
            }
        }
        canvas.restore();
    }

    /**
     * 画背景外圈
     *
     * @param canvas
     */
    private void paintCircle(Canvas canvas) {
        mPaint.setColor(0xffE7ECF4);
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        RectF rectF = new RectF(paddingLeftRight + drawalePadding, drawalePadding, paddingLeftRight + drawalePadding + radius * 2, radius * 2 + drawalePadding);
        canvas.drawArc(rectF, 270 - angle / 2, angle, false, mPaint);

//        canvas.drawPoint(centerPoint[0], centerPoint[1],mPaint);//TODO 测试，画圆点
    }

    /**
     * 画进度，包括进度图标圆点
     *
     * @param canvas
     */
    private void paintProgress(Canvas canvas) {
        mPaint.setColor(0xff459ACA);
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        RectF rectF = new RectF(paddingLeftRight + drawalePadding, drawalePadding, paddingLeftRight + drawalePadding + radius * 2, radius * 2 + drawalePadding);
        canvas.drawArc(rectF, 270 - angle / 2, progress < 0 ? 0 : progress * angle, false, mPaint);


        double agle = ((progress < 0 ? 0 : progress * angle) + 90 - angle / 2) * Math.PI / 180;//旋转的角度
        double x = getWidth() / 2 - radius * Math.cos(agle) - drawableBitmap.getWidth() / 2;
        double y = radius + drawalePadding - radius * Math.sin(agle) - drawableBitmap.getHeight() / 2;

        Matrix matrix = new Matrix();
        matrix.setTranslate((float) x, (float) y);
        matrix.preTranslate(drawableBitmap.getWidth() / 2, drawableBitmap.getHeight() / 2);
        matrix.preRotate((progress < 0 ? 0 : progress * angle) + 90 - angle / 2);
        matrix.preTranslate(-drawableBitmap.getWidth() / 2, -drawableBitmap.getHeight() / 2);

        canvas.drawBitmap(drawableBitmap, matrix, mPaint);
    }


    /**
     * 画内圈刻度断线
     *
     * @param canvas
     */
    private void paintSurroundLine(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth() / 2, radius + drawalePadding);
        canvas.rotate(270 - angle / 2 - 0);

        mPaint.setColor(0xFFE1EFF7);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(2);

        for (int i = 0; i <= angle / 2 + 0; i++) {
//            canvas.drawPoint(radius - segmentPadding - segmentLenth, 0, mPaint);
            canvas.drawLine(radius - segmentPadding - segmentLenth, 0, radius - segmentPadding, 0, mPaint);
            canvas.rotate(2);
        }
        canvas.restore();
    }

    /**
     * 画刻标、中间文字
     *
     * @param canvas
     */
    private void paintSegmentTextView(Canvas canvas) {
        mPaint.setColor(0xfff9542d);
        float textSize = PixelUtils.dp2px(getContext(), 12);
        mPaint.setTextSize(textSize);
        mPaint.setTypeface(Typeface.DEFAULT);

        float textWidthMax = mPaint.measureText(maxSegment + "");
//        float textWidthMaxHalf = mPaint.measureText(maxSegment / 2 + "");
        canvas.drawText(maxSegment + "", getWidth() / 2 - segmentLenth - segmentPadding - textWidthMax + (float) Math.cos((float) (((angle - 180) / 2) * Math.PI / 180)) * radius, getHeight() - textSize, mPaint);
//        canvas.drawText(maxSegment / 2 + "", getWidth() / 2 - textWidthMaxHalf / 2, drawalePadding + segmentPadding + segmentLenth + textSize, mPaint);
        canvas.drawText("0", getWidth() / 2 + segmentPadding + segmentLenth - (float) Math.cos((float) (((angle - 180) / 2) * Math.PI / 180)) * radius, getHeight() - textSize, mPaint);

//        canvas.drawText(progress,);
        textSize = PixelUtils.dp2px(getContext(), 14);
        mPaint.setColor(0xff95a5b8);
        mPaint.setTextSize(textSize);
        float textWarn = mPaint.measureText("信用额度");
        canvas.drawText("信用额度", getWidth() / 2 - textWarn / 2, drawalePadding + radius - PixelUtils.dp2px(getContext(), 30), mPaint);

        textSize = PixelUtils.dp2px(getContext(), 24);
        mPaint.setColor(0xfff9542d);
        mPaint.setTextSize(textSize);
//        String progressStr = (int) (progress * maxSegment) + "";
        String progressStr = maxSegment + "";
        if (progress < 0 || progress > 1 || canUseNumber == -1) {
            progressStr = "待评估";
            mPaint.setColor(0xff95a5b8);
        }

        float textWarn2 = mPaint.measureText(progressStr);
        canvas.drawText(progressStr, getWidth() / 2 - textWarn2 / 2, drawalePadding + radius + textSize, mPaint);
    }


    /**
     * [0, maxSegment] 之间，不需要外部转换百分比
     * 得出的结果是[0,1]小数
     *
     * @param progress
     */
    public void setProgress(float progress) {
        canUseNumber = (int) progress;
        if (maxSegment == 0) {
            isErrorProgress = false;
            this.progress = 0;
        } else if (progress > 0) {
            isErrorProgress = false;
            this.progress = progress / maxSegment;
        } else if (progress < 0) {
            isErrorProgress = true;
            this.progress = 1.001F;
        } else if (progress == 0.00f) {
            isErrorProgress = false;
            this.progress = 0f;
        }
        invalidate();
    }

    public void setMaxProgress(int max) {
        this.maxSegment = max;
    }

    /**
     * 根据角度和半径，求一个点的坐标
     *
     * @param angle
     * @param radius
     * @return
     */
    private int[] getPointFromAngleAndRadius(int angle, int radius) {
        double x = radius * Math.cos(angle * Math.PI / 180) + centerPoint[0];
        double y = radius * Math.sin(angle * Math.PI / 180) + centerPoint[1];
        return new int[]{(int) x, (int) y};
    }
}
