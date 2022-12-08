package mx.edu.ittepic.ladm_u4_practica2_sensoresycanvas

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import android.view.View


class Lienzo(p:MainActivity) : View(p), SensorEventListener {
    //var sensorManager: SensorManager? = null
    var p = p
    var SENSOR_SERVICE = Context.SENSOR_SERVICE

    //Acelerometro
    private var mSensorManager : SensorManager ?= null
    private var mAccelerometer : Sensor ?= null

    //Proximidad
    private var mSensorManagerProx : SensorManager ?= null
    private var mProximidad : Sensor ?= null

    //val sensorManagerAprox = this.context.getSystemService(SENSOR_SERVICE) as SensorManager?
    //val proximitySensor = sensorManagerAprox!!.getDefaultSensor(Sensor.TYPE_PROXIMITY)

    var xluz = 0f
    var yluz = 0f

    var hora = "dia"

    val bruja = Figura(this, R.drawable.bruja, 180f, 600f)
    var luz = Figura(this, R.drawable.sol2, xluz, yluz)
    var nube1 = Figura(this, R.drawable.nubes2, 150f, 20f)

    init {
        //usoSensor()
        //
        /*if(proximitySensor == null) {
            Log.e(TAG, "Sensor de proximidad no disponible");
            println("Sensor de proximidad no disponible");
            finish(); // Close app
        }else{
            println("prueba")
        }*/
        SensorAcelerometro()
        sensorProximidad()
    }

    val proximitySensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(sensorEvent: SensorEvent) {
           /* if(sensorEvent.values[0] < proximitySensor.getMaximumRange()) {
                hora = "noche"
            } else {
                hora = "dia"
            }
            println(hora)
            invalidate()*/
        }

        override fun onAccuracyChanged(sensor: Sensor, i: Int) {}
    }



    private fun sensorProximidad() {

        mSensorManagerProx = this.context.getSystemService(SENSOR_SERVICE) as SensorManager?
        mProximidad = mSensorManagerProx!!.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        mSensorManagerProx!!.getDefaultSensor(Sensor.TYPE_PROXIMITY)?.also {
            mSensorManagerProx!!.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_FASTEST,
                SensorManager.SENSOR_DELAY_FASTEST)
        }

        /*sensorManagerProx?.registerListener(proximitySensorListener,
                proximitySensor, 2 * 1000 * 1000);

        if(proximitySensor == null) {
            Log.e(TAG, "Sensor de proximidad no disponible");
            println("Sensor de proximidad no disponible");
            finish(); // Close app
        }*/

    }

    private fun finish() {
        TODO("Not yet implemented")
    }

    override fun onDraw(c: Canvas) {
        super.onDraw(c)

        val p = Paint()

        cambiarHoraDia(p, c)

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

    fun SensorAcelerometro() {
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



    override fun onSensorChanged(event: SensorEvent?){
        //println("entró a onSensorChanged")
        if(event?.sensor?.type == Sensor.TYPE_ACCELEROMETER){
            val sides = event.values[0]
            val upDown = event.values[1]

            //println("up/down ${upDown.toInt()}\nleft/right ${sides.toInt()}")

            //CODIGO DE MOVIMIENTO
            luz.mover(sides, upDown)

            nube1.mover(sides, upDown)
            //nube2.mover(sides, upDown)
            invalidate()
        }

        if(event?.sensor?.type == Sensor.TYPE_PROXIMITY){
            println("aproximación " + event.values[0])
             if(event.values[0] > 0) {
                 hora = "dia"
             } else {
                 hora = "noche"
             }
             println(hora)
             invalidate()
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return
    }

    private fun cambiarHoraDia(p:Paint, c:Canvas) {
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
        invalidate()
    }
}


