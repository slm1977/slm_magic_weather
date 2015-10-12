package com.slm.games.magicwheather;
 

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.SeekBar;

public class LabelledSeekBar extends ViewGroup {

    /** SeekBar itself */
    private final SeekBar mSeekBar;
    /** Label for left end */
    private String mLeftLabel = null;
    /** Label for right end */
    private String mRightLabel = null;
    /** Bottom paddings for labels */
    private static final int DEFAULT_LABEL_PADDING_BOTTOM = 10; // px
    private int mLabelPaddingBottom = DEFAULT_LABEL_PADDING_BOTTOM;
    /** Center of 'bulbs' to draw labels above centered */
    private static final int DEFAULT_LABEL_PADDING_SIDE = 10; // px
    private int mLabelCenterPadding = DEFAULT_LABEL_PADDING_SIDE;
    /** Here goes labels attributes, they are similar to TextViews ones */
    private static final int DEFAULT_TEXT_SIZE = 10; // px
    private static final int DEFAULT_TEXT_COLOR = Color.BLACK; // px
    private static final Typeface DEFAULT_TEXT_STYLE = Typeface.DEFAULT; // px
    private int mTextSize = DEFAULT_TEXT_SIZE;
    private int mTextColor = DEFAULT_TEXT_COLOR;
    private Typeface mTextStyle = DEFAULT_TEXT_STYLE;
    /** Bounds for labels rects */
    private Rect mLeftTextBound = null;
    private Rect mRightTextBound = null;
    /** Rect for SeekBar */
    private Rect mSeekBarRect = null;
    /** Default height for SeekBar */
    private int mDefaultSeekBarHeight = 0;
    /** Paint for text */
    private Paint mTextPaint = null;
    /** Flag to draw or not the labels */
    private boolean mDrawLabels = false;

    /**
     * Constructor
     */
    public LabelledSeekBar(final Context context) {
        super(context);
        mSeekBar = new SeekBar(context);
        init(null);
    }

    /**
     * Constructor
     */
    public LabelledSeekBar(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        mSeekBar = new SeekBar(context, attrs);
        init(attrs);
    }

    /**
     * Constructor
     */
    public LabelledSeekBar(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        mSeekBar = new SeekBar(context, attrs, defStyle);
        init(attrs);
    }

    @Override
    protected void onLayout(final boolean changed, final int l, final int t, final int r, final int b) {
        mSeekBar.layout(mSeekBarRect.left, mSeekBarRect.top, mSeekBarRect.right, mSeekBarRect.bottom);
    }

    /**
     * Initializes Seek bar extended attributes from xml
     *
     * @param attributeSet {@link AttributeSet}
     */
    private void init(final AttributeSet attributeSet) {
        final TypedArray attrsArray = getContext().obtainStyledAttributes(attributeSet,R.styleable.LabelledSeekBar, 0, 0);
         
        mDefaultSeekBarHeight = getResources().getDimensionPixelSize(R.dimens.default_seekbar_height);
        
        //mDefaultSeekBarHeight = getResources().getDimensionPixelSize(R.id.seekbar);// FIXME
        
        mLeftLabel = attrsArray.getString(R.styleable.LabelledSeekBar_labelLeft);
        mRightLabel = attrsArray.getString(R.styleable.LabelledSeekBar_labelRight);
        mLabelPaddingBottom = attrsArray.getDimensionPixelOffset(R.styleable.LabelledSeekBar_labelPaddingBottom, DEFAULT_LABEL_PADDING_BOTTOM);
        mLabelCenterPadding = attrsArray.getDimensionPixelOffset(R.styleable.LabelledSeekBar_labelCenterSidePadding, DEFAULT_LABEL_PADDING_SIDE);

        // Now get needed Text attributes
        final int textSizeResource = attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "textSize", 0);

        if (0 != textSizeResource) {
            mTextSize = getResources().getDimensionPixelSize(textSizeResource);
        }

