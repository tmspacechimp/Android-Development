package com.tmaisuradze.alarmapp

interface IMainPresenter {
    fun onAlarmsFetched(alarms: List<Alarm>)
    fun onModeFetched(mode: Int)
}