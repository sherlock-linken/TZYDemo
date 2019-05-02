package com.tzy.tzydemo.ratestarbar

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.tzy.tzydemo.R
import kotlinx.android.synthetic.main.view_rating_star_bar.view.*

/**
 * Created by tanzy on 2019/4/29 0029.
 */
class RatingStarBarView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr) {

    private var onStarChanged: (Int) -> Unit = {}
    private val mStars by lazy { arrayOf(iv_star_1, iv_star_2, iv_star_3, iv_star_4, iv_star_5) }
    private var rootView: ViewGroup? = null
    private var mStarFocusLv1: Drawable? = null
    private var mStarFocusLv2: Drawable? = null
    private var mStarFocusLv3: Drawable? = null
    private var mStarNormal: Drawable? = null
    private var currentStar = 0
    private var mStarHeight = 0
    //    private var mDefaultValue = 0
    private var mStarWidth = 0
    private var mClickable = true

    init {
        rootView = LayoutInflater.from(context).inflate(R.layout.view_rating_star_bar, this) as ViewGroup
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatingStarBarView, defStyleAttr, 0)
        mStarFocusLv1 = typedArray.getDrawable(R.styleable.RatingStarBarView_rsbv_star_focus_lv1)
        mStarFocusLv2 = typedArray.getDrawable(R.styleable.RatingStarBarView_rsbv_star_focus_lv2)
        mStarFocusLv3 = typedArray.getDrawable(R.styleable.RatingStarBarView_rsbv_star_focus_lv3)
        mStarNormal = typedArray.getDrawable(R.styleable.RatingStarBarView_rsbv_star_normal)
        mStarHeight = typedArray.getDimensionPixelSize(R.styleable.RatingStarBarView_rsbv_star_height, 0)
        mStarWidth = typedArray.getDimensionPixelSize(R.styleable.RatingStarBarView_rsbv_star_width, 0)
        mClickable = typedArray.getBoolean(R.styleable.RatingStarBarView_rsbv_clickable, true)
        currentStar = typedArray.getInt(R.styleable.RatingStarBarView_rsbv_default_value, 0)
        typedArray.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        initView()
        if (mClickable) {
            setListener()
        }
    }

    private fun initView() {
        mStars.indices

                .forEach {
                    if (it < currentStar)
                        mStars[it].setImageDrawable(getFocusStratDrawable())
                    else
                        mStars[it].setImageDrawable(mStarNormal)
                }
    }

    private fun setListener() {
        for (index in mStars.indices) {
            mStars[index].setOnClickListener {
                currentStar = index + 1
                onStarChanged(index + 1)
                for (i in mStars.indices) {
                    if (i <= index) {
                        mStars[i].setImageDrawable(getFocusStratDrawable())
                    }
                    else {
                        mStars[i].setImageDrawable(mStarNormal)
                    }
                }
                starChangeListener(currentStar)
            }
        }
    }

    fun getStar(): Int {
        return currentStar
    }

    fun setStar(star: Int) {
        for (i in 0 until star) {
            mStars[i].setImageDrawable(getFocusStratDrawable())
        }
    }

    private fun getFocusStratDrawable(): Drawable? {
        return when (currentStar) {
            in 0..2 -> mStarFocusLv1
            3 -> mStarFocusLv2
            else -> mStarFocusLv3
        }
    }

    var starChangeListener: (Int) -> Unit = {}


}