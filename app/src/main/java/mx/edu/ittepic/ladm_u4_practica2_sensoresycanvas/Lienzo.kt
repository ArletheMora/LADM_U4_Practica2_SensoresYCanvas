package mx.edu.ittepic.ladm_u4_practica2_sensoresycanvas

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.view.View
import androidx.core.content.ContextCompat.getSystemService

class Lienzo(p:MainActivity) : View(p), SensorEventListener {
    var sensorManager: SensorManager? = null
    var p = p
    var SENSOR_SERVICE = Context.SENSOR_SERVICE

    private var mSensorManager : SensorManager ?= null
    private var mAccelerometer : Sensor ?= null

    var xluz = 0f
    var yluz = 0f

    val bruja = Figura(this, R.drawable.bruja, 180f, 600f)
    var luz = Figura(this, R.drawable.sol2, xluz, yluz)
    var nube1 = Figura(this, R.drawable.nubes2, 150f, 20f)
    //var nube2 = Figura(this, R.drawable.nube2, 430f, 10f)

    init {
        //usoSensor()
        SensorActivity()
    }

    override fun onDraw(c: Canvas) {
        super.onDraw(c)

        val p = Paint()

        cambiarHoraDia(p, c, "noche")

        bruja.pintar(c)
        nube1.pintar(c)
        //nube2.pintar(c)
    }
    /*private fun usoSensor() {
        SensorManager = this.context.getSystemService(SENSOR_SERVICE) as SensorManager?

        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also {
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_FASTEST,
                SensorManager.SENSOR_DELAY_FASTEST)
        }
    }*/

    fun SensorActivity() {
        mSensorManager = this.context.getSystemService(SENSOR_SERVICE) as SensorManager?
        mAccelerometer = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also {
            mSensorManager!!.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_FASTEST,
                SensorManager.SENSOR_DELAY_FASTEST)
        }
    }

    /*protected fun onResume() {
        super.onResume()
        mSensorManager!!.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    protected fun onPause() {
        super.onPause()
        mSensorManager!!.unregisterListener(this)
    }*/


    override fun onSensorChanged(event: SensorEvent?){
        //println("entró a onSensorChanged")
        if(event?.sensor?.type == Sensor.TYPE_ACCELEROMETER){
            val sides = event.values[0]
            val upDown = event.values[1]

            println("up/down ${upDown.toInt()}\nleft/right ${sides.toInt()}")

            //CODIGO DE MOVIMIENTO
            luz.mover(sides, upDown)

            nube1.mover(sides, upDown)
            //nube2.mover(sides, upDown)
            invalidate()
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return
    }

    private fun cambiarHoraDia(p:Paint, c:Canvas, hora:String) {
        if(hora == "noche"){
            //noche
            p.color = Color.rgb(49, 54, 80)
            luz = Figura(this, R.drawable.luna2, luz.x, luz.y)
        }else{
            //día
            p.color = Color.argb(255,0,170, 228)
            luz = Figura(this, R.drawable.sol2, luz.x, luz.y)
        }
        c.drawRect(0f, 0f, 1200f, 2600f,p)
        luz.pintar(c)
    }
}


