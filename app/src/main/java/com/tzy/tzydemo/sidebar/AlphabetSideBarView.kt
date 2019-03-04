package com.tzy.tzydemo.sidebar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import com.tzy.tzydemo.R

/**
 * Created by tanzy on 2019/3/2 0002.
 */
class AlphabetSideBarView : View {

    private lateinit var mPaint: Paint
    private lateinit var circlePaint: Paint
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private val c = arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#")

    private var textSize: Int = 0
    private var index: Int = 0
    private var choose = -1
    private var mFlag: Boolean = false
    //    private LetterChangeListener mChangeListener;
    private var mTouchListener: LetterTouchListener? = null


    private var selectBgColor = Color.BLACK
    private var selectTextColor = Color.BLACK
    private var normalTextColor = Color.WHITE

    constructor(context: Context) : super(context) {
        initView(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr) {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet?) {

        textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14f, resources.displayMetrics).toInt()

        if (attrs != null) {
            val typeArray = context.obtainStyledAttributes(attrs, R.styleable.asbv)

            selectBgColor = typeArray.getColor(R.styleable.asbv_asbv_select_bg_color, Color.BLACK)
            selectTextColor = typeArray.getColor(R.styleable.asbv_asbv_select_text_color, Color.WHITE)
            normalTextColor = typeArray.getColor(R.styleable.asbv_asbv_normal_text_color, Color.BLACK)
            textSize = typeArray.getDimensionPixelSize(R.styleable.asbv_asbv_text_size, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14f, resources.displayMetrics).toInt())
        }



        mPaint = Paint()
        mPaint.textSize = textSize.toFloat()
        mPaint.color = normalTextColor
        mPaint.isAntiAlias = true

        circlePaint = Paint()
        circlePaint.color = selectBgColor
        circlePaint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas)
        mWidth = width
        mHeight = height
        if (mFlag) {
            //			canvas.drawColor(Color.parseColor("#E5E5E5"));
        }
        val strHeight = (mHeight / c.size).toFloat()
        for (i in c.indices) {
            val x = mWidth / 2 - mPaint.measureText(c[i]) / 2
            val y = strHeight * i + strHeight
            if (choose == i) {
//                mPaint.color = resources.getColor(R.color.c_653fac)
                mPaint.color = selectTextColor
                canvas.drawCircle((mWidth / 2).toFloat(), y - textSize / 2 + textSize * 0.1f, (textSize / 2).toFloat(), circlePaint)
            }
            else {
//                mPaint.color = resources.getColor(R.color.c_feae1b)
                mPaint.color = normalTextColor
            }
            canvas.drawText(c[i], x, y, mPaint)
        }
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // TODO Auto-generated method stub
        val y = event.y
        index = (y / mHeight * c.size).toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mTouchListener?.setLetterVisibility(View.VISIBLE)
                mTouchListener?.setLetter(c[index])
                choose = index
                mFlag = true
            }
            MotionEvent.ACTION_MOVE -> if (index > -1 && index < c.size) {
                mTouchListener?.setLetter(c[index])
                choose = index
                mFlag = true
            }
            MotionEvent.ACTION_UP -> {
                mTouchListener?.setLetterVisibility(View.GONE)
                //                choose = -1;
                mFlag = false
            }
        }
        return true
    }

    fun setLetterTouchListener(listener: LetterTouchListener) {
        mTouchListener = listener
    }

    fun setLetterSelect(letter: String) {
        val index = c.indexOf(letter)
        if (index < 0) {
            Log.i("tanzy", "error letter $letter")
            return
        }
        choose = index
    }

    interface LetterTouchListener {
        fun setLetterVisibility(visibility: Int)

        fun setLetter(letter: String)
    }

}