package com.tmaisuradze.alarmapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainPresenter(private var view: IMainView?) : IMainPresenter{

    private val interactor = MainInteractor(this)

    override fun onAlarmsFetched(alarms: List<Alarm>) {
        view?.showAlarms(alarms)
    }

    override fun onModeFetched(mode: Int) {
        view?.setMode(mode)
    }

    fun saveAlarms(){
        interactor.setAlarms(view)
    }

    fun getAlarms(initialCall: Boolean = false){
        interactor.getAlarms(view, initialCall)
    }

    fun removeAlarm(alarm: Alarm){
        interactor.removeAlarmFromSet(alarm)
    }

    fun addAlarm(hour: Int, minute: Int, view: AppCompatActivity){
        val calendar = Calendar.getInstance()

        calendar.set(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DATE),
            hour, minute, 0
        )

        val time = calendar.timeInMillis - System.currentTimeMillis()
        if (time < 0) return

        var hourStr = if (hour < 10) "0$hour" else hour.toString()
        var minuteStr = if (minute < 10) "0$minute" else minute.toString()
        val alarm = Alarm("$hourStr:$minuteStr", true)
        interactor.addAlarmToSet(alarm)
        activate(alarm, view, calendar)
    }

    fun activate(alarm: Alarm, view: AppCompatActivity, calendar: Calendar){
        val (alarmManager, intent) = getAlarmManager(alarm, view)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, intent)
    }

    fun deactivate(alarm: Alarm, view: AppCompatActivity){
        val (alarmManager, intent) = getAlarmManager(alarm, view)
        alarmManager.cancel(intent)
    }

    private fun getAlarmManager(alarm: Alarm, view: AppCompatActivity):
            Pair<AlarmManager, PendingIntent> {
        val requestCode =
            alarm.time.substringBefore(":").toInt() * 60 + alarm.time.substringAfter(":").toInt()
        val intent = PendingIntent.getBroadcast(
            view,
            requestCode,
            Intent(AlarmReceiver.ALARM_ACTION_NAME).apply {
                `package` = view.packageName
                putExtra("time", alarm.time)
                }, 0)
        val alarmManager = view.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        return Pair(alarmManager, intent)
    }

    fun toggleAlarm(alarm: Alarm, toggled: Boolean, view: AppCompatActivity) {
        if (toggled) {
            val hours = alarm.time.substringBefore(":").toInt()
            val minutes = alarm.time.substringAfter(":").toInt()
            val calendar = Calendar.getInstance()
            calendar.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE),
                hours, minutes, 0
            )
            activate(alarm, view, calendar)
        } else {
            deactivate(alarm, view)
        }
        interactor.updateAlarm(alarm, toggled)
        saveAlarms()
    }

    fun getMode() {
        interactor.getMode(view)
    }

    fun saveMode(mode: Int) {
        interactor.setMode(view, mode)
    }


    fun detachView() {
        view = null
    }

}