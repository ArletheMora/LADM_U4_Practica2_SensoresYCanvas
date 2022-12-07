package mx.edu.ittepic.ladm_u4_practica2_sensoresycanvas

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class Lienzo(p:MainActivity) : View(p) {
    val bruja = Figura(this, R.drawable.bruja, 180f, 600f)
    var luz = Figura(this, R.drawable.sol2, 150f, 600f)
    var nube1 = Figura(this, R.drawable.nube2, 150f, 20f)
    var nube2 = Figura(this, R.drawable.nube2, 430f, 10f)

    override fun onDraw(c: Canvas) {
        super.onDraw(c)

        val p = Paint()

        cambiarHoraDia(p, c, "dia")

        bruja.pintar(c)
        nube1.pintar(c)
        nube2.pintar(c)
    }

    private fun cambiarHoraDia(p:Paint, c:Canvas, hora:String) {
        if(hora == "noche"){
            //noche
            p.color = Color.rgb(49, 54, 80)
            luz = Figura(this, R.drawable.luna2, 0f, 0f)
        }else{
            //día
            p.color = Color.argb(255,0,170, 228)
            luz = Figura(this, R.drawable.sol2, 0f, 0f)
        }
        c.drawRect(0f, 0f, 720f, 1600f,p)
        luz.pintar(c)
    }
}