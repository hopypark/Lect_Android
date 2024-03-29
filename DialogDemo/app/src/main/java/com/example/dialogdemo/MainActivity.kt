package com.example.dialogdemo

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CombinedVibration
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import android.view.LayoutInflater
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //showToast()
//        showDatePick()
//        showTimePicker()
//        showDialog2()
        //customDialog()
//        val ringtoneUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//        val ringtone = RingtoneManager.getRingtone(applicationContext, ringtoneUri)
//        ringtone.play()

//        val player: MediaPlayer = MediaPlayer.create(this, R.raw.hare)
//        player.start()
        // Vibrator
//        val vibrator: Vibrator
//        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            //val effect = VibrationEffect.createOneShot(100L, VibrationEffect.DEFAULT_AMPLITUDE)
//            val effect = VibrationEffect.createWaveform(longArrayOf(500,1000, 500, 2000), intArrayOf(0, 50, 0, 200), -1)
//            vibrator.vibrate(effect)
//        }else{
//            vibrator.vibrate(500)
//        }

        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        }else{
           getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibrator = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager

            //val effect = VibrationEffect.createOneShot(100L, VibrationEffect.DEFAULT_AMPLITUDE)
            val effect = VibrationEffect.createWaveform(longArrayOf(500,1000, 500, 2000), intArrayOf(0, 50, 0, 200), -1)
            val combinedVibration = CombinedVibration.createParallel(effect)
            vibrator.vibrate(combinedVibration)
        }else{
            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            //val effect = VibrationEffect.createOneShot(100L, VibrationEffect.DEFAULT_AMPLITUDE)
            val effect = VibrationEffect.createWaveform(longArrayOf(500,1000, 500, 2000), intArrayOf(0, 50, 0, 200), -1)
            vibrator.vibrate(effect)

        }





    }

    fun customDialog(){
//        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val rootView = inflater.inflate(R.layout.dialog_input, null)
//        val builder = AlertDialog.Builder(this)
//            builder.setTitle("Input Dialog")
//            builder.setView(rootView)
//            builder.setPositiveButton("확인", null)
//            builder.show()
    }

    fun showDialog2(){
        val items = arrayOf<String>("사과", "복숭아", "수박", "딸기")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Item Test")
        builder.setIcon(android.R.drawable.ic_dialog_info)
        builder.setMultiChoiceItems(items, booleanArrayOf(true, false, true, false),
            object : DialogInterface.OnMultiChoiceClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int, isChecked: Boolean) {
                    Log.d( "showDiolog2",
                        "${items[which]}이 ${if (isChecked) "선택되었습니다." else "선택 해제되었습니다."}")
                }
            })
        builder.setPositiveButton("Done",null)
        builder.show()

    }

    fun showDialog1(){
        // 다이얼로그 버튼의 이벤트 핸들러 등록
        val eventHandler = object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                if (which == DialogInterface.BUTTON_POSITIVE){
                    Log.d("showDialog1", "positive button click")
                }else if (which == DialogInterface.BUTTON_NEGATIVE){
                    Log.d("showDialog1", "negative button click")
                }
            }
        }
        // 알림창 빌드
        val build = AlertDialog.Builder(this)
        build.setTitle("Test Dialog")
            .setIcon(android.R.drawable.ic_dialog_info)
            .setMessage("정말로 종료하시겠습니까?")
            .setPositiveButton("Yes",eventHandler)
            .setNegativeButton("No", eventHandler)
            .setNeutralButton("More", null) // 처리할 내용이 없다.
            .show()
    }

    fun showTimePicker(){
        TimePickerDialog(this, object : TimePickerDialog.OnTimeSetListener{
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                Log.d("tae", "hourOfDay:$hourOfDay, minute: $minute")
            }
        }, 14,30,false).show()
    }

    fun showDatePick(){
        DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener{
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                Log.d("tae", "year:$year, month: $month, dayOfMonth:$dayOfMonth")
            }
        }, 2023, 7,3).show()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun showToast(){
        val toast = Toast.makeText(this, "종료하려면 한 번 더 누르세요.", Toast.LENGTH_SHORT)
        toast.addCallback(object : Toast.Callback(){
            override fun onToastShown() {   // 토스트가 나타날 때
                super.onToastShown()
                Log.d("tae", "toast hidden")
            }

            override fun onToastHidden() {  // 토스트가 사라질 때
                super.onToastHidden()
                Log.d("tae", "toast shown")
            }
        })
        toast.show()
    }
}