        final int textColorResource = attributeSet.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "textColor", 0);

        if (0 != textColorResource) {
            mTextColor = getResources().getColor(textColorResource);
        }

        final int typeface = attributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "textStyle", 0);

        switch (typeface) {
            // normale
            case 0:
                mTextStyle = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL);
                break;
            // bold
            case 1:
                mTextStyle = Typeface.create(Typeface.DEFAULT, Typeface.BOLD);
                break;
            // italic
            case 2:
                mTextStyle = Typeface.create(Typeface.DEFAULT, Typeface.ITALIC);
                break;
            // bold | italic
            case 3:
                mTextStyle = Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC);
                break;
        }

        mTextPaint = new TextPaint();
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setTypeface(mTextStyle);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        mTextPaint.setStyle(Paint.Style.FILL);

        addView(mSeekBar);
    }

    /**
     * Setters for labels
     *
     * @param leftLabel {@link String}
     * @param rightLabel {@link String}
     */
    public void setLabels(final String leftLabel, final String rightLabel) {
        mLeftLabel = leftLabel;
        mRightLabel = rightLabel;

        requestLayout();
    }

    @Override
    protected synchronized void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        // measure labels height - this logic is not very strict and can be changed
        mLeftTextBound = new Rect();
        mTextPaint.getTextBounds(mLeftLabel, 0, mLeftLabel.length(), mLeftTextBound);
        mRightTextBound = new Rect();
        mTextPaint.getTextBounds(mRightLabel, 0, mRightLabel.length(), mRightTextBound);

        final int labelHeight = Math.max(mLeftTextBound.height(), mRightTextBound.height());
        final int desiredMinHeight = labelHeight + mLabelPaddingBottom;
        final int desiredMinWidth = mLeftTextBound.width() + mRightTextBound.width();
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int measuredWidth = 0;
        int measuredHeight = 0;

        mSeekBarRect = new Rect();

        // Calculate width
        switch (widthMode) {
            case MeasureSpec.EXACTLY:
            case MeasureSpec.AT_MOST:
                if (widthSize < desiredMinWidth) {
                    mDrawLabels = false;
                } else {
                    mDrawLabels = true;
                    mSeekBarRect.set(mLeftTextBound.width() / 2 - mLabelCenterPadding, desiredMinHeight,
                            widthSize - mRightTextBound.width() / 2 + mLabelCenterPadding, heightSize);
                }
                measuredWidth = widthSize;
                break;

            case MeasureSpec.UNSPECIFIED:
                mDrawLabels = true;
                measuredWidth = desiredMinWidth + mLabelCenterPadding * 4;
                mSeekBarRect.set(mLeftTextBound.width() / 2 - mLabelCenterPadding, desiredMinHeight,
                        widthSize - mRightTextBound.width() / 2 + mLabelCenterPadding, heightSize);
                break;
        }

        if (mDrawLabels) {
            // Calculate height
            switch (heightMode) {
                case MeasureSpec.EXACTLY:
                case MeasureSpec.AT_MOST:
                    if (heightSize < desiredMinHeight) {
                        mDrawLabels = false;
                    } else {
                        mDrawLabels = true;
                        mSeekBarRect.top = desiredMinHeight;
                        mSeekBarRect.bottom = heightSize > mDefaultSeekBarHeight ? (desiredMinHeight + mDefaultSeekBarHeight) : heightSize;
                    }
                    measuredHeight = (heightSize > (desiredMinHeight + mDefaultSeekBarHeight)) ? (desiredMinHeight + mDefaultSeekBarHeight) : heightSize;
                    break;

                case MeasureSpec.UNSPECIFIED:
                    mDrawLabels = true;
                    measuredHeight = desiredMinHeight + mDefaultSeekBarHeight;
                    mSeekBarRect.top = desiredMinHeight;
                    mSeekBarRect.bottom = measuredHeight;
                    break;
            }
        } else {
            switch (heightMode) {
                case MeasureSpec.EXACTLY:
                case MeasureSpec.AT_MOST:
                    measuredHeight = heightSize;
                    break;

                case MeasureSpec.UNSPECIFIED:
                    measuredHeight = mDefaultSeekBarHeight;
                    break;
            }
        }

        if (!mDrawLabels) {
            // define SeekBar rect
            mSeekBarRect.set(0, 0, measuredWidth, measuredHeight);
        }

        mSeekBar.measure(MeasureSpec.makeMeasureSpec(mSeekBarRect.width(), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(mSeekBarRect.height(), MeasureSpec.EXACTLY));

        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void dispatchDraw(final Canvas canvas) {
        if (mDrawLabels) {
            final int height = Math.max(mLeftTextBound.height(), mRightTextBound.height());

            canvas.drawText(mLeftLabel, 0, height, mTextPaint);
            canvas.drawText(mRightLabel, getMeasuredWidth() - mRightTextBound.width(), height, mTextPaint);
        }

        super.dispatchDraw(canvas);
    }

    /**
     * Any layout manager that doesn't scroll will want this.
     */
    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }
}