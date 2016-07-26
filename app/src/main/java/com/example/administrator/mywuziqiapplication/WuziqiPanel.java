package com.example.administrator.mywuziqiapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-07-25.
 */
public class WuziqiPanel extends View {

    //最大行数
    public static final int MAX_LINE = 10;
    public static final String INSTANCE_WHITE_LIST = "whiteList";
    public static final String INSTANCE = "Instance";
    public static final String INSTANCE_IS_GAME_OVER = "Instance_isGameOver";
    public static final String INSTANCE_BLACK_LIST = "instance_Black_list";
    private float mRatioPieceLengthOfLineHeight = (3 * 1.0f / 4);
    //自定义View的宽
    private int mPanelWidth;
    //每行间隔高度
    private float mLineHeight;
    private Paint mPaint;
    private Bitmap mWhitePiece;
    private Bitmap mBlackPiece;

    //记录黑白子的位置
    ArrayList<Point> mWhiteList = new ArrayList<>();
    ArrayList<Point> mBlackList = new ArrayList<>();
    //是否白棋先手  或这轮到白棋
    private boolean mIsWhiteFirst = false;
    //是否胜利
    private boolean mIsGameOver = false;


    public WuziqiPanel(Context context) {
        this(context, null);
    }

    public WuziqiPanel(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WuziqiPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setBackgroundColor(Color.parseColor("#3ffd023d"));
        //初始化
        init();

    }

