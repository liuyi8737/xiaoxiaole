package com.example.xiaoxiaole

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import com.example.xiaoxiaole.databinding.ActivityMainBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel: ViewModel() {
    lateinit var viewsList:MutableList<ImageView>
    val cardModels = arrayListOf<CardModel>()
    var lastSelectedCard:CardModel? = null

    val resIdList = listOf(
        R.drawable.hema,
        R.drawable.huli,
        R.drawable.wa,
        R.drawable.yazi
    )

    fun bind(binding: ActivityMainBinding){
        viewsList = mutableListOf(
            binding.iv11,binding.iv12,binding.iv13,binding.iv14,
            binding.iv21,binding.iv22,binding.iv23,binding.iv24,
            binding.iv31,binding.iv32,binding.iv33,binding.iv34,
            binding.iv41,binding.iv42,binding.iv43,binding.iv44,
        )

        viewsList.shuffle()

        /**
         * 0
         * 0 1 2 3
         * 1
         * 4 5 6 7
         * 2
         * 8 9 10 11
         */
        for ((index,id) in resIdList.withIndex()){
            for (i in 4*index..4*index+3){
                val model = CardModel(viewsList[i],id)
                //model.view.setImageResource(model.resId)
                cardModels.add(model)
            }
        }
    }

    fun cardClicked(view: View){
        cardModels.forEach {
            if (it.view == view){
                it.view.setImageResource(it.resId)

                if (lastSelectedCard == null){
                    lastSelectedCard = it
                }else{
                    if (lastSelectedCard?.resId == it.resId){
                        Timer().schedule(object:TimerTask(){
                            override fun run() {
                                MainScope().launch {
                                    //隐藏两个
                                    view.visibility = View.INVISIBLE
                                    lastSelectedCard?.view?.visibility = View.INVISIBLE
                                    lastSelectedCard = null
                                }
                            }
                        },500)

                    }else{
                        Timer().schedule(object:TimerTask(){
                            override fun run() {
                                MainScope().launch {
                                    //显示原始图片
                                    lastSelectedCard?.view?.setImageResource(R.drawable.icbg)
                                    it.view.setImageResource(R.drawable.icbg)
                                    lastSelectedCard = null
                                }
                            }
                        },500)

                    }
                }
            }
        }
    }
}