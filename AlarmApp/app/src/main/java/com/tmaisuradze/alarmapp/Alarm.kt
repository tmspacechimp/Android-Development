package com.tmaisuradze.alarmapp

data class Alarm(val time: String, val toggled: Boolean): Comparable<Alarm> {
    override fun compareTo(other: Alarm): Int {
        return if(time > other.time) 1
            else if(time < other.time) -1
                else if(toggled && !other.toggled) 1
                    else if(!toggled && other.toggled) -1
                        else 0
    }

}
