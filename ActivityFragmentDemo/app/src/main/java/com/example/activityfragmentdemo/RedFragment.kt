package com.example.activityfragmentdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class RedFragment : Fragment() {

    override fun onCreateView(  // 1번.
        inflater: LayoutInflater,       // 뷰를 생성하는 객체
        container: ViewGroup?,          // 생성할 뷰(자식 뷰)가 들어갈 부모의 뷰
        savedInstanceState: Bundle?     // 이전 프래그먼트 객체에서 전달된 데이터(번들)
    ): View? {
        return inflater.inflate(R.layout.fragment_red, container, false)  // 2번
    }
}

