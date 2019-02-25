package com.tzy.tzydemo.spanhtml

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.method.LinkMovementMethod
import android.util.Log
import kotlinx.android.synthetic.main.activity_span_and_html.*
import java.util.regex.Pattern


class SpanAndHtmlActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.tzy.tzydemo.R.layout.activity_span_and_html)


        tv_btn_1.setOnClickListener {
            Log.i("tanzy", "SpanAndHtmlActivity.onCreate ")
            var data = "good morning <at bid=\"12138\" nickname=\"\">@sherlock</at>, this is <at bid=\"10086\">@linken</at> speaking"

            val regxpForEmbedTag = "<at.*?>.*?</at>"
            val atTahPattern = Pattern.compile(regxpForEmbedTag)
            val atTagMatcher = atTahPattern.matcher(data)
            while (atTagMatcher.find()) {
                val strAtTag = atTagMatcher.group()

                val bidPattern = Pattern.compile("bid=\".*?\"")
                val bidMatcher = bidPattern.matcher(strAtTag)
                var bid =""
                if(bidMatcher.find())
                    bid = bidMatcher.group().replace("bid=\"","").replace("\"","")


                val nicknamePattern = Pattern.compile("nickname=\".*?\"")
                val nicknameMatcher = nicknamePattern.matcher(strAtTag)
                var nickname =""
                if(nicknameMatcher.find())
                    nickname = nicknameMatcher.group().replace("nickname=\"","").replace("\"","")

                Log.i("tanzy", "SpanAndHtmlActivity.onCreate nickname = $nickname and bid = $bid")

            }


//            var sHtml = Html.fromHtml(data) as SpannableStringBuilder


//            tv_test_1.text = sHtml

//            tv_test_1.movementMethod = LinkMovementMethod.getInstance()

        }

    }
}
