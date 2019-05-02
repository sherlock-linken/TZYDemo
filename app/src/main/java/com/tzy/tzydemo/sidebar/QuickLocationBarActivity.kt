package com.tzy.tzydemo.sidebar

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.tzy.tzydemo.R
import com.tzy.tzydemo.sidebar.entity.WordEntity
import kotlinx.android.synthetic.main.activity_quick_location_bar.*
import kotlinx.android.synthetic.main.item_letter_layout.*
import java.util.*

class QuickLocationBarActivity : Activity() {

    private val wordList = arrayListOf("jay(2000)",
            "七里香(2004)",
            "魔杰座(2008)",
            "寻找周杰伦(2003)",
            "大灌篮(2008)",
            "Mr.J频道",
            "不能说的秘密(2007)",
            "熊猫人(2010)",
            "范特西(2001)",
            "十一月的萧邦(2005)",
            "跨时代(2010)",
            "头文字D(2005)",
            "头文字D(2005)",
            "头文字D(2005)",
            "刺陵(2009)",
            "八度空间(2002)",
            "八度空间(2002)",
            "八度空间(2002)",
            "依然范特西(2006)",
            "惊叹号(2011)",
            "惊叹号(2011)",
            "惊叹号(2011)",
            "惊叹号(2011)",
            "不能说的秘密(2007)",
            "青蜂侠(2011)",
            "叶惠美(2003)",
            "叶惠美(2003)",
            "叶惠美(2003)",
            "叶惠美(2003)",
            "叶惠美(2003)",
            "我很忙(2007)",
            "十二新作(2012)",
            "满城尽带黄金甲(2006)")

    private val dataList = arrayListOf<WordEntity>()
    private val mAdapter by lazy { WordAdapter() }
    private val mLayoutManager by lazy { LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) }

    var mLetterHeight = 0
    var mCurrentPosition = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quick_location_bar)

        wordList.forEach {
            val letter = CharacterParser.getInstnat().getSelling(it).toString()
            dataList.add(WordEntity(letter.toUpperCase(), it))
        }

        dataList.sort()

        rcy_word_list.layoutManager = mLayoutManager
        rcy_word_list.adapter = mAdapter

        rcy_word_list?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                mLetterHeight = ll_top_title?.height!!
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //找到列表下一个可见的View
                var view = mLayoutManager?.findViewByPosition(mCurrentPosition + 1)
                // 检查列表中的letter布局是否显示
                if (view != null && view.top <= mLetterHeight && view?.findViewById<TextView>(R.id.tv_letter)?.visibility == View.VISIBLE) {
                    //被顶掉的效果
                    ll_top_title?.y = (-(mLetterHeight - view.top)).toFloat()
                }
                else {
                    ll_top_title?.y = 0f
                }
                //判断是否需要更新悬浮条
                if (mCurrentPosition != mLayoutManager?.findFirstVisibleItemPosition()) {
                    ll_top_title?.y = 0f
                    updateLetter()
                }

                if (mCurrentPosition >= 0) {
                    asb_side_bar.setLetterSelect(dataList[mCurrentPosition].letter.substring(0,1))
                }

            }

        })
    }

    /**
     * 刷新 字母title
     */
    fun updateLetter() {
        mCurrentPosition = mLayoutManager?.findFirstVisibleItemPosition() ?: -1
        if (dataList.size > 0 && mCurrentPosition > -1 && mCurrentPosition < dataList.size) {
            tv_letter?.text = dataList[mCurrentPosition].letter.substring(0, 1)
        }
    }


    inner class WordAdapter : RecyclerView.Adapter<WordViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
            return WordViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_alphabet_item, parent, false))
        }

        override fun getItemCount(): Int {
            return dataList.size
        }

        override fun onBindViewHolder(holder: WordViewHolder, position: Int) {

            val data = dataList[position]

            if (position == 0 || dataList[position].letter.substring(0, 1) != dataList[position - 1].letter.substring(0, 1)) {
                holder.ll_top_title?.visibility = View.VISIBLE
                holder.tv_letter?.text = data.letter.substring(0, 1)
            }
            else {
                holder.ll_top_title?.visibility = View.GONE
            }

            holder.tv_name?.text = data.word

        }

    }


    inner class WordViewHolder : RecyclerView.ViewHolder {

        var tv_name: TextView? = null
        var ll_top_title: LinearLayout? = null
        var tv_letter: TextView? = null

        constructor(itemView: View?) : super(itemView) {
            tv_name = itemView?.findViewById(R.id.tv_name)
            ll_top_title = itemView?.findViewById(R.id.ll_top_title)
            tv_letter = itemView?.findViewById(R.id.tv_letter)
        }


    }

}
