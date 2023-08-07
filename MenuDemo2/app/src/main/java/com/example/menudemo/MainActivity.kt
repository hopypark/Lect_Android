package com.example.menudemo

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    var textView:TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 컨텍스트 메뉴 등록
        textView = findViewById(R.id.textHello)
        registerForContextMenu(textView) // 텍스트뷰에서 컨텍스트 메뉴가 등록하도록 처리
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
//        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
//        menu!!.setHeaderTitle("코드로 만드는 컨텍스트 메뉴")
//        menu!!.add(0,R.id.context_bg_red,1,"배경색: RED")
//        menu!!.add(0,R.id.context_bg_green,2,"배경색: GREEN")
//        menu!!.add(0,R.id.context_bg_blue,3,"배경색: BLUE")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.context_bg_red -> {
                textView!!.setBackgroundColor(Color.RED)
            }
            R.id.context_bg_green -> {
                textView!!.setBackgroundColor(Color.GREEN)
            }
            R.id.context_bg_blue -> {
                textView!!.setBackgroundColor(Color.BLUE)
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // return super.onCreateOptionsMenu(menu)
        // 옵션 메뉴로 R.menu.mymenu를 등록
        menuInflater.inflate(R.menu.mymenu, menu)
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_apple -> {
                Toast.makeText(this, "사과를 선택했습니다.", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.menu_graph -> {
                Toast.makeText(this, "포도를 선택했습니다.", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.menu_banana ->{
                Toast.makeText(this, "바나나를 선택했습니다.", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.menu_watermelon -> {
                Toast.makeText(this, "수박을 선택했습니다.", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return false
    }
}