    private void init() {
        //画笔初始化
        mPaint = new Paint();
        //去锯齿
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#80000000"));

        //初始化黑白棋
        mWhitePiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_w2);
        mBlackPiece = BitmapFactory.decodeResource(getResources(), R.drawable.stone_b1);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i("aaaaa", "onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int length = Math.min(widthSize, heightSize);

        if (widthMode == MeasureSpec.UNSPECIFIED) {
            length = heightSize;
        } else if (heightMode == MeasureSpec.UNSPECIFIED) {
            length = widthSize;
        }
        //设置自定义View的大小
        setMeasuredDimension(length, length);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.i("aaaaa", "onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);

        mPanelWidth = w;
        mLineHeight = w * 1.0f / MAX_LINE;


        //设置棋子的大小不能超过格子
        int pieceLength = (int) (mLineHeight * mRatioPieceLengthOfLineHeight);

        mWhitePiece = Bitmap.createScaledBitmap(mWhitePiece, pieceLength, pieceLength, false);
        mBlackPiece = Bitmap.createScaledBitmap(mBlackPiece, pieceLength, pieceLength, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i("aaaaa", "onDraw");
        super.onDraw(canvas);
        //绘制棋盘
        drawBoard(canvas);
        //绘制棋子
        drawPiece(canvas);
        //检查游戏是否结束
        checkGameOver();
    }

    private void checkGameOver() {

        boolean whiteWin = checkFiveInLine(mWhiteList);
        boolean blackWin = checkFiveInLine(mBlackList);

        if (whiteWin || blackWin){
            mIsGameOver = true;

            String str = whiteWin?"白棋胜利":"黑棋胜利";
            Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkFiveInLine(List<Point> list) {

        for (Point p : list){
            if (checkHorizontalWin(list, p)) return true;
            if (checkVertical(list, p)) return true;
            if (checkSlantLeft(list, p)) return true;
            if (checkSlantRight(list, p)) return true;
        }
        return false;
    }

    private boolean checkSlantRight(List<Point> pointList, Point p) {
        int count = 1;
        for (int i = 1; i < 5; i++) {
            if (pointList.contains(new Point(p.x + i, p.y - i))){
                count++;
            }
            else{
                break;
            }
        }
        if (count == 5) {
            return true;
        }
        for (int i = 1; i < 5; i++) {
            if (pointList.contains(new Point(p.x - i, p.y + i))){
                count++;
            }
            else{
                break;
            }
        }
        if (count == 5) {
            return true;
        }
        return false;
    }

    private boolean checkSlantLeft(List<Point> pointList, Point p) {
        int count = 1;
        for (int i = 1; i < 5; i++) {
            if (pointList.contains(new Point(p.x - i, p.y - i))){
                count++;
            }
            else{
                break;
            }
        }
        if (count == 5) {
            return true;
        }
        for (int i = 1; i < 5; i++) {
            if (pointList.contains(new Point(p.x + i, p.y + i))){
                count++;
            }
            else{
                break;
            }
        }
        if (count == 5) {
            return true;
        }
        return false;
    }

    private boolean checkVertical(List<Point> pointList, Point p) {
        int count = 1;
        for (int i = 1; i < 5; i++) {
            if (pointList.contains(new Point(p.x, p.y - i))){
                count++;
            }
            else{
                break;
            }
        }
        if (count == 5) {
            return true;
        }
        for (int i = 1; i < 5; i++) {
            if (pointList.contains(new Point(p.x, p.y + i))){
                count++;
            }
            else{
                break;
            }
        }
        if (count == 5) {
            return true;
        }
        return false;
    }

    private boolean checkHorizontalWin(List<Point> pointList, Point p){
        int count = 1;
        for (int i = 1; i < 5; i++) {
            if (pointList.contains(new Point(p.x-i, p.y))){
                count++;
            }
            else{
                break;
            }
        }
        if (count == 5) {
            return true;
        }
        for (int i = 1; i < 5; i++) {
            if (pointList.contains(new Point(p.x+i, p.y))){
                count++;
            }
            else{
                break;
            }
        }
        if (count == 5) {
            return true;
        }
        return false;
    }

    private void drawPiece(Canvas canvas) {

        for (int i = 0; i < mWhiteList.size() ; i++) {
            Point p = mWhiteList.get(i);
            canvas.drawBitmap(mWhitePiece, (p.x + (1-mRatioPieceLengthOfLineHeight)/2)*mLineHeight,
                    (p.y + (1-mRatioPieceLengthOfLineHeight)/2)*mLineHeight, null);
        }

        for (int i = 0; i < mBlackList.size() ; i++) {
            Point p = mBlackList.get(i);
            canvas.drawBitmap(mBlackPiece, (p.x + (1-mRatioPieceLengthOfLineHeight)/2)*mLineHeight,
                    (p.y + (1-mRatioPieceLengthOfLineHeight)/2)*mLineHeight, null);
        }


    }

    private void drawBoard(Canvas canvas) {
        float startX = mLineHeight / 2;
        float endX = mPanelWidth - startX;
        float Y = mLineHeight / 2;

        for (int i = 0; i < MAX_LINE; i++) {
            canvas.drawLine(startX, mLineHeight * i + Y, endX, mLineHeight * i + Y, mPaint);
            canvas.drawLine(mLineHeight * i + Y, startX, mLineHeight * i + Y, endX, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mIsGameOver) {
            return false;
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            Point point = new Point((int) (x / mLineHeight), (int) (y / mLineHeight));

            //不能在同一个位置重复下棋
            if (mWhiteList.contains(point) || mBlackList.contains(point)) {
                return false;
            }

            if (mIsWhiteFirst) {
                mWhiteList.add(point);
            } else {
                mBlackList.add(point);
            }
            invalidate();  //刷新
            mIsWhiteFirst = !mIsWhiteFirst;

        }

        return true;
    }

    //保存实例
    /* !!在布局文件里的view 一定要写上id,否则不执行 */
    @Override
    protected Parcelable onSaveInstanceState() {
        
        Log.i("aaaaa", "onSaveInstanceState");
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE, super.onSaveInstanceState());
        bundle.putBoolean(INSTANCE_IS_GAME_OVER, mIsGameOver);
        bundle.putParcelableArrayList(INSTANCE_WHITE_LIST, mWhiteList);
        bundle.putParcelableArrayList(INSTANCE_BLACK_LIST, mBlackList);
        
        return bundle;
    }

    //恢复实例
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Log.i("aaaaa", "onRestoreInstanceState");
        if(state instanceof Bundle){
            Bundle bundle = (Bundle) state;
            mIsGameOver = bundle.getBoolean(INSTANCE_IS_GAME_OVER);
            mWhiteList = bundle.getParcelableArrayList(INSTANCE_WHITE_LIST);
            mBlackList = bundle.getParcelableArrayList(INSTANCE_BLACK_LIST);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    public void reStart(){
        mWhiteList.clear();
        mBlackList.clear();
        mIsGameOver = false;
        invalidate();
    }

}
