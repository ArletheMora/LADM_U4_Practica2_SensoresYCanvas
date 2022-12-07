package mx.edu.ittepic.ladm_u4_practica2_sensoresycanvas

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint

class Figura(lienzo: Lienzo, imagen:Int, x:Float, y:Float) {
    val lienzo = lienzo
    val imagen = BitmapFactory.decodeResource(lienzo.resources, imagen)
    var x = x
    var y = y

    fun pintar(c: Canvas){
        val p = Paint()
        c.drawBitmap(imagen, x, y, p)
    }

    fun mover(xSensor:Float, ySensor:Float){
        //if (x<=720f && x >=0) {
            x += xSensor*-1
        //}
        //if (y<=1600 && y >=0) {
            y += ySensor
        //}
    }
}