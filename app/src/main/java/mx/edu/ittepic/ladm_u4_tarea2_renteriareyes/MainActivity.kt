package mx.edu.ittepic.ladm_u4_tarea2_renteriareyes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCalendario.setOnClickListener {
            //Mandar llamar calendario
            startActivity(Intent(this,CalendarioActivity::class.java))
        }

        btnLLamadas.setOnClickListener {
            //Mandar llamar registro de llamadas
            startActivity(Intent(this,LlamadasActivity::class.java))
        }
    }
}
